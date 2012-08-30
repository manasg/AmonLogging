package com.github.manasg.logging;

import java.util.ArrayList;

import org.zeromq.ZMQ;

public class ZeroMQTransport {
    ZMQ.Context context;
    ZMQ.Socket socket;
    String destination;
    
    public ZeroMQTransport(String destination) {
        context = ZMQ.context(1);
        socket = context.socket(ZMQ.DEALER);
        socket.setLinger(100);
        //socket.setSwap(25000000); // 25MB disk swap
        this.destination = "tcp://"+destination;
        
        // ZMQ lets us connect right away and I don't have to deal if the remote end is up or not
        socket.connect(this.destination);
    }
    
    public void post(String key, String message, ArrayList<String> tags) {
        String jsonData = ZmqAmonPlusPayload
                .PayloadJson(key, message, tags);
        
        //System.out.println(jsonData);
        socket.send(jsonData.getBytes(), ZMQ.NOBLOCK);
    }
    
    public void close() {
        socket.close();
    }
    
    public static void main(String[] args){
        String dest = "server.someplace.com:5464";
        
        ZeroMQTransport zmqT = new ZeroMQTransport(dest);
        
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("mg-test");
        
        int num = 3;
        for(int i=0; i<num; i++) {
            zmqT.post("ALPoyJxtlx3A1Prt7Rs", "A message from Java Land "+i, tags);
                
        }

        zmqT.close();
        
        System.out.println("Done");
    }

}
