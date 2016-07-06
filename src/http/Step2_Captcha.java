package http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import utils.ProxyRequestUtils;
import utils.RecCodeAuto;

public class Step2_Captcha {
	public static Map<String,Object> captcha(Map<String,Object> p){
		Map<String,Object> r=new HashMap<String,Object>();
		HttpsURLConnection conn=null;
		BufferedReader in=null;

		try
		{
			
			String urlName = "https://"+p.get("Province")+".122.gov.cn/captcha1";
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
			conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3)");
			conn.setRequestProperty("Accept-Encoding","gzip, deflate, sdch");
			conn.setRequestProperty("Host", p.get("Province")+".122.gov.cn");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Cache-Control", "max-age=0");
			conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
//			conn.setRequestProperty("Cookie","JSESSIONID-L="+p.get("JSESSIONID-L"));
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

			byte[] body=toByteArray(conn.getInputStream());
			System.out.println("Body--->" + Arrays.toString(body));
			r.put("Body", body);
			
			String code=RecCodeAuto.getCode(body);
			System.out.println("Code--->" + code);
			r.put("Code", code);
			
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
