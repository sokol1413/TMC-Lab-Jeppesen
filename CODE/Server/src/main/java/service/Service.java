package service;

import java.util.ArrayList;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

public class Service { 
    // get gates - wyslij liste gate'ow
    // post gates - odbierz wybrany gate
    // get cgate - wyslij wybrany gate do oficera
    
     ArrayList<Gate> gatesList = new ArrayList<Gate>();
     GatesList gates = new GatesList(gatesList);
    
     public Service() {
         createSomeGates();  
     }
     
     public Service(GatesList gateList) {
         gates = gateList;
     }
     
    public void run()
    {           
        staticFiles.location("/public");
        webSocket("/infosource", WsHandler.class);
        port(80);             
        redirect.get("/", "/Captain.html");        
        get("/gates", (req, res) -> handleGetGates(req,res));       
        get("/cgate", (req, res) -> handleGetCGate(req,res));      
        post("/gates", (req, res) -> handlePostGates(req,res));
    }
    
    public void stop() {
        spark.Spark.stop();
    }
    
    private void createSomeGates()
    {
        gatesList.add(new Gate(1,18.47429, 54.37914));
        gatesList.add(new Gate(2,18.47364, 54.37934));
        gatesList.add(new Gate(3,18.47291, 54.37953));
        gatesList.add(new Gate(4,18.47220, 54.37969));
        gatesList.add(new Gate(5,18.47149, 54.37985));
        gatesList.add(new Gate(6,18.47082, 54.37995));
        gatesList.add(new Gate(7,18.47021, 54.37982));
        gatesList.add(new Gate(8,18.46903, 54.37962));
        gatesList.add(new Gate(9,18.46838, 54.37977));
        gatesList.add(new Gate(10,18.46773, 54.37994));
        gatesList.add(new Gate(11,18.46710, 54.38010));
        gatesList.add(new Gate(20,18.46655, 54.38076));
        gatesList.add(new Gate(21,18.46606, 54.38100));
        gatesList.add(new Gate(22,18.46543, 54.38112));
        gatesList.add(new Gate(23,18.46490, 54.38126));    
    }
    public String handlePostGates(Request req, Response res)
    {
        res.type("text");
        res.header("Access-Control-Allow-Origin", "*");
        int selectedGateId = Integer.parseInt(req.body());
        gates.selected_id = selectedGateId;
        System.out.println("Captain has chosen gate " + gates.getGate(gates.selected_id) + "!");
        WsHandler.broadcastMessage("new gate");
        return "Gate selected properly";
    }
    
    public String handleGetGates(Request req, Response res)
    {
        res.type("application/json");
        res.header("Access-Control-Allow-Origin", "*");
        return gates.getGates();
    }
    public String handleGetCGate(Request req, Response res)
    {
        res.type("application/json");
        res.header("Access-Control-Allow-Origin", "*");
        System.out.println("I'm sending gate " + gates.getGate(gates.selected_id) + " to the Officer");
        return gates.getGate(gates.selected_id);  
    }
}
