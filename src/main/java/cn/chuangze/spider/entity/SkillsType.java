package cn.chuangze.spider.entity;

import java.util.Date;


/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：
 * @作者：WangCY
 * @创建时间：2017-07-28 13:15:52
 */
public class SkillsType{

	private Integer id;
	private String	name; // 类型名称
	private Date createdate; // 创建日期
	private Integer	user; // 创建人
	private Integer	isdel; // 是否删除 0是 1否
	private Integer	ispub; // 是否公共 0是 1否 为0时则不显示到私有关联列表中
	private Integer parentId;	//所属父级Id（为 0是为最高级）
	private String descn;  // 简介
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Integer getUser() {
		return user;
	}
	public void setUser(Integer user) {
		this.user = user;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	public Integer getIspub() {
		return ispub;
	}
	public void setIspub(Integer ispub) {
		this.ispub = ispub;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	
}
