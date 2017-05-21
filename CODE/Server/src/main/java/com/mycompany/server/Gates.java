package com.mycompany.server;

import com.google.gson.Gson;
import java.util.ArrayList;

public class Gates {
    private final ArrayList<Gate> gates;
    public final Gson gson;
    
    public int selected_id = 0;
    
    Gates()
    {
        gson = new Gson();
        gates = new ArrayList<Gate>();
        
        gates.add(new Gate(1,18.47429, 54.37914));
        gates.add(new Gate(2,18.47364, 54.37934));
        gates.add(new Gate(3,18.47291, 54.37953));
        gates.add(new Gate(4,18.47220, 54.37969));
        gates.add(new Gate(5,18.47149, 54.37985));
        gates.add(new Gate(6,18.47082, 54.37995));
        gates.add(new Gate(7,18.47021, 54.37982));
        gates.add(new Gate(8,18.46903, 54.37962));
        gates.add(new Gate(9,18.46838, 54.37977));
        gates.add(new Gate(10,18.46773, 54.37994));
        gates.add(new Gate(11,18.46710, 54.38010));
        gates.add(new Gate(20,18.46655, 54.38076));
        gates.add(new Gate(21,18.46606, 54.38100));
        gates.add(new Gate(22,18.46543, 54.38112));
        gates.add(new Gate(23,18.46490, 54.38126));
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
