package cn.chuangze.spider.test;

import java.sql.Connection;

import cn.chuangze.spider.dao.SkillsDao;
import cn.chuangze.spider.dao.SkillsOptionDao;
import cn.chuangze.spider.dao.SkillsTypeDao;
import cn.chuangze.spider.entity.Skills;
import cn.chuangze.spider.entity.SkillsOption;
import cn.chuangze.spider.entity.SkillsType;
import cn.chuangze.util.JdbcUtil;
import org.junit.Test;

public class TestDao {
	private SkillsTypeDao skillsTypeDao = new SkillsTypeDao();
	private SkillsDao skillsDao = new SkillsDao();
	private SkillsOptionDao skillsOptionDao = new SkillsOptionDao();
	
	@Test
	public void testConn() throws Exception{
		Connection conn = JdbcUtil.getConnection();
		
	}
	
	@Test
	public void testSkillsType() throws Exception{
		Connection conn = JdbcUtil.getConnection();
		conn.setAutoCommit(false);
		SkillsType type = new SkillsType();
		type.setName("测试一");
		try{
			skillsTypeDao.insert(type);
			conn.commit();
			Integer id = skillsTypeDao.getId();
			System.out.println(id);
		}catch(Exception e){
			conn.rollback();
			e.printStackTrace();
		}finally{
			JdbcUtil.release(null, null, conn);
		}
	}
	@Test
	public void testSkills() throws Exception{
		Connection conn = JdbcUtil.getConnection();
		conn.setAutoCommit(false);
		Skills skills = new Skills();
		skills.setTitle("测试一");
		skills.setType(4);
		try{
			skillsDao.insert(skills);
			conn.commit();
			Integer id = skillsDao.getId();
			System.out.println(id);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void testSkillsOption(){
		SkillsOption option = new SkillsOption();
		option.setContents("测试一");
		option.setSkill(2);
		try{
			skillsOptionDao.insert(option);
			System.out.println("success");
		}catch(Exception e){
			System.out.println("fail");
			e.printStackTrace();
		}
	}
}
