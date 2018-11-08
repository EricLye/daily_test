package cn.chuangze.reserved_funds.dao;

import cn.chuangze.reserved_funds.module.QuestionAnswer;
import cn.chuangze.util.JdbcUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/10/13 9:54
 */
public class QuestionAnswerDao {

	public QuestionAnswer getById(Integer id) throws Exception{
		Connection connection = JdbcUtil.getConnection("reserved_funds","127.0.0.1");
		Statement statement = connection.createStatement();
		String sql = "SELECT id,question,answer,type FROM `question_answer` WHERE id = " + id;
		ResultSet resultSet = statement.executeQuery(sql);

		QuestionAnswer questionAnswer = new QuestionAnswer();
		if (resultSet.next()){

			questionAnswer.setId(resultSet.getInt(1));
			questionAnswer.setQuestion(resultSet.getString(2));
			questionAnswer.setAnswer(resultSet.getString(3));
			questionAnswer.setType(resultSet.getInt(4));
		}
		return questionAnswer;
	}
	public void add(List<Map<String,Object>> list){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtil.getConnection("reserved_funds","127.0.0.1");
			connection.setAutoCommit(false);
			String sql = "INSERT INTO new_question_answer(question,answer,p_id) VALUES (?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
			for (Map map:list) {
				preparedStatement.setString(1, (String) map.get("question"));
				preparedStatement.setString(2, (String) map.get("answer"));
				preparedStatement.setInt(3,(Integer) map.get("id"));
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			connection.commit();
			System.out.println("插入数据库成功");
		}catch (Exception e){
			System.out.println("插入数据库失败");
			try{
				connection.rollback();
			}catch (Exception e1){}

			e.printStackTrace();
		}finally {
			JdbcUtil.release(null,preparedStatement,connection);
		}
	}
}
