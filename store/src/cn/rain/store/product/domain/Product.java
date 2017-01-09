package cn.rain.store.product.domain;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Product implements Serializable {

	/**
	 * pid VARCHAR(32) NOT NULL, pname VARCHAR(50) DEFAULT NULL, market_price
	 * DOUBLE DEFAULT NULL, shop_price DOUBLE DEFAULT NULL, pimage VARCHAR(200)
	 * DEFAULT NULL, pdate DATE DEFAULT NULL, is_hot INT(11) DEFAULT NULL, pdesc
	 * VARCHAR(255) DEFAULT NULL, pflag INT(11) DEFAULT NULL, cid VARCHAR(32)
	 * DEFAULT NULL,
	 */
	private String pid;
	private String pname;
	private double market_price;// 市场价格
	private double shop_price;// 商城价格
	private String pimage;
	private Date pdate; // 商品的上架日期
	private int is_hot; // 是否为热门商品 0不热门 1热门
	private String pdesc;// 商品描述
	private int pflag;// 伪删除字段 0未删除 1已删除

	// 外键
	private Category category;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public double getMarket_price() {
		return market_price;
	}

	public void setMarket_price(double market_price) {
		this.market_price = market_price;
	}

	public double getShop_price() {
		return shop_price;
	}

	public void setShop_price(double shop_price) {
		this.shop_price = shop_price;
	}

	public String getPimage() {
		return pimage;
	}

	public void setPimage(String pimage) {
		this.pimage = pimage;
	}

	public Date getPdate() {
		return pdate;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	public int getIs_hot() {
		return is_hot;
	}

	public void setIs_hot(int is_hot) {
		this.is_hot = is_hot;
	}

	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	public int getPflag() {
		return pflag;
	}

	public void setPflag(int pflag) {
		this.pflag = pflag;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

}
