package http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import utils.ProxyRequestUtils;

public class Step5_PhoneYzm {
	public static Map<String,Object> phoneyzm(Map<String,Object> p){
		Map<String,Object> r=new HashMap<String,Object>();
		HttpsURLConnection conn=null;
		BufferedReader in=null;
                
                PrintWriter out = null;
		try
		{
			
			String urlName = "https://"+p.get("Province")+".122.gov.cn/veh1/netxh/createPhoneYzm";
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
			conn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
			conn.setRequestProperty("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.8,ja;q=0.6,en-US;q=0.4,en;q=0.2");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3)");
			conn.setRequestProperty("Accept-Encoding","gzip, deflate, sdch");
			conn.setRequestProperty("Host", p.get("Province")+".122.gov.cn");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Cookie",String.valueOf(p.get("Set-Cookie")));

			//发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			//发送请求参数
			out.print("");
			//flush输出流的缓冲
			out.flush();

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
		String s=cookie;
		int a=s.indexOf("JSESSIONID-L=");
		int b=s.indexOf(";", a);
		String sessionid=s.substring(a+13, b);
		return sessionid;
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
