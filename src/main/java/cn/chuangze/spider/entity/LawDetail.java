package cn.chuangze.spider.entity;
/**
 * 法律详情
 * @author LiZG
 *
 */
public class LawDetail {
	private Integer id;
	private Integer law;//所属法律id
	private String titleNum;//章节条数
	private String content;//章节条内容
	private Integer pid;//上级id
	private Integer type;//类型 (1章 2节 3条)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLaw() {
		return law;
	}
	public void setLaw(Integer law) {
		this.law = law;
	}
	public String getTitleNum() {
		return titleNum;
	}
	public void setTitleNum(String titleNum) {
		this.titleNum = titleNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
