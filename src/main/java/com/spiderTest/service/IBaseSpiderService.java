package com.spiderTest.service;

import java.util.List;

import com.spiderTest.SpiderCityEnums;
import com.spiderTest.SpiderEnums;



public abstract interface IBaseSpiderService<T>{
	
	/**
	 * ������ȡList
	 * @param lastEffectId ��һ����ЧID��Ĭ��Ϊnull
	 * @param typeEnums SPIDER_URLMAP_KEY_*
	 * @return
	 * @throws Exception
	 */
	public List<T> getListByConditions(SpiderCityEnums cityEnums, Integer pageCount, Integer pageSize, SpiderEnums typeEnums) throws Exception;
	
	/**
	 * ������ȡ��������
	 * @param targetId
	 * @return
	 */
	public T getObjectDetailByConditions(SpiderCityEnums cityEnums, String targetId, SpiderEnums typeEnums) throws Exception;
	
	/**
	 * �����������ͻ�ȡҳ��
	 * @param cityEnums
	 * @param typeEnums Ŀ����������
	 * @param pageUrlId Ĭ����null����������£�ϸ����Ϣ���б�ɼ������б�ҳ�������ȡ��
	 * @param pageSize ҳ��
	 * @return
	 * @throws Exception 
	 */
	public Integer getPageCountByType(SpiderCityEnums cityEnums, SpiderEnums typeEnums, String pageUrlId, Integer pageSize) throws Exception;
}
