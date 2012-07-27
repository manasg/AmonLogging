package com.github.manasg.logging.test;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.github.manasg.logging.RemoteAppender;

public class AmonPlusLogger {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(AmonPlusLogger.class.getName());
        PropertyConfigurator.configure("conf/amonplus.log4j.properties");
        
        logger.debug("Starting to do something");        
        logger.info("Informational");        
        logger.warn("Don't think I can do it");        
        logger.error("Its official. I can't do it");        
        logger.fatal("Please help");
    }

}
