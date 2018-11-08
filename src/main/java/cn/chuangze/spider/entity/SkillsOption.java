package cn.chuangze.spider.entity;

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

public class SkillsOption  {

	private Integer id;
	private String name	;	
	private String contents; // 内容
	private String depict;	//描述
	private Integer skill; // 场景ID
	private Integer isdel; // 是否删除 0是 1否
	private Integer ispub; // 是否公共 0是 1否
	private String url;		//链接
	private Integer	user; // 创建人
	private Integer netType; //网络类型  0内网 默认 1外网
	/**
	 * 排序
	 */
	private Integer sort;
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Integer getSkill() {
		return skill;
	}
	public void setSkill(Integer skill) {
		this.skill = skill;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getUser() {
		return user;
	}
	public void setUser(Integer user) {
		this.user = user;
	}
	public Integer getNetType() {
		return netType;
	}
	public void setNetType(Integer netType) {
		this.netType = netType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepict() {
		return depict;
	}
	public void setDepict(String depict) {
		this.depict = depict;
	}
	
}