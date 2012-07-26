package com.github.manasg.logging;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.spi.LoggingEvent;

import com.google.gson.Gson;

public class AmonPayload {
    String message;
    ArrayList<String> tags;
    
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public ArrayList<String> getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
    
    public static String AmonPayloadJson(LoggingEvent e, String environ, String hostname) {
        AmonPayload p = new AmonPayload();
        StringBuffer sb = new StringBuffer();
        
        sb.append(new Date(e.getTimeStamp()));
        sb.append(" [").append(e.getThreadName()).append("] ");
        sb.append(e.getLevel().toString());
        sb.append(" ");
        sb.append(e.getLoggerName());
        sb.append(" - ");
        sb.append(e.getRenderedMessage());
        
        p.setMessage(sb.toString());
        
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(e.getLevel().toString());
        // add more useful tags!
        tags.add(environ);
        tags.add(hostname);
        p.setTags(tags);
        
        return new Gson().toJson(p);
    }
    

    
}
