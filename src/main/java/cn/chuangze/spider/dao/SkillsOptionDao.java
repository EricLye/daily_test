package cn.chuangze.spider.dao;

import cn.chuangze.spider.entity.SkillsOption;
import cn.chuangze.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;


/**
 * @author LiZG
 */
public class SkillsOptionDao {
	public void insert(SkillsOption option) throws Exception{
		Connection conn=null;
		PreparedStatement pstm=null;
		try {
			conn = JdbcUtil.getConnection();
			//insert into users
			String sql = "insert into skills_option(contents,skill)values(?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, option.getContents());
			pstm.setInt(2, option.getSkill());
			pstm.executeUpdate();
			
		}finally {
			JdbcUtil.release(null, pstm, null);
		}
	}
	
}
