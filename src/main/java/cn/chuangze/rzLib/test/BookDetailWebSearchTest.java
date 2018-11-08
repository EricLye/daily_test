package cn.chuangze.rzLib.test;

import cn.chuangze.rzLib.module.BookDetail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/11/8 16:04
 */
public class BookDetailWebSearchTest {
	/**
	 * 根据url查询图书详情
	 * @param bookUrl
	 * @return cn.chuangze.rzLib.module.BookDetail
	 * @author LiZG
	 * @date 2018/11/08 10:27
	 */
	public BookDetail findBookDetail(String bookUrl) throws Exception{
//		String bookUrl = "http://61.162.219.218:8082/opac/book/148917";
		Document doc = Jsoup.connect(bookUrl).get();
		Element bookInfoTable = doc.getElementById("bookInfoTable");
		Elements trs = bookInfoTable.getElementsByTag("tr");
		//书名
		String title = trs.get(0).text();
		BookDetail bookDetail = new BookDetail();
		bookDetail.setTitle(title);

		String[] isbnArr = trs.get(1).text().replaceAll("：",":").split(":");
		//ISBN
		String isbn = isbnArr[1].substring(0,isbnArr[1].length()-3).replaceAll("-","").trim();
		//价格
		String price = isbnArr[2].trim();
		//语种
		String language = trs.get(2).text().split(":")[1].trim();

		bookDetail.setIsbn(isbn);
		bookDetail.setPrice(price);
		bookDetail.setLanguage(language);

		String[] titleAndAuthor = trs.get(3).text().split(":")[1].split("/");
		//题名
		String originTitle = titleAndAuthor[0].trim();
		//著者
		String author = titleAndAuthor[1].trim();
		//	版次
		String edition = trs.get(4).text().split(":")[1].trim();

		bookDetail.setOriginTitle(originTitle);
		bookDetail.setAuthor(author);
		bookDetail.setEdition(edition);

		String[] pubArr = trs.get(5).text().
				//将中文冒号转为英文冒号
						replaceAll("：",":").split(":");
		//出版地
		String pubCity = pubArr[2].substring(0,pubArr[2].length()-8).trim();
		//出版社
		String publisher = pubArr[3].substring(0,pubArr[3].length()-10).trim();
		//出版日期
		String pubDate = pubArr[4].trim();

		bookDetail.setPubCity(pubCity);
		bookDetail.setPublisher(publisher);
		bookDetail.setPubDate(pubDate);

		//载体形态
		String carrier = trs.get(6).text().split(":")[1].trim();
		bookDetail.setCarrier(carrier);

		//摘要
		String summary = trs.get(7).text().split(":")[1].trim();
		bookDetail.setSummary(summary);

		//使用对象附注
		String customer = trs.get(8).text().split(":")[1].trim();
		bookDetail.setCustomer(customer);

		//主题
		String theme = trs.get(9).text().split(":")[1].trim().replace(" ",",");
		bookDetail.setTheme(theme);

		//中图分类
		String category = trs.get(10).text().split(":")[1].trim();
		bookDetail.setCategory(category);

		//主要著者
		String mainAuthor = trs.get(11).text().split(":")[1].trim();
		bookDetail.setMainAuthor(mainAuthor);

		//标签
		String label = trs.get(12).text().split(":")[1].trim();
		bookDetail.setLabel(label);

		return bookDetail;
	}
}
