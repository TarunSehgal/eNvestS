package com.eNvestDetails.util;

import java.net.URI;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.eNvestDetails.Config.ConfigFactory;
import com.eNvestDetails.dto.AccountsDTO;
import com.eNvestDetails.dto.AddressDTO;
import com.eNvestDetails.dto.BankDTO;
import com.eNvestDetails.dto.ProductDTO;
import com.eNvestDetails.dto.UserAccessTokenDTO;
import com.eNvestDetails.dto.UserEmailDTO;
import com.eNvestDetails.dto.UserInfoDTO;
import com.eNvestDetails.dto.UserPhoneDTO;
import com.eNvestDetails.dto.UserProductDTO;
import com.eNvestDetails.dto.UserProfileDataDTO;



public class HibernateUtils {
	
	static Configuration configuration = new Configuration();
	
	
	static SessionFactory sessionFactory = null;
	
	private static ConfigFactory config = new ConfigFactory();
	
	private static Logger log = Logger.getLogger(HibernateUtils.class.getName());
	
	public static SessionFactory getSessionFactory(){
		try{
			if(sessionFactory == null){
				log.info("starting to buiod session factory");
				if("Y".equals(config.getResultString("localrun"))){
					configuration.setProperty("hibernate.connection.driver_class", config.getResultString("jdbc.driverClassName"));
	                configuration.setProperty("hibernate.connection.url", config.getResultString("jdbc.url"));
	                configuration.setProperty("hibernate.connection.username",config.getResultString("jdbc.username"));
	                configuration.setProperty("hibernate.connection.password", config.getResultString("jdbc.password"));
				}else{
					URI dbUri = new URI(System.getenv(config.getResultString("dbProperty")));
			        String username = dbUri.getUserInfo().split(":")[0];
			        String password = dbUri.getUserInfo().split(":")[1];
			        String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
					configuration.setProperty("hibernate.connection.driver_class", config.getResultString("jdbc.driverClassName"));
	                configuration.setProperty("hibernate.connection.url", dbUrl);
	                configuration.setProperty("hibernate.connection.username",username);
	                configuration.setProperty("hibernate.connection.password", password);
				}							
                configuration.setProperty("show_sql", config.getResultString("hibernate.show_sql"));
                configuration.setProperty("dateTime", "org.joda.time.contrib.hibernate.PersistentDateTime");
                //configuration.addPackage("com.eNvestDetails.dto");
                configuration.addAnnotatedClass(UserInfoDTO.class);
                configuration.addAnnotatedClass(AddressDTO.class);
                configuration.addAnnotatedClass(AccountsDTO.class);
                configuration.addAnnotatedClass(UserEmailDTO.class);
                configuration.addAnnotatedClass(UserPhoneDTO.class);
                configuration.addAnnotatedClass(UserAccessTokenDTO.class);
                configuration.addAnnotatedClass(ProductDTO.class);
                configuration.addAnnotatedClass(UserProductDTO.class);
                configuration.addAnnotatedClass(BankDTO.class);
               // configuration.addAnnotatedClass(UserProfileDTO.class);
                configuration.addAnnotatedClass(UserProfileDataDTO.class);
                sessionFactory = configuration.configure().buildSessionFactory();
                log.info("successfully build the session");
			}else{
				log.info("session not null returning exsiting session object");
				return sessionFactory;
			}
		}catch(Exception e){
			log.error("Error occurred while initializing session", e);
		}
		return sessionFactory;
	}
	
	/**
     * Gets the open session.
     * 
     * @return the open session
     */
    public static Session getOpenSession() {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
        } catch (Throwable e) {
        	 log.error("Error occured while getting open session",e);
        }
        return session;
    }

    /**
     * Close session.
     * 
     * @param session
     *            the session
     */
    public static void closeSession(Session session) {
        try {
            session.close();
        } catch (HibernateException e) {
        	log.error("Error occured while closing session",e);
        }

    }

    public static Session getCurrentSession() {
        Session session = null;
        try {
            session = getSessionFactory().getCurrentSession();
        } catch (Throwable e) {
        	log.error("Error occured while getting current session",e);
        }
        return session;
    }
}
