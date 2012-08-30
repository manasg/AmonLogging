package com.github.manasg.logging;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

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

public class AmonPlusAppender extends AppenderSkeleton {
    String key;
    String hostname;
    String destinationIp;
    String destinationPort;
    String appName;
    // We keep the same ZMQ object for as long as we can.
    ZeroMQTransport z;
    
    public AmonPlusAppender() {
        this.threshold = Level.WARN;

        if (hostname == null) {
            try {
                this.hostname = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                this.hostname = "unknown";
            }
        }
        // initializing z here creates a problem as desintationIP, port are null at this point
    }

    private String getDestinationURL() {
        StringBuffer sb = new StringBuffer();
        sb.append("http://").append(this.destinationIp).append(":")
                .append(this.destinationPort).append("/api/log/")
                .append(this.key);
        return sb.toString();
    }
    
    private String getZMQAddress() {
        return new StringBuffer().append(this.destinationIp)
                .append(":")
                .append(this.destinationPort).toString();
    }

    @Override
    public void close() {
        z.close();
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    private ArrayList<String> gatherTags(LoggingEvent e) {
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(this.appName);
        tags.add(e.getLevel().toString());
        tags.add(this.hostname);

        return tags;
    }

    private String generateMessage(LoggingEvent e) {
        // Similar to PatternLayout
        StringBuffer sb = new StringBuffer();

        sb.append(new Date(e.getTimeStamp()));
        sb.append(" [").append(e.getThreadName()).append("] ");
        sb.append(e.getLevel().toString());
        sb.append(" ");
        sb.append(e.getLoggerName());
        sb.append(" - ");
        sb.append(e.getRenderedMessage());

        // INFR-122 : Including Exception as part of log message
        // Amon lets you send exceptions separately, but we use logs for now.
        // Using HTML "<BR>" as a line break. It makes it look nicer on the web-console
        String[] exceptionStackTrace = e.getThrowableStrRep();
        if (null != exceptionStackTrace) {
            for (String line : exceptionStackTrace) {
                sb.append("<br>").append(line);
            }
        }
        
        return sb.toString();
    }

    protected String getJsonPayload(LoggingEvent e) {

        return AmonPlusPayload.PayloadJson(this.generateMessage(e),
                this.gatherTags(e));
    }

    @Override
    protected void append(LoggingEvent event) {
        appendViaZMQ(event);
        //appendViaHTTP(event);
    }
    
   
    private void appendViaZMQ(LoggingEvent event) {
        if (null==z) {
            z = new ZeroMQTransport(getZMQAddress());
        }
        
        // TODO -   Buffer this. Currently this sends each message in its own packet, 
        //          while the Python implementation buffers it - reducing number of packets.
        z.post(this.key, this.generateMessage(event), this.gatherTags(event));
         
    }

    private void appendViaHTTP(LoggingEvent event) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(getDestinationURL());

        try {
            String payload = this.getJsonPayload(event);

            httpPost.setEntity(new StringEntity(payload));
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse response2 = httpclient.execute(httpPost);

            // System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);

        } catch (Exception e) {

        } finally {
            httpPost.releaseConnection();
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDestinationIp() {
        return destinationIp;
    }

    public void setDestinationIp(String destinationIp) {
        this.destinationIp = destinationIp;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}
