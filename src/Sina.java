

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.client.DefaultHttpClient;

import sun.net.www.http.HttpClient;

public class Sina {
	public static void main (String[] args){
		Sina s=new Sina();
		s.start("sh00");
//		DecimalFormat dl=new DecimalFormat("0000");
//		System.out.println(dl.format(2));0002
	}
	public void start(String s){
//		List<String> list =new ArrayList<String>();sh000, sh001, sh002, sh003
//		for(int i=0000;i<=9999;i++){
//			StringBuffer sb=new StringBuffer(s);
//			sb.append(i);
//			list.add(sb.toString());
//		}
//		System.out.println(list);
		
		DecimalFormat dl=new DecimalFormat("0000");
		int i=0;
		while(i<100){//这样做的目的是每次发100条
			List<String> list =new ArrayList<String>(100);
			for(int j=0;j<100;j++){
				list.add(s+dl.format(i++));//下一次循环是从101循环
			}
			String url=createUrl(list);
			try { 
				    List<String> records = request(url); 
				    List<String> codesAndName = getCodes(records); 
				    writeToFile(codesAndName); 
				 } catch (IOException e) { 
				           e.printStackTrace(); 
				   	} 
		}
//		for(int i=0;i<=9999;i++){
//			List<String> list =new ArrayList<String>();
//			for(int j=0;j<100;j++){
//				list.add(s+dl.format(i++));
//			}
//			list.add(s+dl.format(i));
//			System.out.println(list);
//		}
	}
	private String createUrl(List<String> list){
		StringBuffer sb=new StringBuffer("http://hq.sinajs.cn/list=");
		for(String s:list){
			sb.append(s+",");
		}
		return sb.toString();
	}
	private List<String> request(String url) throws IOException{
		 DefaultHttpClient hl=new DefaultHttpClient();
		 HttpGet hg=new HttpGet(url);
		 HttpResponse hr=hl.execute(hg);
		 List<String> recodes = new ArrayList<>(); 
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(hr.getEntity().getContent(),"gbk"))){ 
				String line = null; 
		 		while((line = reader.readLine())!= null){ 
		 		recodes.add(line); 
		 	} 
		} 
		return recodes; 
	}
	private List<String> getCodes(List<String> records){ 
		List<String> codes = new ArrayList<>(); 
		for (String record:records){ 
			if(record.contains(",")){ 
		            if(record.contains("sh")){ 
		                  String code = record.substring(record.indexOf("sh"),record.indexOf(",")); 
		                   codes.add(code); 
	                }else if (record.contains("sz")){ 
	                   String code = record.substring(record.indexOf("sz"),record.indexOf(",")); 
		                 codes.add(code); 
		               } 
		           }else { 
		
		 
	           } 
		   } 
	     return codes; 
     } 

	 private void writeToFile(List<String> codes){ 
		     try(FileWriter writer = new FileWriter(new File("D://codes.txt"),true)){ 
		            for (String code:codes){ 
		                 writer.write(code+","); 
		              } 
		             writer.flush(); 
		        } catch (IOException e) { 
		              e.printStackTrace(); 
		         } 
		     } 

}
