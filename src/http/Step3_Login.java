package http;

import java.io.BufferedReader;
import java.io.IOException;
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

public class Step3_Login {
	
	public static Map<String,Object> login(Map<String,Object> p)
	{
		Map<String,Object> r=new HashMap<String,Object>();
		HttpsURLConnection conn=null;
		
		PrintWriter out = null;
		BufferedReader in = null;
		try
		{
			String urlName = "https://"+p.get("Province")+".122.gov.cn/user/m/login";
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
			conn.setRequestProperty("Accept", "application/x-ms-application, image/jpeg, application/xaml+xml, image/gif, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			conn.setRequestProperty("Referer","http://"+p.get("Province")+".122.gov.cn/m/login");
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("UA-CPU", "AMD64");
			conn.setRequestProperty("Accept-Encoding","gzip, deflate");
			conn.setRequestProperty("Host", p.get("Province")+".122.gov.cn");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Pragma", "no-cache");
			conn.setRequestProperty("Cookie", String.valueOf(p.get("Set-Cookie")));

			
			//发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			//发送请求参数
			String param="usertype=1&systemid=main&username="+p.get("Username")+"&password="+p.get("Password")+"&captcha="+p.get("Code");
			out.print(param);
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
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally
		{
			try
			{
				if(conn!=null)
				{
					conn.disconnect();
				}
				if (out != null)
				{
					out.close();
				}
				if (in != null)
				{
					in.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return r;
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
