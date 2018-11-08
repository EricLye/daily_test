package cn.chuangze.spider.util;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author Y
 *
 */
public class URLFecter {
    public static Map<String, Object> URLParser (HttpClient client, String url) throws Exception {
        //用来接收解析的数据
    	Map<String, Object> map = new HashMap<String, Object>();
        //获取网站响应的html，这里调用了HTTPUtils类
        HttpResponse response = HTTPUtils.getRawHtml(client, url);      
        //获取响应状态码
        int StatusCode = response.getStatusLine().getStatusCode();
        //如果状态响应码为200，则获取html实体内容或者json文件
        if(StatusCode == 200){
            String entity = EntityUtils.toString (response.getEntity(),"gbk"); 
            map = JdParse.getHtmlData(entity);
//            EntityUtils.consume(response.getEntity());
        }else {
            //否则，消耗掉实体
            EntityUtils.consume(response.getEntity());
        }
        return map;
    }
    
    public static Map<String, Object> URLParser (HttpClient client, String url, int itemId, String type) throws Exception {
        //用来接收解析的数据
    	Map<String, Object> map = new HashMap<String, Object>();
        //获取网站响应的html，这里调用了HTTPUtils类
        HttpResponse response = HTTPUtils.getRawHtml(client, url);      
        //获取响应状态码
        int StatusCode = response.getStatusLine().getStatusCode();
        //如果状态响应码为200，则获取html实体内容或者json文件
        if(StatusCode == 200){
            String entity = EntityUtils.toString (response.getEntity(),"utf-8"); 
        	map = JdParse.getHtmlDataDetail(entity, itemId, type);
            EntityUtils.consume(response.getEntity());
        }else {
            //否则，消耗掉实体
            EntityUtils.consume(response.getEntity());
        }
        return map;
    }
}