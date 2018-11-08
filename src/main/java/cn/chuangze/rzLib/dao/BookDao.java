package cn.chuangze.rzLib.dao;

import cn.chuangze.rzLib.module.Book;
import cn.chuangze.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/11/8 11:53
 */
public class BookDao {
	public void insert(List<Book> bookList){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = JdbcUtil.getConnection();
			String sql = "insert into book (src,isbn,bookrecno,title,titleHref,authorName,authorHref,pubDate,pubName,pubNameHref,articleType) " +
					"values(?,?,?,?,?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
			connection.setAutoCommit(false);
			for (Book book :bookList) {
				preparedStatement.setString(1,book.getSrc());
				preparedStatement.setString(2,book.getIsbn());
				preparedStatement.setString(3,book.getBookrecno());
				preparedStatement.setString(4,book.getTitle());
				preparedStatement.setString(5,book.getTitleHref());
				preparedStatement.setString(6,book.getAuthorName());
				preparedStatement.setString(7,book.getAuthorHref());
				preparedStatement.setString(8,book.getPubDate());
				preparedStatement.setString(9,book.getPubName());
				preparedStatement.setString(10,book.getPubNameHref());
				preparedStatement.setString(11,book.getArticleType());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			connection.commit();
		}catch (Exception e){
			try{connection.rollback();}catch(Exception e1){}
			e.printStackTrace();
		}finally {
			JdbcUtil.release(null,preparedStatement,null);
		}
	}
}
