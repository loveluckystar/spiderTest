package com.spiderTest.service;

import java.util.List;

import com.spiderTest.SpiderCityEnums;
import com.spiderTest.SpiderEnums;
import com.spiderTest.domain.SpiderZiroomProduct;



public interface IZiroomSpiderService extends IBaseSpiderService<SpiderZiroomProduct> {


	public List<SpiderZiroomProduct> getListByConditions(SpiderCityEnums cityEnums, Integer pageCount, Integer pageSize, SpiderEnums typeEnums) throws Exception;
	
	public SpiderZiroomProduct getObjectDetailByConditions(SpiderCityEnums cityEnums, String targetId, SpiderEnums typeEnums) throws Exception;
	
	public Integer getPageCountByType(SpiderCityEnums cityEnums, SpiderEnums typeEnums, String pageUrlId, Integer pageSize) throws Exception;

	/**
	 * ץȡ����ҳ����
	 * @param service
	 * @param pageIndex
	 * @param pageSize    
	 * @return_type void
	 */
	public List<SpiderZiroomProduct> grabSearchData(Integer pageIndex, Integer pageSize);
	
	/**
	 * ץȡ����ҳ����
	 * @param pageIndex
	 * @param pageSize
	 * @param typeEnums
	 * @return    
	 * @return_type List<SpiderProduct>
	 */
	public List<SpiderZiroomProduct> grabSearchData(Integer pageIndex, Integer pageSize, SpiderEnums typeEnums);
    
	/**
	 * ץȡ����ҳ����
	 * @param service
	 * @param pageIndex
	 * @param pageSize    
	 * @return_type void
	 */
	public void grabDetailData(Integer pageIndex, Integer pageSize,List<SpiderZiroomProduct> list,boolean isSleep,int sleepTime);
	
	
	
}
