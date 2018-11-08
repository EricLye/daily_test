package cn.chuangze.spider.util;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * author qianyang 1563178220@qq.com
 * 用于将上面传下来的html解析，获取我们需要的内容
 * 解析方式，采用Jsoup解析，有不明白Jsoup的可以上网搜索API文档
 * Jsoup是一款很简单的html解析器
 */
public class JdParse {
    
    /**
     * @describe 政务大厅 浪潮接口 获取数据
     * @author Y
     * @date 2018年5月15日
     * @param html
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getHtmlData (String html) throws Exception{
	 
        //采用Jsoup解析
        Document doc = Jsoup.parse(html);
        //获取html标签中的内容
        //	标题
        Elements elements_title = doc.select("body");
        //	内容
        Map<String, Object> map = new ElementJxUtil().JsonAnalysis(elements_title);
        //返回数据
        return map;
    }
    
    /**
     * @describe 获取事项下的详细内容
     * @author Y
     * @date 2018年5月23日
     * @param html
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getHtmlDataDetail (String html, int itemId, String type) throws Exception{
   	 
        //采用Jsoup解析
        Document doc = Jsoup.parse(html);
        //获取html标签中的内容
        //	标题
        Elements elements_title = doc.select("body");
        //	内容
        Map<String, Object> map = new HashMap<String, Object>();  
        if(type.equals("GG"))
        	map = new ElementJxUtil().JsonAnalysisDetailGG(elements_title, itemId);
        else 
        	map = new ElementJxUtil().JsonAnalysisDetailXK(elements_title, itemId);
        //返回数据
        return map;
    }
    
}