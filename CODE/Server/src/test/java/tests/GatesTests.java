package tests;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.ArraySizeComparator;
import service.Gate;
import service.GatesList;

public class GatesTests {
    private ArrayList<Gate> gates;
    private GatesList gl;
    private Gson gson; 
    
    @Before
    public void setUp() throws Exception {
        System.out.println("Launching gates tests");  
        gates = new ArrayList<>();
        gates.add(new Gate(8,18.46903, 54.37962));
        gates.add(new Gate(9,18.46838, 54.37977));
        gates.add(new Gate(10,18.46773, 18.46773));
        gates.add(new Gate(11,18.46710, 54.38010));
        
        gson = new Gson();
        gl = new GatesList(gates);
    }
 
    @Test
    public void testNonExistentId() {
        String ex = "";
        assertEquals(ex,gl.getGate(0));
    }

    @Test
    public void testGate1DataValidity() {
        try {
            String ex = "{\"id\":10,\"lon\":18.46773,\"lat\":18.46773}";
            String res = gl.getGate(10);
            System.out.println(ex);
            System.out.println(res);
            JSONAssert.assertEquals(ex, gl.getGate(10), true);
        } catch (JSONException e) {
            Logger.getLogger(GatesTests.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    //ensures that minimum of 1 gate and maximum of 30 gates are found in gates JSON
    public void testGatesCount() {
        try {
            String data = gl.getGates();
            String ex = "[1,30]";
            JSONAssert.assertEquals(ex, data, new ArraySizeComparator(JSONCompareMode.LENIENT));
        } catch (JSONException e) {
            Logger.getLogger(GatesTests.class.getName()).log(Level.SEVERE, null, e);
        }
    }       
    //tests gson parsing with getGates method
    @Test
    public void testGetGates() 
    {            
        String ex = gson.toJson(gates); 
        GatesList gl = new GatesList(gates);
        String res = gl.getGates();
        assertEquals(ex, res);
    }
}