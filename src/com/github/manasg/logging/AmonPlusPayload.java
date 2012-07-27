package com.github.manasg.logging;

import java.util.ArrayList;
import com.google.gson.Gson;

public class AmonPlusPayload {
    String message;
    ArrayList<String> tags;
    
    public static String PayloadJson(String message, ArrayList<String> tags) {
        AmonPayload p = new AmonPayload();
        p.message = message;
        p.tags = tags;
        
        return new Gson().toJson(p);
    }
    
}
