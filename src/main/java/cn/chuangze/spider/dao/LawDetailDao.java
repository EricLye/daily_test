package cn.chuangze.spider.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.chuangze.spider.entity.LawDetail;
import cn.chuangze.util.JdbcUtil;

public class LawDetailDao {
	
	public void insert(LawDetail detail) throws Exception{
		Connection conn=null;
		PreparedStatement pstm=null;
		try {
			conn = JdbcUtil.getConnection();
			//insert into users
			String sql = "insert into law_detail(titleNum,content,type,pid,law)values(?,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1,detail.getTitleNum());
			pstm.setString(2,detail.getContent());
			pstm.setInt(3,detail.getType());
			pstm.setInt(4,detail.getPid());
			pstm.setInt(5,detail.getLaw());
			pstm.executeUpdate();
		}finally {
			JdbcUtil.release(null, pstm, null);
		}
	}
	
	public void insertIncludeId(LawDetail detail) throws Exception{
		Connection conn=null;
		PreparedStatement pstm=null;
		try {
			conn = JdbcUtil.getConnection();
			//insert into users
			String sql = "insert into law_detail(titleNum,content,type,pid,law,id)values(?,?,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1,detail.getTitleNum());
			pstm.setString(2,detail.getContent());
			pstm.setInt(3,detail.getType());
			pstm.setInt(4,detail.getPid());
			pstm.setInt(5,detail.getLaw());
			pstm.setInt(6,detail.getId());
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
			String sql = "SELECT MAX(id) FROM law_detail";
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

	public void updatePid(LawDetail detail) throws Exception {
		Connection conn=null;
		PreparedStatement pstm=null;
		try {
			conn = JdbcUtil.getConnection();
			//insert into users
			String sql = "update law_detail set pid = ? where id = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1,detail.getPid());
			pstm.setInt(2,detail.getId());
			pstm.executeUpdate();
		}finally {
			JdbcUtil.release(null, pstm, null);
		}
		
	}

	public List<LawDetail> findAllByLaw(int lawId) throws Exception {
		List<LawDetail> detailList = new ArrayList<LawDetail>();
		Connection conn=null;
		PreparedStatement pstm=null;
		ResultSet ret = null;
		try {
			conn = JdbcUtil.getConnection();
			//insert into users
			String sql = "select titleNum,content,type,pid,law,id from law_detail where law = " + lawId;
			pstm = conn.prepareStatement(sql);
			ret = pstm.executeQuery();
			while(ret.next()){
				LawDetail detail = new LawDetail();
				detail.setTitleNum(ret.getString(1));
				detail.setContent(ret.getString(2));
				detail.setType(ret.getInt(3));
				detail.setPid(ret.getInt(4));
				detail.setLaw(ret.getInt(5));
				detail.setId(ret.getInt(6));
				detailList.add(detail);
			}
		}finally {
			JdbcUtil.release(null, pstm, null);
		}
		return detailList;
	}
}
