package query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Api {
	//	public static void main(String [] args){
	//		System.out.println(getCityTfhd("410100000400"));
	//		
	//		System.out.println(getProvinceTfhd("sn"));
	//		
	//		List list=getAllTfhd();
	//		System.out.println(list.size());
	//		System.out.println(list);
	//
	//		List list2=getAllTfhdAsync();
	//		System.out.println(list2.size());
	//		System.out.println(list2);
	//	}

	public static List<Map<String,Object>> tfhd=new ArrayList<Map<String,Object>>();

	private  static Integer n=0;
	public static List<Map<String,Object>> getAllTfhdAsync(){
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();

		int m=0;
		for(Map<String,Object> province: Site.getAll()){
			for(Map<String,Object> city: (List<Map<String,Object>>)province.get("cs")){
				++m;
				Map map=new HashMap<String,Object>();
				map.put("Province", province.get("pid"));
				map.put("City", city.get("cid"));

				new Thread(() -> {
					try{
						result.addAll(Tfhd.parseBody(Tfhd.get(map).get("Body")+""));
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						synchronized(n){
							++n;
						}
					}
				}).start();

			}
		}

		for(int k=0;k<10;k++){
			if(n==m){
				tfhd=result;
				return result;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		tfhd=result;
		return result;
	}

	public static List<Map<String,Object>> getAllTfhd(){
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> province: Site.getAll()){
			for(Map<String,Object> city: (List<Map<String,Object>>)province.get("cs")){
				Map map=new HashMap<String,Object>();
				map.put("Province", province.get("pid"));
				map.put("City", city.get("cid"));
				result.addAll(Tfhd.parseBody(Tfhd.get(map).get("Body")+""));
			}
		}
		tfhd=result;
		return result;
	}

	public static List<Map<String,Object>> getProvinceTfhd(String pid){
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		Map province=Site.getProvinceById(pid);
		for(Map<String,Object> city: (List<Map<String,Object>>)province.get("cs")){
			Map map=new HashMap<String,Object>();
			map.put("Province", pid);
			map.put("City", city.get("cid"));
			result.addAll(Tfhd.parseBody(Tfhd.get(map).get("Body")+""));
		}
		tfhd=result;
		return result;
	}

	public static List<Map<String,Object>> getCityTfhd(String cid){
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		Map city=Site.getCityById(cid);
		Map map=new HashMap<String,Object>();
		map.put("Province", city.get("pid"));
		map.put("City", cid);
		result.addAll(Tfhd.parseBody(Tfhd.get(map).get("Body")+""));
		tfhd=result;
		return result;
	}
}
