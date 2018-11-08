package cn.chuangze.spider.entity;

import java.util.Date;

/**
 * 法律目录，存放某部法律除正文以外的其他相关信息
 * @author LiZG
 *
 */
public class LawMenu {
	private Integer id;
	private String data_attr;//资料属性
	private String org_type;//部门分类
	private String legislatures;//制定机关
	private String publish_no;//颁布文号
	private Date publish_date;//颁布日期
	private Date execute_date;//施行日期
	private Date invalid_date;//失效日期
	private String title;//法律标题
	private String contents;//法律内容
	private String about_file;//相关文件
	private String about_data;//相关资料
	private String initial;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getData_attr() {
		return data_attr;
	}
	public void setData_attr(String data_attr) {
		this.data_attr = data_attr;
	}
	public String getOrg_type() {
		return org_type;
	}
	public void setOrg_type(String org_type) {
		this.org_type = org_type;
	}
	public String getLegislatures() {
		return legislatures;
	}
	public void setLegislatures(String legislatures) {
		this.legislatures = legislatures;
	}
	public String getPublish_no() {
		return publish_no;
	}
	public void setPublish_no(String publish_no) {
		this.publish_no = publish_no;
	}
	public Date getPublish_date() {
		return publish_date;
	}
	public void setPublish_date(Date publish_date) {
		this.publish_date = publish_date;
	}
	public Date getExecute_date() {
		return execute_date;
	}
	public void setExecute_date(Date execute_date) {
		this.execute_date = execute_date;
	}
	public Date getInvalid_date() {
		return invalid_date;
	}
	public void setInvalid_date(Date invalid_date) {
		this.invalid_date = invalid_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getAbout_file() {
		return about_file;
	}
	public void setAbout_file(String about_file) {
		this.about_file = about_file;
	}
	public String getAbout_data() {
		return about_data;
	}
	public void setAbout_data(String about_data) {
		this.about_data = about_data;
	}
	public String getInitial() {
		return initial;
	}
	public void setInitial(String initial) {
		this.initial = initial;
	}
	
}
