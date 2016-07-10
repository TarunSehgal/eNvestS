package com.eNvestDetails.Config;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("configFactory")
@Scope("singleton")
public class ConfigFactory {

    /**
     * Gets the result string.
     * 
     * @return the result string
     */
    private static final Logger LOGGER = Logger.getLogger(ConfigFactory.class.getName());

    /**
     * Gets the key and return the value of that key which is mentioned in config.properties.
     * 
     * @param key
     *            the key
     * @return the result string
     */
    private static Properties prop =null;

    public String getResultString(String key) {

        String value = "";
        try {
            if (null == prop) {
                prop=new Properties();
                prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            }

               // LOGGER.debug(LOGGER.getName() + ":getResultStringStart");
                value = prop.getProperty(key);
                //LOGGER.debug(LOGGER.getName() + ":getResultStringEnd");  


        } catch (Exception e) {
            LOGGER.error("Exception while getting property value",e);
        }

        return value;
    }


}