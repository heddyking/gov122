import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.apache.commons.io.IOUtils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;
import java.util.HashMap;
import org.codehaus.jackson.map.ObjectMapper;
  
public class HttpYzmServer {  
    public static void httpserverService(Integer port) throws IOException {  
        HttpServerProvider provider = HttpServerProvider.provider();  
        HttpServer httpserver =provider.createHttpServer(new InetSocketAddress(port), 100);
        httpserver.createContext("/", new MyHttpHandler());   
        httpserver.setExecutor(null);  
        httpserver.start();  
        System.out.println("http server started at "+port);  
    }  
    
    static class MyHttpHandler implements HttpHandler {  
        public void handle(HttpExchange httpExchange) throws IOException {  
            System.out.println(">>>>>>>>>>>>>>HTTP>>>>>>>>>>>>>>>");
            
            //http://localhost:8081/myApp?a=123
            String url=httpExchange.getRequestURI().toString();
            System.out.println("request: "+url);
//            InputStream in = httpExchange.getRequestBody();
//            System.out.println(IOUtils.toString(in));
           
            String responseMsg = "{}"; 
            
            if(url!=null && url.indexOf("/getAll")==0){
                synchronized(TcpYzmServer.map){
                    responseMsg=new ObjectMapper().writeValueAsString(TcpYzmServer.map);
                    System.out.println("response: "+TcpYzmServer.map);
                    TcpYzmServer.map=new HashMap<String,String>();
                }
            }
            
            System.out.println("<<<<<<<<<<<<<<<<HTTP<<<<<<<<<<<<<<<\n\n\n");
            
            httpExchange.sendResponseHeaders(200, responseMsg.length()); 
            OutputStream out = httpExchange.getResponseBody(); 
            out.write(responseMsg.getBytes());  
            out.flush();  
            httpExchange.close();                                 
        }  
    }  
    
    public static void main(String[] args) throws IOException {  
        new Thread(() -> {try{httpserverService(8081);}catch(Exception e){e.printStackTrace();}}).start();
        new Thread(() -> { try{TcpYzmServer.start(new String[]{"6789"});}catch(Exception e){e.printStackTrace();}}).start();
    }  
}  