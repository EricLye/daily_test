package cn.chuangze.spider.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


public class BaiduIndexSpider {
	/**
	作者：郭无心
	链接：https://www.zhihu.com/question/30626103/answer/83157368
	来源：知乎
	著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
	*/
	@Test
	public void testContentByUrl(){
		String url = "http://china.findlaw.cn/info/hy/hunyinfagui/hunyinfalv/80924.html";
		String result = getResult(url);
		System.out.println(result);
		// 使用正则匹配图片的src内容
//		String imgSrc = regexString(result, "src=(.*png)");
//		// 打印结果
//		System.out.println(imgSrc);
	}
	
	public static String getResult(String url){
		// 定义即将访问的链接
		//String url = "http://www.baidu.com";
		// 定义一个字符串用来存储网页内容
		String result = "";
		// 定义一个缓冲字符输入流
		BufferedReader in = null;
		try
		{
			// 将string转成url对象
			URL realUrl = new URL(url);
			// 初始化一个链接到那个url的连接
			URLConnection connection = realUrl.openConnection();
			// 开始实际的连接
			connection.connect();
			// 初始化 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"gbk"));
			// 用来临时存储抓取到的每一行的数据
			String line;
			while ((line = in.readLine()) != null)
			{
				// 遍历抓取到的每一行并将其存储到result里面
				result += line + "\n";
			}
		}catch (Exception e){
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}finally{// 使用finally来关闭输入流
			try{
				if (in != null){
					in.close();
				}
			} catch (Exception e2){
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	public static String regexString(String targetStr,String patternStr){
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(targetStr);
		if(matcher.find()){
			return matcher.group(1);
		}else{
			return "nothing";
		}
	}
	
	
}

