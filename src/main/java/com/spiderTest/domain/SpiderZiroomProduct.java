package com.spiderTest.domain;
/** 
 * @Description: ����ץȡҳ��  ʵ����
 * @ClassName: SpiderZiroomProduct 
 */
public class SpiderZiroomProduct {
	
	private long autoid;
	
	private String city ;//����
	private String origin;//������Դҳ��url
	
	private String productid = "";//¥��id
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
	private String productlink = "";//¥������
	private String name = "";//¥����
	
	private String price = "";//�۸�
	private String district = "";//����
	private String subway = "";//�������
	
}
