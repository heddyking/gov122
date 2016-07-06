import java.net.*;
import java.text.MessageFormat;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;

public class TcpYzmServer {
    
    public static Map<String,String> map=new HashMap<String,String>();
    
    private static final int BUFSIZE = 32;


    public static void start(String[] args) throws IOException {
        // TODO Auto-generated method stub
        if (args.length != 1)
            throw new IllegalArgumentException("Parameter(s):<Port>");
        int servPort = Integer.parseInt(args[0]);
        ServerSocket servSocket = new ServerSocket(servPort);
        System.out.println("tcp server started at "+args[0]);

        int recvMsgSize;
        byte[] receiveBuf = new byte[BUFSIZE];

        while (true) {

            Socket clntSocket = servSocket.accept();
            SocketAddress clientAddress = clntSocket.getRemoteSocketAddress();
            System.out.println(">>>>>>>>>>>>>>TCP>>>>>>>>>>>>>>>");
            System.out.println("Handling client at" + clientAddress);
            InputStream in = clntSocket.getInputStream();
            OutputStream out = clntSocket.getOutputStream();

            while ((recvMsgSize = in.read(receiveBuf)) != -1) {
                String str=IOUtils.toString(receiveBuf);
		str=str==null?"":str;
                System.out.println(str);
		System.out.println("size:"+str.length());
                
                if(str!=null && !str.isEmpty() && str.length()>=25 && str.indexOf("&")==18){
                    synchronized(map){
                         map.put(str.substring(0,18),str.substring(19,25));
                    }
                }
                out.write(receiveBuf, 0, recvMsgSize);
            }
            System.out.println("tcp client closed");
            System.out.println("<<<<<<<<<<<<<<<<TCP<<<<<<<<<<<<<<<<");
            clntSocket.close();
        }

    }
    
//    public static void main(String[] args) throws Exception{
//        start(new String[]{"6789"});
//    }
}