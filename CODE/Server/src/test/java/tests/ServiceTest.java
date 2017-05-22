package tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import service.Gate;
import service.Service;

public class ServiceTest 
{
    private ArrayList<Gate> gates = new ArrayList<Gate>();
    
    public void createGates()
    {
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
    
    public ServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.out.println("handleGetGates(Request req, Response res) test");  
        createGates();
    }
    
    @After
    public void tearDown() {
    }

    //test handleGetGates(Request req, Response res) method from Service class
    @Test
    public void TestGetGatesMethod() throws IOException 
    {      
        Service s = new Service();
        Gson g = new Gson();
        s.run(); 
        String ex = g.toJson(gates);
        
        CloseableHttpClient httpClient = HttpClients.custom().build();

        HttpGet httpGet = new HttpGet("http://localhost:80/gates");
        CloseableHttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null)
        {
            result.append(line);
        }
        System.out.println(result.toString());
        assertEquals(200, statusCode);
        assertEquals(ex, result.toString());
    }
}
    
    
    

