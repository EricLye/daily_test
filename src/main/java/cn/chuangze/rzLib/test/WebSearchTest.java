package cn.chuangze.rzLib.test;

import cn.chuangze.rzLib.dao.BookDao;
import cn.chuangze.rzLib.module.Book;
import cn.chuangze.rzLib.module.BookDetail;
import cn.chuangze.rzLib.module.HoldingPreview;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/11/7 11:28
 */
public class WebSearchTest {


	/**
	 * 测试
	 * @throws Exception
	 */
	@Test
	public void runTest() throws Exception{
		System.out.println(new Date().toString());
	}






	/**
	 function showHoldingPreview(bookrecno) {
	 var isDone = $.trim($("#express_" + bookrecno + " .holdingPreviewSpan").html());
	 var isDisplay = $("#express_" + bookrecno + " .holdingPreviewSpan").css("display");
	 if(isDisplay == "none" || isDone == "") {
	 if(isDone == "") {
	 $.ajax({
	 type: "GET",
	 url: "/opac/book/holdingpreview/;jsessionid=19545D9DFC2F5871B16139D1F63289EE" + bookrecno,
	 data: {},
	 dataType: "xml",//这里要用xml,如果用html,则在IE下无法解析这个数据
	 success: function(xml){
	 insertHoldingPreview(bookrecno, xml);
	 }
	 });
	 }
	 $("#express_" + bookrecno + " .holdingPreviewSpan").show();
	 $("#express_" + bookrecno + " a").html("隐藏馆藏信息");
	 } else {
	 $("#express_" + bookrecno + " .holdingPreviewSpan").hide();
	 $("#express_" + bookrecno + " a").html("查看馆藏信息");
	 }
	 }
	 function insertHoldingPreview(bookrecno, data) {
	 var tableHeader = new Array("索书号", "所在馆", "所在馆藏地点", "在馆复本数");
	 var glc="P2SD0633142";
	 if(glc=="P3JS0519059"){
	 tableHeader = new Array("架位号", "所在馆藏地点", "索书号", "所在馆", "在馆复本数");
	 }
	 var tableContent = new Array();
	 var records = $(data).find("record");
	 records.each(function(i) {
	 var callno = $(this).find("callno").text();
	 var curlib = $(this).find("curlib").text();
	 var curlibName = $(this).find("curlibName").text();
	 var curlocal = $(this).find("curlocal").text();
	 var curlocalName = $(this).find("curlocalName").text();
	 var copycount = $(this).find("copycount").text();
	 var shelfno = $(this).find("shelfno").text();
	 var dataArray = new Array(callno, curlibName, curlocalName, copycount);
	 if(glc=="P3JS0519059"){
	 dataArray = new Array(shelfno, curlocalName, callno, curlibName, copycount);
	 }
	 tableContent.push(dataArray);
	 });
	 var tableStr = createTable("expressTable", tableHeader, tableContent);
	 $("#express_" + bookrecno + " .holdingPreviewSpan").html(tableStr);
	 }
	 */
	private Document getHttpResponse(String url) throws Exception{
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		httpGet.addHeader("Host","61.162.219.218:8082");
		httpGet.addHeader("Referer","http://61.162.219.218:8082/opac/");
		HttpResponse response = httpClient.execute(httpGet);
		String entity = EntityUtils.toString (response.getEntity(),"utf-8");
		//采用Jsoup解析
		Document doc = Jsoup.parse(entity);
		return doc;
	}
}
