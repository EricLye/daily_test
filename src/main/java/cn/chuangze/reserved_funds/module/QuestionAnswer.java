package cn.chuangze.reserved_funds.module;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/10/13 13:51
 */
public class QuestionAnswer {
	private Integer id;
	private String question;
	private String answer;
	private Integer type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
