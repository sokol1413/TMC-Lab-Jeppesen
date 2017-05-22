package service;

import com.google.gson.Gson;
import java.util.ArrayList;

public class GatesList {
    public ArrayList<Gate> gates = new ArrayList<Gate>();
    public final Gson gson;
    public int selected_id = 0;
    
    public GatesList(ArrayList<Gate> g)
    {
        gson = new Gson();
        this.gates = g;           
    }
    
    public String getGates()
    {
        return gson.toJson(gates);
    }

    public String getGate(int id) 
    {     
        for (Gate g : gates)
            if (g.id == id)
                return gson.toJson(g);
        return "";
    }
}
