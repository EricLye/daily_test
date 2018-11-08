package cn.chuangze.spider.test;

import java.util.List;
import java.util.Map;

import cn.chuangze.spider.dao.LawDetailDao;
import cn.chuangze.spider.entity.LawDetail;
import cn.chuangze.spider.util.HTTPUtils;
import cn.chuangze.spider.util.URLFecter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import com.google.gson.Gson;


public class TestHtmlGet {
	private LawDetailDao detailDao = new LawDetailDao();
	@Test
	public void testHtmlGet(){
		HttpClient client = HttpClients.createDefault(); 
		String personalUrl = "http://china.findlaw.cn/info/hy/hunyinfagui/hunyinfalv/80924.html";
		HttpResponse response = HTTPUtils.getRawHtml(client, personalUrl);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testURLFecter() throws Exception{
		HttpClient client = HttpClients.createDefault(); 
		String url = "http://china.findlaw.cn/info/hy/hunyinfagui/hunyinfalv/80924.html";
		Map<String, Object> map = URLFecter.URLParser(client, url);
		List<LawDetail> chpList = (List<LawDetail>) map.get("chpList");
		List<LawDetail> articleList = (List<LawDetail>) map.get("articleList");
		for(LawDetail chp:chpList){
			detailDao.insert(chp);
			int id = detailDao.getId();
			for(LawDetail another:articleList){
				if(another.getPid() == chp.getPid()){
					another.setPid(id);
					detailDao.insert(another);
					another.setPid(0);
				}
			}
			chp.setId(id);
			chp.setPid(0);
			detailDao.updatePid(chp);
		}
		System.out.println(new Gson().toJson(map));
	}
}
