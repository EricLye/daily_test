package cn.chuangze.rzLib.test;

import cn.chuangze.rzLib.module.HoldingPreview;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/11/8 16:04
 */
public class HoldingPreviewWebSearchTest {
	/**
	 * 获取馆藏内容
	 * @return
	 * @throws Exception
	 */
	public List<HoldingPreview> showHoldingPreview() throws Exception{
		List<HoldingPreview> holdingPreviewList = new ArrayList<>(8);
		String url = "http://61.162.219.218:8082/opac/book/holdingpreview/148917;jsessionid=19545D9DFC2F5871B16139D1F63289EE";
		Document doc = Jsoup.connect(url).get();
		Elements records = doc.getElementsByTag("record");
		for (Element record : records){
			HoldingPreview holdingPreview = new HoldingPreview();

			// "索书号"
			String callNo = record.getElementsByTag("callno").first().text();
			holdingPreview.setCallNo(callNo);
			// "所在馆"
			String curLib = record.getElementsByTag("curlib").first().text();
			String curLibName = record.getElementsByTag("curlibname").first().text();
			holdingPreview.setCurLib(curLib);
			holdingPreview.setCurLibName(curLibName);
			// "所在馆藏地点"
			String curLocal = record.getElementsByTag("curlocal").first().text();
			String curLocalName = record.getElementsByTag("curlocalname").first().text();
			holdingPreview.setCurLocal(curLocal);
			holdingPreview.setCurLocalName(curLocalName);
			// "在馆复本数"
			String copyCount = record.getElementsByTag("copycount").first().text();
			holdingPreview.setCopyCount(copyCount);
			//书架号
			Elements shelfNoEles = record.getElementsByTag("shelfno");
			if (!ObjectUtils.isEmpty(shelfNoEles)){
				String shelfNo = shelfNoEles.first().text();
				holdingPreview.setShelfNo(shelfNo);
			}
			holdingPreviewList.add(holdingPreview);
		}
		return holdingPreviewList;
	}

}
