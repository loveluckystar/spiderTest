package com.spiderTest;
/** 
 * @Description: spider所需的所有常量
 * @ClassName: SpiderConstant 
 * @author bjzhouling 
 */
public class SpiderConstant {
	
	public static final int PAGESIZE_SOUFANG = 10; 
	public static final int PAGESIZE_JIAODIAN = 10;
	public static final int PAGESIZE_LEJU = 20;
	public static final int PAGESIZE_XINLANG = 10;
	
	public static final String ORIGIN_ZIROOM = "http://www.ziroom.com";
	
	public static final String REGEX_NUMBER = "\\d*"; // 匹配数字的正则
	public static final String REGEX_DOUBLE_NUMBER = "(\\d|\\.)"; // 匹配小数的正则
	public static final String REGEX_SEVERAL_DOUBLE_NUMBER = "(\\d|\\.)+"; // 匹配多个小数的正则
	
	public static final String SEPERATOR = "##";
	public static final String APPENDIX	= "Appendix";
	
	
	public static final String HTTP_PREFIX = "http://"; // http前缀
	
	public static final String PRODUCT_NAME = "name";
	
	public static final String PRODUCT_ID = "productid";
	public static final String PRODUCT_LINK = "productlink";
	
	public static final String PRODUCT_PRICE = "price";
	
	public static final String PRODUCT_DISTRICT = "district";
	public static final String PRODUCT_SUBWAY = "subway";
	
}
