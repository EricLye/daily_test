package cn.chuangze.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/11/8 10:33
 */
public class NothingTest {
	@Test
	public void nothingTest(){
		String[] strArr = {"索书号", "所在馆", "所在馆藏地点", "在馆复本数"};
		System.out.println(JSON.toJSON(strArr));
	}
}
