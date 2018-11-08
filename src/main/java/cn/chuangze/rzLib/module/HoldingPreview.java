package cn.chuangze.rzLib.module;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/11/8 11:13
 */
public class HoldingPreview {
	private Integer id;
	/**
	 * 索书号
	 */
	private String callNo;
	/**
	 * 所在馆
	 */
	private String curLib;
	private String curLibName;
	/**
	 * 所在馆藏地点
	 */
	private String curLocal;
	private String curLocalName;
	/**
	 * 在馆复本数
	 */
	private String copyCount;
	/**
	 * 书架号
	 */
	private String shelfNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCallNo() {
		return callNo;
	}

	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}

	public String getCurLib() {
		return curLib;
	}

	public void setCurLib(String curLib) {
		this.curLib = curLib;
	}

	public String getCurLibName() {
		return curLibName;
	}

	public void setCurLibName(String curLibName) {
		this.curLibName = curLibName;
	}

	public String getCurLocal() {
		return curLocal;
	}

	public void setCurLocal(String curLocal) {
		this.curLocal = curLocal;
	}

	public String getCurLocalName() {
		return curLocalName;
	}

	public void setCurLocalName(String curLocalName) {
		this.curLocalName = curLocalName;
	}

	public String getCopyCount() {
		return copyCount;
	}

	public void setCopyCount(String copyCount) {
		this.copyCount = copyCount;
	}

	public String getShelfNo() {
		return shelfNo;
	}

	public void setShelfNo(String shelfNo) {
		this.shelfNo = shelfNo;
	}
}
