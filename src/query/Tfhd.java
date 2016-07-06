package query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import utils.ProxyRequestUtils;

public class Tfhd {
//	public static void main(String[] args){
//		Map map=new HashMap<String,Object>();
//		map.put("Province", "ha");
//		map.put("City", "410100000400");
//		System.out.println(get(map));
//		System.out.println(parseBody(get(map).get("Body")+""));
//	}
	
	public static List<Map<String,Object>> parseBody(String json){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			Map<String,Object> tmp=new ObjectMapper().readValue(json, Map.class);
			for(Map<String,Object> map:(List<Map<String,Object>>)(((Map)((Map)tmp.get("data")).get("list")).get("content"))){
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	@SuppressWarnings("deprecation")
	public static Map<String,Object> get(Map<String,Object> p)
	{
		Map<String,Object> r=new HashMap<String,Object>();
		HttpURLConnection conn=null;
		
		PrintWriter out = null;
		BufferedReader in = null;
		try
		{
			String urlName = "http://"+p.get("Province")+".122.gov.cn/m/mvehxh/getTfhdList";
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("Request--->"+urlName);
			URL realUrl = new URL(urlName);
			//打开和URL之间的连接
			conn = (HttpURLConnection)realUrl.openConnection(ProxyRequestUtils.proxy);

			//设置通用的请求属性，请设置好请求时延  ！!!务必处理请求超时！！！
			conn.setReadTimeout(5000);
			conn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Accept-Encoding","gzip, deflate");
			conn.setRequestProperty("Host", p.get("Province")+".122.gov.cn");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Pragma", "no-cache");

			
			//发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			//发送请求参数
			Date now=new Date(System.currentTimeMillis());
			Date pre=new Date(System.currentTimeMillis());
			pre.setMonth(pre.getMonth()-2);
			String param="page=0&tfqk=1&glbm="+p.get("City")+"&startTime="+pre+"&endTime="+now;
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
}
