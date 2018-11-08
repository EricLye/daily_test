package cn.chuangze.spider.dao;


import cn.chuangze.spider.entity.Skills;
import cn.chuangze.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author LiZG
 */
public class SkillsDao {
	
	public void insert(Skills skills) throws Exception{
		Connection conn=null;
		PreparedStatement pstm=null;
		try {
			conn = JdbcUtil.getConnection();
			//insert into users
			String sql = "insert into skills(title,typeId)values(?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1,skills.getTitle());
			pstm.setInt(2, skills.getType());
			pstm.executeUpdate();
		}finally {
			JdbcUtil.release(null, pstm, null);
		}
	}
	
	public Integer getId() throws Exception{
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet ret = null;
		Integer id = null;
		try {
			conn = JdbcUtil.getConnection();
			//insert into users
			String sql = "SELECT MAX(id) FROM skills";
			pstm = conn.prepareStatement(sql);
			ret = pstm.executeQuery();
			if(ret.next()){
				id = ret.getInt(1);
			}
		}finally {
			JdbcUtil.release(ret, pstm, null);
		}
		return id;
	}
}
