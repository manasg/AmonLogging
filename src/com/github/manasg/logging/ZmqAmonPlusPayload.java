package com.github.manasg.logging;

import java.util.ArrayList;

import com.google.gson.Gson;
/*
 * I had to create another class to enclose AmonPayload 
 * to play along with what the AmonMQ daemon was expecting as JSON.
 * There is absolutely no other reason to do this.
 * 
 * This was the easiest way for me to generate valid JSON with GSON lib.
 */
public class ZmqAmonPlusPayload  {
    String type = "log";
    AmonPayload content;
    String secret_key;
    
    public static String PayloadJson(String key, String message, ArrayList<String> tags) {
        AmonPayload p = new AmonPayload();
        p.message = message;
        p.tags = tags;
        
        ZmqAmonPlusPayload zp = new ZmqAmonPlusPayload();
        zp.content = p;
        zp.secret_key = key;
        
        return new Gson().toJson(zp);
    }
}
