package utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class RecCodeIOTool {


	public static String getRootPath(){
		String rootPath=null;
		try {
			rootPath = RecCodeIOTool.class.getClass().getResource("/").toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if(rootPath.startsWith("/")){
			rootPath = rootPath.substring(1);
		}
		System.out.println(rootPath);
		return rootPath;
	}

	public static String getRootPath(String subPath){
		String allPath=null;
		try {
			allPath = RecCodeIOTool.class.getClass().getResource("/").toURI().getPath()+subPath;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if(allPath.startsWith("/")){
			allPath = allPath.substring(1);
		}
		return allPath;
	}

	public static byte[] getContent(String filePath) {  
		try{
			File file = new File(filePath);  
			long fileSize = file.length();  
			if (fileSize > Integer.MAX_VALUE) {  
				System.out.println("file too big...");  
				return null;  
			}  
			FileInputStream fi = new FileInputStream(file);  
			byte[] buffer = new byte[(int) fileSize];  
			int offset = 0;  
			int numRead = 0;  
			while (offset < buffer.length  
					&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
				offset += numRead;  
			}  
			if (offset != buffer.length) {  
				throw new IOException("Could not completely read file "  
						+ file.getName());  
			}  
			fi.close();  
			return buffer;  
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
