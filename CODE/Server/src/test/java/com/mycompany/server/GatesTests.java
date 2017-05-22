package com.mycompany.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import org.skyscreamer.jsonassert.ArrayValueMatcher;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.ArraySizeComparator;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

public class GatesTests {
    private Gates gates;
    
    @Before
    public void setUp() throws Exception {
        gates = new Gates();
    }
 
    @Test
    public void testNonExistentId() {
        String expected = "";
        assertEquals(expected,gates.getGate(0));
    }

    @Test
    public void testGate1DataValidity() {
        try {
            String expected = "{\"id\":1,\"lon\":18.47429,\"lat\":54.37914}";
            JSONAssert.assertEquals(expected, gates.getGate(1), true);
        } catch (JSONException ex) {
            Logger.getLogger(GatesTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    //ensures that minimum of 1 gate and maximum of 30 gates are found in gates JSON
    public void testGatesCount() {
        try {
            String data = gates.getGates();
            String expected = "[1,30]";
            JSONAssert.assertEquals(expected, data, new ArraySizeComparator(JSONCompareMode.LENIENT));
        } catch (JSONException ex) {
            Logger.getLogger(GatesTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       

}