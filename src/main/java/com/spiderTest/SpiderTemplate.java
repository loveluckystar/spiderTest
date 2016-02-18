package com.spiderTest;

import java.util.Map;

/**
 * spider模版
 *
 */
public abstract class SpiderTemplate {
	//登录信息website:... user:...  pwd:...
	protected Map<String, String> loginMap = null;
	
	//爬取url??
	protected Map<String, String> urlMap = null;
	
	protected Map<String, String> headerMap = null;
	
	public Map<String, String> getLoginMap() {
		return loginMap;
	}

	public void setLoginMap(Map<String, String> loginMap) {
		this.loginMap = loginMap;
	}

	public Map<String, String> getUrlMap() {
		return urlMap;
	}

	public void setUrlMap(Map<String, String> urlMap) {
		this.urlMap = urlMap;
	}

	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	public abstract Map<String, Map<String, String>> getXPathMapByType(SpiderEnums typeEnums);
}
