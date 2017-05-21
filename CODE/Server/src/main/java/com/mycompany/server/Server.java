package com.mycompany.server;

import static spark.Spark.*;

public class Server { 
    // get gates - wyslij liste gate'ow
    // post gates - odbierz wybrany gate
    // get cgate - wyslij wybrany gate do oficera
    
    public static void main(String[] args) { 
        Gates gates = new Gates();
        port(80);
        
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
            return "Gate selected properly";
        });
    }
}
