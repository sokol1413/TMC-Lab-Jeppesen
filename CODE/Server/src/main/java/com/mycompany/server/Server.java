package com.mycompany.server;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.websocket.api.Session;
import static spark.Spark.*;

public class Server { 
    // get gates - wyslij liste gate'ow
    // post gates - odbierz wybrany gate
    // get cgate - wyslij wybrany gate do oficera
    
    // this map is shared between sessions and threads, so it needs to be thread-safe (http://stackoverflow.com/a/2688817)
    static Map<Session, String> activeWsSessions = new ConcurrentHashMap<>();
    static int nextSessionId = 1; //Used for creating the next username
    
    public static void main(String[] args) { 
        Gates gates = new Gates();
        staticFiles.location("/public");
        webSocket("/infosource", WsHandler.class);
        port(80);
        
        redirect.get("/", "/Captain.html");
        
        get("/gates", (req, res) -> {
            res.type("application/json");
            res.header("Access-Control-Allow-Origin", "*");
            return gates.getGates();
        });
        
        get("/cgate", (req, res) -> {
            res.type("application/json");
            res.header("Access-Control-Allow-Origin", "*");
            System.out.println("I'm sending gate " + gates.getGate(gates.selected_id) + " to the Officer");             
            return gates.getGate(gates.selected_id);
        });
        
        post("/gates", (req, res) -> {
            res.type("text");
            res.header("Access-Control-Allow-Origin", "*");
            int selectedGateId = Integer.parseInt(req.body());
            gates.selected_id = selectedGateId;
            System.out.println("Captain has chosen gate " + gates.getGate(gates.selected_id) + "!");
            broadcastMessage("new gate");
            return "Gate selected properly";
        });
    }
    
    //broadcast message to every connected ws (https://sparktutorials.github.io/2015/11/08/spark-websocket-chat.html)
    public static void broadcastMessage(String message) {
        activeWsSessions.keySet().stream().filter(Session::isOpen).forEach(session -> {
        try {
            session.getRemote().sendString(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    }
}
