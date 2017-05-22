/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import com.google.gson.Gson;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import service.Gate;
import service.GatesList;

public class GetGatesListTest {
    
    public GetGatesListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.out.println("getGates() test");
    }
    
    @After
    public void tearDown() {
    }

     //test for getGates() method from class GatesList
   @Test
    public void testGetGates() 
    {            
        Gson gson = new Gson();       
        ArrayList<Gate> gates = new ArrayList<>();
        gates.add(new Gate(8,18.46903, 54.37962));
        gates.add(new Gate(9,18.46838, 54.37977));
        gates.add(new Gate(10,18.46773, 54.37994));
        gates.add(new Gate(11,18.46710, 54.38010));
        String ex = gson.toJson(gates); 
        GatesList gl = new GatesList(gates);
        String res = gl.getGates();
        assertEquals(ex, res);
    }
}
