/*package com.eNvestDetails.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate4.encryptor.HibernatePBEEncryptorRegistry;
import org.springframework.beans.factory.annotation.Autowired;

@WebListener
public class LoadEncrypter  {

	

	@Autowired
	StandardPBEStringEncryptor encryptor = null;
	
	public void init(){
		HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
		registry.registerPBEStringEncryptor("myHibernateStringEncryptor", encryptor);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		// TODO Auto-generated method stub
		StandardPBEStringEncryptor strongEncryptor = new StandardPBEStringEncryptor();
		strongEncryptor.setAlgorithm("PBEWithMD5AndDES");
		strongEncryptor.setPassword("test");
		HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
		registry.registerPBEStringEncryptor("myHibernateStringEncryptor", encryptor);
	}
	}*/

package com.envest.services.components.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate4.encryptor.HibernatePBEEncryptorRegistry;

import com.envest.services.components.config.ConfigFactory;

@WebListener
public class LoadEncrypter implements javax.servlet.ServletContextListener {
	
	private ConfigFactory config = new ConfigFactory();
	private Logger log = Logger.getLogger(LoadEncrypter.class.getName());

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		log.info("Starting to initialize Standared Encryptor");
		StandardPBEStringEncryptor strongEncryptor = new StandardPBEStringEncryptor();
		strongEncryptor.setAlgorithm(config.getResultString("alogrithm"));
		strongEncryptor.setPassword(config.getResultString("algoPassword"));
		HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
		registry.registerPBEStringEncryptor("myHibernateStringEncryptor", strongEncryptor);
	}
	}