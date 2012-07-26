package com.github.manasg.logging.test;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Simple {

    
    
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Simple.class.getName());
        PropertyConfigurator.configure("conf/log4j.properties");
        
        logger.info("It starts now!");
        
        try {
            logger.info("Trying something");
            throw new Exception();
            
        } catch(Exception e) {
            logger.error("Something bad happened");
            // check something
            logger.warn("This is a warning");
            // actually its worse
            logger.fatal("Call somebody!");
            logger.log(Level.FATAL, "so fatal !");
        }
        
        logger.info("Done");
        
        
    }

}
