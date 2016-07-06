package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsRequestUtils
{
//	public static String base="https://192.168.0.53:2003/";
//	public static String base="https://202.120.40.140:2003/";
//	public static String base="https://192.168.1.108:2003/";
	public static String base="https://localhost:2003/";

	/**
	 * 向指定URL发送GET方法的请求
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendGet(String url , String param)
	{
		String result = "";
		BufferedReader in = null;
		try
		{
			String urlName = url + "?" + param;
			URL realUrl = new URL(urlName);
			//打开和URL之间的连接
			HttpsURLConnection conn = (HttpsURLConnection)realUrl.openConnection();

			//设置安全属性
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());

			//设置通用的请求属性，请设置好请求时延  ！!!务必处理请求超时！！！
			conn.setReadTimeout(100000);
			//			conn.setRequestProperty("accept", "*/*");
			//			conn.setRequestProperty("connection", "Keep-Alive");
			//			conn.setRequestProperty("user-agent", "Android/4.0");

			//建立实际的连接
			conn.connect();

			//获取所有响应头字段
			//			Map<String,List<String>> map = conn.getHeaderFields();
			//			//遍历所有的响应头字段
			//			for (String key : map.keySet())
			//			{
			//				System.out.println(key + "--->" + map.get(key));
			//			}

			//定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine())!= null)
			{
				result += "\n" + line;
			}
			//			InputStream is = conn.getInputStream();
			//			DataInputStream indata = new DataInputStream(is);
			//			String ret = "";
			//			while (ret != null) {
			//				ret = indata.readLine();
			//				if (ret != null && !ret.trim().equals("")) {
			//					result += new String(ret.getBytes("ISO-8859-1"), "UTF-8");
			//				}
			//			}
			conn.disconnect();
		}
		catch(Exception e)
		{
			System.out.println("发送GET请求出现异常！" + e);
			//			e.printStackTrace();
		}
		//使用finally块来关闭输入流
		finally
		{
			try
			{
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
		return result;
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendPost(String url,String param)
	{
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try
		{
			URL realUrl = new URL(url);
			//打开和URL之间的连接
			HttpsURLConnection conn = (HttpsURLConnection)realUrl.openConnection();

			//设置安全属性
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());

			//设置通用的请求属性，请设置好请求时延  ！!!务必处理请求超时！！！
			conn.setReadTimeout(5000);
			//			conn.setRequestProperty("accept", "*/*");
			//			conn.setRequestProperty("connection", "Keep-Alive");
			//			conn.setRequestProperty("user-agent", "Android/4.0");

			//发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			//发送请求参数
			out.print(param);
			//flush输出流的缓冲
			out.flush();

			//定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine())!= null)
			{
				result += "\n" + line;
			}
			conn.disconnect();
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
		return result;
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