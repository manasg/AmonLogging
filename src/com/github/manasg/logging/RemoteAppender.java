package com.github.manasg.logging;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

public class RemoteAppender extends AppenderSkeleton {
    protected String destination = "localhost:2464";
    protected String environment = "unknown_env";
    protected String hostname = "";
    
    public String getHostname() {
        return hostname;
    }


    public void setHostname(String hostname) {
        this.hostname = hostname;
    }


    private String getDestinationURL() {
        StringBuffer sb = new StringBuffer();
        sb.append("http://").append(this.destination).append("/api/log");
        return sb.toString();
    }

    
    public String getEnvironment() {
        return environment;
    }


    public void setEnvironment(String environment) {
        this.environment = environment;
    }


    public String getDestination() {
        return destination;
    }


    public void setDestination(String destination) {
        this.destination = destination;
    }

    public RemoteAppender() {
        // default
        this.setThreshold(Level.WARN);
        
        // try to get the IP/hostname
        try {
            this.hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            this.hostname = "unknown";
        }
        
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    @Override
    protected void append(LoggingEvent arg0) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(getDestinationURL());
        
        try {
            String payload = AmonPayload.AmonPayloadJson(arg0,getEnvironment(),hostname);
            httpPost.setEntity(new StringEntity(payload));
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response2 = httpclient.execute(httpPost);

            //System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);

        } catch (Exception e) {

        } finally {
            httpPost.releaseConnection();
        }
    }

}
