package cn.chuangze.rzLib.test;

import cn.chuangze.rzLib.module.Book;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/11/8 11:56
 */
public class GenerateDaoTest {
	@Test
	public void generateBookDaoTest(){
		Book book = new Book();
		Field[] fields = book.getClass().getDeclaredFields();
		String fieldStr = "";
		String getStr = "";
		int count = 0;
		for (Field field:fields){
			fieldStr += field.getName() + ",";
			String fieldName = upperCase(field.getName());
			getStr += "preparedStatement.setString(" + count + ",book.get" + fieldName + "());";
			count ++;
		}
		System.out.println(fieldStr);
		System.out.println(getStr);
	}

	private String upperCase(String str){
		return str.substring(0,1).toUpperCase().concat(str.substring(1).toLowerCase());
	}
}
