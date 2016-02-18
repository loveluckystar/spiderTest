package com.spiderTest;

import java.util.Map;

/**
 * ����spiderģ��
 *
 */
public class ZiroomSpiderTemplate extends SpiderTemplate
{
	//����ҳ xpathmap
	private Map<String, Map<String, String>> xfSearchInfo = null;
	//����ҳ ���� xpathmap
	private Map<String, Map<String, String>> xfSearchZaishouInfo = null;
	//����ҳ ���� xpathmap
	private Map<String, Map<String, String>> xfSearchDaishouInfo = null;
	//����ҳ ���� xpathmap
	private Map<String, Map<String, String>> xfSearchShouwanInfo = null;
	//��ҳ xpathmap
	private Map<String, Map<String, String>> xfHomeInfo = null;
	//����ҳ xpathmap
	private Map<String, Map<String, String>> xfDetailInfo = null;
	//����ͼҳ xpathmap
	private Map<String, Map<String, String>> xfPhotoInfo = null;
	//¥�̶�̬ҳ xpathmap
	private Map<String, Map<String, String>> xfDongtaiInfo = null;
	//����ҳ���� ���� xpathmap���ѷ���
	private Map<String, Map<String, String>> xfSearchBJZaishouInfo = null;
	//����ҳ �������� xpathmap���ѷ���
	private Map<String, Map<String, String>> xfSearchBJDaishouInfo = null;
	//����ҳ �������� xpathmap���ѷ���
	private Map<String, Map<String, String>> xfSearchBJShouwanInfo = null;
	

	public Map<String, Map<String, String>> getXPathMapByType(SpiderEnums typeEnums)
	{
		switch (typeEnums) {
			case SPIDER_URLMAP_KEY_XFSEARCHINFO: return this.getXfSearchInfo();
			case SPIDER_URLMAP_KEY_XFDETAILINFO: return this.getXfDetailInfo();
			case SPIDER_URLMAP_KEY_XFHOMEINFO: return this.getXfHomeInfo();
			case SPIDER_URLMAP_KEY_XFPHOTOINFO: return this.getXfPhotoInfo();
			case SPIDER_URLMAP_KEY_XFSEARCHZAISHOUINFO: return this.getXfSearchZaishouInfo();
			case SPIDER_URLMAP_KEY_XFSEARCHDAISHOUINFO: return this.getXfSearchDaishouInfo();
			case SPIDER_URLMAP_KEY_XFSEARCHSHOUWANINFO: return this.getXfSearchShouwanInfo();
			case SPIDER_URLMAP_KEY_XFDONGTAIINFO: return this.getXfDongtaiInfo();
			case SPIDER_URLMAP_KEY_XFSEARCHBJZAISHOUINFO: return this.getXfSearchBJZaishouInfo();
			case SPIDER_URLMAP_KEY_XFSEARCHBJDAISHOUINFO: return this.getXfSearchBJDaishouInfo();
			case SPIDER_URLMAP_KEY_XFSEARCHBJSHOUWANINFO: return this.getXfSearchBJShouwanInfo();
			default: return null;
		}
	}
	
	
	public Map<String, Map<String, String>> getXfSearchBJZaishouInfo() {
		return xfSearchBJZaishouInfo;
	}


	public void setXfSearchBJZaishouInfo(
			Map<String, Map<String, String>> xfSearchBJZaishouInfo) {
		this.xfSearchBJZaishouInfo = xfSearchBJZaishouInfo;
	}


	public Map<String, Map<String, String>> getXfSearchBJDaishouInfo() {
		return xfSearchBJDaishouInfo;
	}


	public void setXfSearchBJDaishouInfo(
			Map<String, Map<String, String>> xfSearchBJDaishouInfo) {
		this.xfSearchBJDaishouInfo = xfSearchBJDaishouInfo;
	}


	public Map<String, Map<String, String>> getXfSearchBJShouwanInfo() {
		return xfSearchBJShouwanInfo;
	}


	public void setXfSearchBJShouwanInfo(
			Map<String, Map<String, String>> xfSearchBJShouwanInfo) {
		this.xfSearchBJShouwanInfo = xfSearchBJShouwanInfo;
	}


	public Map<String, Map<String, String>> getXfSearchInfo() {
		return xfSearchInfo;
	}
	
	public void setXfSearchInfo(Map<String, Map<String, String>> xfSearchInfo) {
		this.xfSearchInfo = xfSearchInfo;
	}
	
	public Map<String, Map<String, String>> getXfHomeInfo() {
		return xfHomeInfo;
	}


	public void setXfHomeInfo(Map<String, Map<String, String>> xfHomeInfo) {
		this.xfHomeInfo = xfHomeInfo;
	}


	public Map<String, Map<String, String>> getXfDetailInfo() {
		return xfDetailInfo;
	}
	
	public void setXfDetailInfo(Map<String, Map<String, String>> xfDetailInfo) {
		this.xfDetailInfo = xfDetailInfo;
	}


	public Map<String, Map<String, String>> getXfPhotoInfo() {
		return xfPhotoInfo;
	}


	public void setXfPhotoInfo(Map<String, Map<String, String>> xfPhotoInfo) {
		this.xfPhotoInfo = xfPhotoInfo;
	}


	public Map<String, Map<String, String>> getXfSearchZaishouInfo() {
		return xfSearchZaishouInfo;
	}


	public void setXfSearchZaishouInfo(Map<String, Map<String, String>> xfSearchZaishouInfo) {
		this.xfSearchZaishouInfo = xfSearchZaishouInfo;
	}


	public Map<String, Map<String, String>> getXfSearchDaishouInfo() {
		return xfSearchDaishouInfo;
	}


	public void setXfSearchDaishouInfo(Map<String, Map<String, String>> xfSearchDaishouInfo) {
		this.xfSearchDaishouInfo = xfSearchDaishouInfo;
	}


	public Map<String, Map<String, String>> getXfSearchShouwanInfo() {
		return xfSearchShouwanInfo;
	}


	public void setXfSearchShouwanInfo(Map<String, Map<String, String>> xfSearchShouwanInfo) {
		this.xfSearchShouwanInfo = xfSearchShouwanInfo;
	}


	public Map<String, Map<String, String>> getXfDongtaiInfo() {
		return xfDongtaiInfo;
	}


	public void setXfDongtaiInfo(Map<String, Map<String, String>> xfDongtaiInfo) {
		this.xfDongtaiInfo = xfDongtaiInfo;
	}
}
