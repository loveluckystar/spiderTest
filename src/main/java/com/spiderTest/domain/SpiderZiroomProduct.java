package com.spiderTest.domain;
/** 
 * @Description: 自如抓取页面  实体类
 * @ClassName: SpiderZiroomProduct 
 */
public class SpiderZiroomProduct {
	
	private long autoid;
	
	private String city ;//城市
	private String origin;//数据来源页面url
	
	private String productid = "";//楼盘id
	public long getAutoid() {
		return autoid;
	}
	public void setAutoid(long autoid) {
		this.autoid = autoid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductlink() {
		return productlink;
	}
	public void setProductlink(String productlink) {
		this.productlink = productlink;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getSubway() {
		return subway;
	}
	public void setSubway(String subway) {
		this.subway = subway;
	}
	private String productlink = "";//楼盘链接
	private String name = "";//楼盘名
	
	private String price = "";//价格
	private String district = "";//区域
	private String subway = "";//地铁情况
	
}
