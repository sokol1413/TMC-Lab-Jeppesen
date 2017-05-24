package service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.Session;

@WebSocket
public class WsHandler {
    
    // this map is shared between sessions and threads, so it needs to be thread-safe (http://stackoverflow.com/a/2688817)
    static Map<Session, String> activeWsSessions = new ConcurrentHashMap<>();
    static int nextSessionId = 1; //Used for creating the next username

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        String id = String.valueOf(nextSessionId++);        
        activeWsSessions.put(session, id);
        System.out.println("New WS connection");
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {        
        activeWsSessions.remove(session);
        System.out.println("Removed WS connection");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
    }
    
        //broadcast message to every connected ws (https://sparktutorials.github.io/2015/11/08/spark-websocket-chat.html)
    public static void broadcastMessage(String message) {
        activeWsSessions.keySet().stream().filter(Session::isOpen).forEach((Session session) -> {
            try {
                session.getRemote().sendString(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
        
}
    
