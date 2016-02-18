package com.spiderTest;

/**
 * ��ȡģ�����ö��
 *
 */
public enum SpiderEnums 
{
	//��¼��Ϣ
	SPIDER_LOGINMAP_KEY_USER("user"),
	SPIDER_LOGINMAP_KEY_PASSWORD("password"),
	SPIDER_LOGINMAP_KEY_ROOTURL("rootUrl"),
	SPIDER_LOGINMAP_KEY_VALIDATECODE("validateCode"),
	SPIDER_LOGINMAP_KEY_CHECKUSER("checkUser"),
	SPIDER_LOGINMAP_KEY_CHECKPASSWORD("checkPassword"),
	
	//URL����
	SPIDER_URLMAP_KEY_ZIROOMSEARCHINFO("ziroomSearchInfo"),
	SPIDER_URLMAP_KEY_XFSEARCHINFO("xfSearchInfo"),
	SPIDER_URLMAP_KEY_XFDETAILINFO("xfDetailInfo"),
	SPIDER_URLMAP_KEY_XFHOMEINFO("xfHomeInfo"),
	SPIDER_URLMAP_KEY_XFPHOTOINFO("xfPhotoInfo"),
	SPIDER_URLMAP_KEY_XFSEARCHZAISHOUINFO("xfSearchZaishouInfo"),
	SPIDER_URLMAP_KEY_XFSEARCHDAISHOUINFO("xfSearchDaishouInfo"),
	SPIDER_URLMAP_KEY_XFSEARCHSHOUWANINFO("xfSearchShouwanInfo"),
	SPIDER_URLMAP_KEY_XFSEARCHBJZAISHOUINFO("xfSearchBJZaishouInfo"),
	SPIDER_URLMAP_KEY_XFSEARCHBJDAISHOUINFO("xfSearchBJDaishouInfo"),
	SPIDER_URLMAP_KEY_XFSEARCHBJSHOUWANINFO("xfSearchBJShouwanInfo"),
	SPIDER_URLMAP_KEY_XFDONGTAIINFO("xfDongtaiInfo"),
	
	//ͨ��??
	SPIDER_MATCH_KEY_PAGEINDEX("#pageIndex#"),
	SPIDER_MATCH_KEY_PAGESIZE("#pageSize#"),
	SPIDER_MATCH_KEY_ID("#id#"),
	SPIDER_MATCH_KEY_CITY("#city#"),
	SPIDER_MATHC_KEY_XPATHPREFIX("#xpathPrefix#"),
	SPIDER_MATHC_KEY_XPATHPREFIXAPPENDIX("#xpathPrefixAppendix#"),
	
	//ͨ����ȡĿ��
	SPIDER_COMMON_TARGET_PAGECOUNT("pagecount"),
	
	//Ŀ����??
	SPIDER_COMMON_ATTR_MAPTYPE("mapType"),
	SPIDER_COMMON_ATTR_ATTRNAME("attrName"),
	SPIDER_COMMON_ATTR_XPATH("xpath"),
	
	//��������
	SPIDER_COMMON_MAPTYPE_SINGLE("single"),
	SPIDER_COMMON_MAPTYPE_MULTIPLE("multiple"),
	
	//���ݼ��??
	SPIDER_SPLIT_KEY_INTERVAL("#interval#");
	
	private String alias;
	
	SpiderEnums(String alias)
	{
		this.alias = alias;
	}
	
	public String getAlias()
	{
		return alias;
	}
}
