package cn.chuangze.rzLib.module;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/11/8 10:00
 */
public class BookDetail {
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 书名
	 */
	private String title;
	/**
	 * ISBN
	 */
	private String isbn;
	/**
	 * 价格
	 */
	private String price;
	/**
	 * 语种
	 */
	private String language;
	/**
	 * 题名
	 */
	private String originTitle;
	/**
	 * 著者
	 */
	private String author;
	/**
	 * 版次
	 */
	private String edition;
	/**
	 * 出版地
	 */
	private String pubCity;
	/**
	 * 出版社
	 */
	private String publisher;
	/**
	 * 出版日期
	 */
	private String pubDate;
	/**
	 * 载体形态
	 */
	private String carrier;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 使用对象附注
	 */
	private String customer;
	/**
	 * 主题
	 */
	private String theme;
	/**
	 * 中图分类
	 */
	private String category;
	/**
	 * 主要著者
	 */
	private String mainAuthor;
	/**
	 * 标签
	 */
	private String label;

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

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getOriginTitle() {
		return originTitle;
	}

	public void setOriginTitle(String originTitle) {
		this.originTitle = originTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getPubCity() {
		return pubCity;
	}

	public void setPubCity(String pubCity) {
		this.pubCity = pubCity;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMainAuthor() {
		return mainAuthor;
	}

	public void setMainAuthor(String mainAuthor) {
		this.mainAuthor = mainAuthor;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
