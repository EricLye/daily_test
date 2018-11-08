package cn.chuangze.rzLib.module;

/**
 * @author LiZG
 * @version 1.0
 * @date 2018/11/7 16:47
 */
public class Book {
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 封面图片url
	 */
	private String src;
	/**
	 * 图书专属号---豆瓣相关
	 */
	private String isbn;
	/**
	 * 图书号：可根据接口查询索引号
	 */
	private	String bookrecno;
	/**
	 * 图书标题
	 */
	private	String title;
	/**
	 * 标题点击url
	 */
	private String titleHref;
	/**
	 * 著者
	 */
	private String authorName;
	/**
	 * 著者点击url
	 */
	private String authorHref;
	/**
	 * 出版日期
	 */
	private String pubDate;
	/**
	 * 出版社
	 */
	private String pubName;
	/**
	 * 出版社点击url
	 */
	private String pubNameHref;
	/**
	 * 文献类型
	 */
	private String articleType;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookrecno() {
		return bookrecno;
	}

	public void setBookrecno(String bookrecno) {
		this.bookrecno = bookrecno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleHref() {
		return titleHref;
	}

	public void setTitleHref(String titleHref) {
		this.titleHref = titleHref;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorHref() {
		return authorHref;
	}

	public void setAuthorHref(String authorHref) {
		this.authorHref = authorHref;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPubName() {
		return pubName;

	}

	public void setPubName(String pubName) {
		this.pubName = pubName;
	}

	public String getPubNameHref() {
		return pubNameHref;
	}

	public void setPubNameHref(String pubNameHref) {
		this.pubNameHref = pubNameHref;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}
}
