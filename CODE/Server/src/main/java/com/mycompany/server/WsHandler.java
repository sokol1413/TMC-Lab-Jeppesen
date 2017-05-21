package com.mycompany.server;

import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.Session;

@WebSocket
public class WsHandler {
    
    private String sender;

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        String id = String.valueOf(Server.nextSessionId++);        
        Server.activeWsSessions.put(session, id);
        System.out.println("New WS connection");
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {        
        Server.activeWsSessions.remove(session);
        System.out.println("Removed WS connection");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
    }
        
}
    
