package cn.chuangze.spider.test;

import cn.chuangze.util.JdbcUtil;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author LiZG
 */
public class InsertLawDetailAndLawMenuTest {
    @Test
    public void testDetailAndMenu(){
        Connection conn = null;
        try{
            conn = JdbcUtil.getConnection();


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
