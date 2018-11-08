package cn.chuangze.spider.entity;



/**
 * 业务服务
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：
 * @作者：WangCY
 * @创建时间：2017-07-28 13:15:52
 */
/**
 * @describe update Skills 
 * 				add orgName and itemType
 * @author Y
 * @date 2018年5月23日
 */
 
public class Skills {

	private Integer id;
	private String title;	//标题
	private Integer	type; // 服务分类ID
	private Integer	isdel; // 是否删除 1是 0否
	private Integer	ispub; // 是否公共 1是 0否 
	private String depict;	//描述
	private Integer	userId; // 创建人
	/**
	 * 所属单位名称
	 */
	private String orgName;
	/**
	 * 事项类型
	 */
	private String itemType;
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	
	public String getDepict() {
		return depict;
	}
	public void setDepict(String depict) {
		this.depict = depict;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
