package cn.chuangze.spider.util;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;


public class JdongMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 HttpClient client = new DefaultHttpClient();
	        //我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
	        //抓取的数据
	    	 try {
				//URLFecter.URLParser(client, "http://12366.chinatax.gov.cn/GzwsWebBLH_gzwsShow.do?id=7A57852AA57447559184C8248CD77DE6");
	    		 Map<String, Object> map = URLFecter.URLParser(client, "http://china.findlaw.cn/info/hy/hunyinfagui/hunyinfalv/80924.html");
	    		 Gson gson = new Gson();
	    		 String json = gson.toJson(map);
	    		 System.out.println(json);
	    	 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
