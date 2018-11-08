package cn.chuangze.spider.test;

import java.sql.Connection;
import java.util.List;

import cn.chuangze.spider.dao.LawDetailDao;
import cn.chuangze.spider.entity.LawDetail;
import cn.chuangze.util.JdbcUtil;
import org.junit.Test;

/**
 * @author LiZG
 */
public class TestDataTransportBetweenDifferentDB {
	private LawDetailDao detailDao = new LawDetailDao();
	
	@Test
	public void transferLawDetail() throws Exception{
		
		List<LawDetail> detailList = detailDao.findAllByLaw(1356);
		Connection conn = null;
		try{
			conn = JdbcUtil.getConnection("robot");
			conn.setAutoCommit(false);
			for(LawDetail thisOne:detailList){
				detailDao.insertIncludeId(thisOne);
			}
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			e.printStackTrace();
		}finally{
			JdbcUtil.release(null, null, conn);
		}
	}
}
