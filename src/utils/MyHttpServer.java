package utils;

import utils.MyTcpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.apache.commons.io.IOUtils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;
import static utils.MyTcpServer.map;
import java.util.HashMap;
import org.codehaus.jackson.map.ObjectMapper;
  
public class MyHttpServer {  
    public static void httpserverService(Integer port) throws IOException {  
        HttpServerProvider provider = HttpServerProvider.provider();  
        HttpServer httpserver =provider.createHttpServer(new InetSocketAddress(port), 100);
        httpserver.createContext("/", new MyHttpHandler());   
        httpserver.setExecutor(null);  
        httpserver.start();  
        System.out.println("server started");  
    }  
    
    static class MyHttpHandler implements HttpHandler {  
        public void handle(HttpExchange httpExchange) throws IOException {  
            System.out.println("#########");
            
            //http://localhost:8081/myApp?a=123
            String url=httpExchange.getRequestURI().toString();
            System.out.println(url);
            System.out.println("@@@@@@@@@@@@@@@");
            InputStream in = httpExchange.getRequestBody();
            System.out.println(IOUtils.toString(in));
            
            String responseMsg = "null"; 
            
            if(url!=null && url.indexOf("/getAll")==0){
                synchronized(map){
                    responseMsg=new ObjectMapper().writeValueAsString(map);
                    System.out.println(">>>>"+map);
                    map=new HashMap<String,String>();
                }
            }
            
            System.out.println("$$$$$$$$$$$$$$");
            
            httpExchange.sendResponseHeaders(200, responseMsg.length()); 
            OutputStream out = httpExchange.getResponseBody(); 
            out.write(responseMsg.getBytes());  
            out.flush();  
            httpExchange.close();                                 
        }  
    }  
    
    public static void main(String[] args) throws IOException {  
        new Thread(() -> {try{httpserverService(8081);}catch(Exception e){e.printStackTrace();}}).start();
        new Thread(() -> { try{MyTcpServer.start(new String[]{"6789"});}catch(Exception e){e.printStackTrace();}}).start();
    }  
}  