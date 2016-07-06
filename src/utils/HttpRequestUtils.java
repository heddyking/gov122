package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestUtils
{
	public static String base="http://projects.chinaflamingo.com/";
	
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
			HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();

			//设置通用的请求属性，请设置好请求时延  ！!!务必处理请求超时！！！
			conn.setReadTimeout(5000);
//						conn.setRequestProperty("accept", "*/*");
//						conn.setRequestProperty("connection", "Keep-Alive");
//						conn.setRequestProperty("user-agent", "Android/4.0");

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
			HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();

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
	
	public static void main(String[] args){
		sendPost("http://localhost:6789","mm=12345");
	}
}