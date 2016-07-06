package http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

import utils.ProxyRequestUtils;

public class Stepx_Monitor {

    private static String host = "xiaoxiwang.cn";
    private static String port = "8081";

    public static void monitor(Stepy_Handler handler) {
        HttpURLConnection conn = null;
        BufferedReader in = null;

        try {
            String urlName = "http://" + host + ":" + port + "/getAll";
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("Request--->" + urlName);
            URL realUrl = new URL(urlName);
            //打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection(ProxyRequestUtils.proxy);

            //设置通用的请求属性，请设置好请求时延  ！!!务必处理请求超时！！！
            conn.setReadTimeout(5000);

            //建立实际的连接
            conn.connect();

            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String body = IOUtils.toString(in);
            
            Map<String,String> map=new HashMap<String,String>();
            try{
                map=new ObjectMapper().readValue(body, Map.class);
            }catch(Exception e){
                map=new HashMap<String,String>();
            }
            
            handler.handle(map);

            System.out.println("Body--->" + body);

            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\r\n\r\n");
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
