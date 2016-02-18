package com.spiderTest.service;

import java.util.List;

import com.spiderTest.SpiderCityEnums;
import com.spiderTest.SpiderEnums;



public abstract interface IBaseSpiderService<T>{
	
	/**
	 * 条件爬取List
	 * @param lastEffectId 上一次有效ID，默认为null
	 * @param typeEnums SPIDER_URLMAP_KEY_*
	 * @return
	 * @throws Exception
	 */
	public List<T> getListByConditions(SpiderCityEnums cityEnums, Integer pageCount, Integer pageSize, SpiderEnums typeEnums) throws Exception;
	
	/**
	 * 条件获取对象详情
	 * @param targetId
	 * @return
	 */
	public T getObjectDetailByConditions(SpiderCityEnums cityEnums, String targetId, SpiderEnums typeEnums) throws Exception;
	
	/**
	 * 根据数据类型获取页数
	 * @param cityEnums
	 * @param typeEnums 目标属性名称
	 * @param pageUrlId 默认是null，部分情况下，细分信息的列表成几个子列表页面进行爬取。
	 * @param pageSize 页号
	 * @return
	 * @throws Exception 
	 */
	public Integer getPageCountByType(SpiderCityEnums cityEnums, SpiderEnums typeEnums, String pageUrlId, Integer pageSize) throws Exception;
}
