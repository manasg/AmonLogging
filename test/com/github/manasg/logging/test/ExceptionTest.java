package com.github.manasg.logging.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ExceptionTest {
    // Test sending exceptions to Amon
    
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(AmonPlusLogger.class.getName());
        PropertyConfigurator.configure("conf/amonplus.log4j.properties");
        
        logger.info("Starting to do something");
        
        try {
            genRunTimeEx();
            
        } catch(Exception ex) {
            logger.error("Caught Exception", ex);
        }
        
    }
    
    private static void doSomething() {
        int div = 5-5;
        int c = 10/div;
    }
    
    private static void genRunTimeEx() throws Exception {
        // non-existent file
        BufferedReader in = new BufferedReader(new FileReader("foo.in"));
    }

}
