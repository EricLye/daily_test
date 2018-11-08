package cn.chuangze.rzLib.test;

import cn.chuangze.rzLib.dao.BookDao;
import cn.chuangze.rzLib.module.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/11/8 16:04
 */
public class BookWebSearchTest {
	private final String[] codeList = {"A","B","C","D","E","F","G","H","I","J","K","N","O","P","Q","R","S","T","U","V","X","z"};
	private BookDao bookDao = new BookDao();
	/**
	 * 注意：没爬10个网页线程休眠5秒钟,保险起见，设置6秒钟，不然403
	 * @throws Exception
	 */
	@Test
	public void run() throws Exception{
		Date startDate = new Date();
		//查询图书馆所有图书信息
		List<Book> bookList = new ArrayList<>();
		int count = 0;

		//设置初始值
		String code = "A";
		int pageNum = 1;


		//查询初始值code在codeList中的下标
		int codeNum = 0;
		for (int j = 0;j < codeList.length; j ++){
			if (codeList[j].equals(code)){
				codeNum = j;
			}
		}


		outer:for (int i = codeNum;i < codeList.length; i++){
			int page = 1;
			do{
				if (count == 0 && i == codeNum){
					if ( page != pageNum) {
						page = pageNum;
					}
				}
				count ++;
				bookList.clear();
				String url = "http://61.162.219.218:8082/opac/search?q=" + codeList[i] + "&searchType=standard&isFacet=false&view=simple&searchWay=class&rows=10&sortWay=score&sortOrder=desc&searchWay0=marc&q0=&logical0=AND&page=" + page;
				try {
					bookList = getAllBooks(url);
					bookDao.insert(bookList);
				}catch (Exception e){
					System.out.println(url);
					e.printStackTrace();
					break outer;
				}
				System.out.println(codeList[i] + "类第" + page + "页成功");
				if (count % 10 == 0){
					Thread.sleep(5000);
				}else if(count % 100 == 0){
					Thread.sleep(15000);
				}else if(count % 300 == 0){
					Thread.sleep(30000);
				}
				page ++;
			}while (bookList.size() > 0);
		}
		Date endDate = new Date();
		System.out.println("=========>开始时间为： " + startDate.toString());
		System.out.println("=========>结束时间为： " + endDate.toString());
		System.out.println("=========>耗时： " + (endDate.getTime()-startDate.getTime()));
	}
	/**
	 * 根据url查询图书信息
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public List<Book> getAllBooks(String url) throws Exception{
		List<Book> bookList = new ArrayList<>();
//		String url = "http://61.162.219.218:8082/opac/search?q=A&searchType=standard&isFacet=false&view=simple&searchWay=class&rows=10&sortWay=score&sortOrder=desc&searchWay0=marc&q0=&logical0=AND&page=1";
		Document doc = Jsoup.connect(url).get();
		Element resultTable = doc.getElementsByClass("resultTable").first();
		Elements trs = resultTable.getElementsByTag("tr");


		for(Element tr : trs) {
			Book book = new Book();
			//图书封面
			Element bookCover = tr.getElementsByClass("coverTD").first();
			Element coverImg = bookCover.getElementsByTag("img").first();
			String src = coverImg.attr("src");
			String isbn = coverImg.attr("isbn").replaceAll("-","");
			String bookrecno = coverImg.attr("bookrecno");

			book.setSrc(src);
			book.setIsbn(isbn);
			book.setBookrecno(bookrecno);

			//图书详情
			Element bookDetail = tr.getElementsByClass("bookmetaTD").first();
			Element bookmeta = bookDetail.getElementsByClass("bookmeta").first();
			Elements divs = bookmeta.getElementsByTag("div");
			//	标题
			Element titleEle = divs.get(1).getElementsByTag("a").first();
			String title = titleEle.text();
			String titleHref = titleEle.attr("href");

			book.setTitle(title);
			book.setTitleHref(titleHref);

			//	著者
			Element authorEle = divs.get(2).getElementsByTag("a").first();
			String anthorName = authorEle.text();
			String authorHref = authorEle.attr("href");

			book.setAuthorName(anthorName);
			book.setAuthorHref(authorHref);

			//	出版社
			Element outerDiv = divs.get(3);
			if (outerDiv.text().split(":").length > 2) {
				String pubDate = outerDiv.text().split(":")[2].trim();

				book.setPubDate(pubDate);
			}

			Element pubEle = divs.get(3).getElementsByTag("a").first();
			String pubName = pubEle.text();
			String pubNameHref = pubEle.attr("href");
			//	文献类型
			String articleType = divs.get(4).text().split(":")[1].split(",")[0].replaceAll(" ","");

			book.setPubName(pubName);
			book.setPubNameHref(pubNameHref);
			book.setArticleType(articleType);

			//	索书号
			//	查看馆藏信息

			bookList.add(book);
		}
		return bookList;
	}
}
