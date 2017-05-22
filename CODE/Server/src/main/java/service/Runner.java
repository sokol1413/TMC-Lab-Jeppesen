package service;

import java.util.ArrayList;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

public class Runner { 
       
    public static void main(String[] args) 
    {        
        Service s = new Service();
        s.run();
    }
}
