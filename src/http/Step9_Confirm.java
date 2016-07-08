package http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import utils.Encription;

import utils.ProxyRequestUtils;
import utils.RecCodeAuto;
import utils.RecCodeAuto2;

public class Step9_Confirm {
	public static Map<String,Object> confirm(Map<String,Object> p){
		Map<String,Object> r=new HashMap<String,Object>();
		HttpsURLConnection conn=null;
		BufferedReader in=null;

		try
		{
			
			String urlName = "https://"+p.get("Province")+".122.gov.cn/veh1/netxh/validConfirmHphm?"
                                + "JSESSIONID-L="+getSessionId(String.valueOf(p.get("Set-Cookie")))
                                +"&captcha="+p.get("Code")
                                +"&selectInfo="+Encription.Encryption(p.get("Cph")+"");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("Request--->"+urlName);
			URL realUrl = new URL(urlName);
			//打开和URL之间的连接
			conn = (HttpsURLConnection)realUrl.openConnection(ProxyRequestUtils.proxy);
			
			//设置安全属性
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());

			//设置通用的请求属性，请设置好请求时延  ！!!务必处理请求超时！！！
			conn.setReadTimeout(5000);
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
			conn.setRequestProperty("Accept-Encoding","gzip, deflate");
			conn.setRequestProperty("Host", p.get("Province")+".122.gov.cn");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Pragma", "no-cache");
			conn.setRequestProperty("Cookie",String.valueOf(p.get("Set-Cookie")));

			//建立实际的连接
			conn.connect();

			//获取所有响应头字段
			Map<String,List<String>> map = conn.getHeaderFields();
			//遍历所有的响应头字段
			for (String key : map.keySet())
			{
				r.put(key, map.get(key));
				System.out.println(key + "--->" + map.get(key));
			}

			//定义BufferedReader输入流来读取URL的响应
			String body = "";
			in  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine())!= null)
			{
				body += "\n" + line;
			}
			//System.out.println("Body--->" + body);
			System.out.println("Body--->" + ((body!=null && body.replaceAll("\\s*", "").length()>250)?
					(body.replaceAll("\\s*", "").substring(0, 120)+" ... ... "+body.replaceAll("\\s*", "").
							substring(body.replaceAll("\\s*", "").length()-120, body.replaceAll("\\s*", "").length())):
								body.replaceAll("\\s*", "")));

			r.put("Body", body);
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\r\n\r\n");
		}
		catch(Exception e)
		{
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(conn!=null)
				{
					conn.disconnect();
				}
				if (in != null)
				{
					in.close();
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

		//System.out.println(r);
		return r;
	}
        
	public static String getSessionId(String cookie){
            String sessionid="";
            try{
                String s=cookie;
		int a=s.indexOf("JSESSIONID-L=");
		int b=s.indexOf(";", a);
		sessionid=s.substring(a+13, b);
            }catch(Exception e){
            }
	    return sessionid;
	}
        
	public static byte[] toByteArray(InputStream input) throws IOException {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    while (-1 != (n = input.read(buffer))) {
	        output.write(buffer, 0, n);
	    }
	    return output.toByteArray();
	}

	private static class TrustAnyTrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}
