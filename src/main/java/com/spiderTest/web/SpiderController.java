package com.spiderTest.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spiderTest.ApplicationContextInit;
import com.spiderTest.SpiderCityEnums;
import com.spiderTest.SpiderConstant;
import com.spiderTest.SpiderEnums;
import com.spiderTest.util.ThreadPoolUtil;

@Controller(value="spiderController")
@RequestMapping("/spider")
public class SpiderController extends BaseController {
	
	@Autowired
	@Qualifier("xinlangSpiderService")
	private IXfSpiderService xinlangSpiderService;
	
	/**
	 * 按城市抓取 搜房 的数据
	 * @param city    
	 * @param isGrabShouwanData 是否抓取售完的数据 
	 * @return_type void
	 * @author bjzhouling
	 */
	public void grabSoufangDataByCity(String city,Boolean isGrabShouwanData){
    	SpObserver.putCity(city);
    	Integer pageIndex = 1 ;
    	Integer pageSize = SpiderConstant.PAGESIZE_SOUFANG;
    	//SouFangSpiderServiceImpl service = (SouFangSpiderServiceImpl)ApplicationContextInit.getApplicationContext().getBean("souFangSpiderServiceImpl");
    	
    	//搜索页拆分为在售、待售和售完抓取， 北京和非北京处理情况不同
    	if(city.equals("bj")){
    		grabSoufangDataByCityAndType(pageIndex, pageSize, SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHBJZAISHOUINFO);
    		grabSoufangDataByCityAndType(pageIndex, pageSize, SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHBJDAISHOUINFO);
    		if(isGrabShouwanData){
    			grabSoufangDataByCityAndType(pageIndex, pageSize, SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHBJSHOUWANINFO);
    		}
    	}else{
    		grabSoufangDataByCityAndType(pageIndex, pageSize, SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHZAISHOUINFO);
    		grabSoufangDataByCityAndType(pageIndex, pageSize, SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHDAISHOUINFO);
    		if(isGrabShouwanData){
    			grabSoufangDataByCityAndType(pageIndex, pageSize, SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHSHOUWANINFO);
    		}
    	}
    	
    	
	}
	
	/**
	 * 按城市抓取 搜房某一页 的数据
	 * @param city    
	 * @param pageIndex 第几页
	 * @param typeEnums 抓取的页面类型
	 * @return_type void
	 * @author bjzhouling
	 */
	public void grabSoufangDataByCityAndPageIndex(String city, int pageIndex, SpiderEnums typeEnums){
    	if(pageIndex > 0 ){
    		SpObserver.putCity(city);
        	Integer pageSize = SpiderConstant.PAGESIZE_SOUFANG;
        	//SouFangSpiderServiceImpl service = (SouFangSpiderServiceImpl)ApplicationContextInit.getApplicationContext().getBean("souFangSpiderServiceImpl");
        	
        	System.out.println("----------正在抓取"+typeEnums+"的第  "+pageIndex+"  页的数据！------------");
			List<SpiderProduct> list = soufangSpiderService.grabSearchData(pageIndex,pageSize,typeEnums);
			if(!typeEnums.equals(SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHSHOUWANINFO)){
				soufangSpiderService.grabDetailData(pageIndex,pageSize,list,true,150);
			}
    	}
	}
	
	
	/**
	 * 按城市和类型（在售状态）抓取 搜房 的数据
	 * @param pageIndex
	 * @param pageSize
	 * @param typeEnums    
	 * @return_type void
	 * @author bjzhouling
	 */
	public void grabSoufangDataByCityAndType(Integer pageIndex,Integer pageSize, SpiderEnums typeEnums){
		// 获取总页数
    	Integer pageCount = 0;
		try {
			pageCount = soufangSpiderService.getPageCountByType(SpiderCityEnums.getCityEnumsByCity(SpObserver.getCity()), typeEnums, null, 1);
		} catch (Exception e) {
			pageCount = 0;
			e.printStackTrace();
		}
    	if(pageCount == 0){
    		System.out.println("----"+typeEnums+"获取的总页数为0 ！！---");
    		return;
    	}
    	System.out.println("---> 搜房 "+SpObserver.getCity()+"  "+typeEnums+"总页数为:"+pageCount+"页。");
    	
		//开始抓取数据，不走多线程
//		pageCount = 5 ;//设定一个小值，用于观察测试
		for( ;pageIndex<=pageCount; pageIndex++){
			//抓取搜索页数据
			System.out.println("----------"+SpObserver.getCity()+":正在抓取"+typeEnums+"的第  "+pageIndex+"  页的数据，总页数为:"+pageCount+"页！------------");
			List<SpiderProduct> list = soufangSpiderService.grabSearchData(pageIndex,pageSize,typeEnums);
			//根据搜索页数据，抓取详情页，补全数据 (售完状态的不抓)
			if(!typeEnums.equals(SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHSHOUWANINFO) && !typeEnums.equals(SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHBJSHOUWANINFO)){
				soufangSpiderService.grabDetailData(pageIndex,pageSize,list,true,2000);
			}
		}
		//开始抓取数据，走多线程
		//submitGrabData(pageCount,pageIndex,pageSize,soufangSpiderService,true,3000);
	}
	
	
	/**
	 * 开始抓取数据
	 * @param pageCount
	 * @param pageIndex
	 * @param pageSize    
	 * @return_type void
	 * @author bjzhouling
	 */
	public void submitGrabData(int pageCount,int pageIndex,int pageSize,IXfSpiderService service,boolean isSleep,int sleepTime){
    	//pageCount = 10 ;//设定一个小值，用于观察测试，大量抓取时需要注释掉此行
    	for( ;pageIndex<=pageCount; pageIndex++){
    		
    		ThreadPoolUtil.addTask(new SpiderRunnable(pageIndex,pageSize,service, SpObserver.getCity(),isSleep,sleepTime,pageCount));
    	}
	}
	
	/**
	 * 按城市抓取 搜狐焦点 的数据
	 * @param city    
	 * @return_type void
	 * @author bjzhouling
	 */
	public void grabJiaodianDataByCity(String city){
    	SpObserver.putCity(city);
    	Integer pageIndex = 1 ;
    	Integer pageSize = SpiderConstant.PAGESIZE_JIAODIAN;
    	// 获取总页数
    	Integer pageCount = 0;
		try {
			pageCount = jiaodianSpiderService.getPageCountByType(SpiderCityEnums.getCityEnumsByCity(SpObserver.getCity()), SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHINFO, null, 1);
		} catch (Exception e) {
			pageCount = 0;
			e.printStackTrace();
		}
    	if(pageCount == 0){
    		System.out.println("---焦点-"+city+"获取的总页数为0 ！！---");
    		return;
    	}
    	System.out.println("--->搜狐焦点 "+city+" 总页数为:"+pageCount+"页。");
    	
    	//开始抓取数据，走多线程
    	submitGrabData(pageCount,pageIndex,pageSize,jiaodianSpiderService,false,0);
    	//开始抓取数据，不走多线程
//    	pageCount = 2 ;//设定一个小值，用于观察测试
//    	for( ;pageIndex<=pageCount; pageIndex++){
//    		//抓取搜索页数据
//    		System.out.println("----------正在抓取第  "+pageIndex+"  页的数据，总页数为:"+pageCount+"页！------------");
//    		List<SpiderProduct> list = jiaodianSpiderService.grabSearchData(pageIndex,pageSize);
//    		//根据搜索页数据，抓取详情页，补全数据
//    		jiaodianSpiderService.grabDetailData(pageIndex,pageSize,list,false,0);
//    	}
	}
	
	/**
	 * 按城市抓取 安居客 的数据
	 * @param city    
	 * @return_type void
	 * @author bjzhouling
	 */
	public void grabLejuDataByCity(String city){
    	SpObserver.putCity(city);
    	Integer pageIndex = 1 ;
    	Integer pageSize = SpiderConstant.PAGESIZE_LEJU;
    	// 获取总页数
    	Integer pageCount = 0;
		try {
			pageCount = lejuSpiderService.getPageCountByType(SpiderCityEnums.getCityEnumsByCity(SpObserver.getCity()), SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHINFO, null, 1);
		} catch (Exception e) {
			pageCount = 0;
			e.printStackTrace();
		}
    	if(pageCount == 0){
    		System.out.println("---安居客-"+city+"获取的总页数为0 ！！---");
    		return;
    	}
    	System.out.println("---> 总页数为:"+pageCount+"页。");
    	
    	//开始抓取数据，走多线程
    	submitGrabData(pageCount,pageIndex,pageSize,lejuSpiderService,false,0);
    	//开始抓取数据，不走多线程
//    	pageCount = 2 ;//设定一个小值，用于观察测试
//    	for( ;pageIndex<=pageCount; pageIndex++){
//    		//抓取搜索页数据
//    		System.out.println("----------正在抓取第  "+pageIndex+"  页的数据，总页数为:"+pageCount+"页！------------");
//    		List<SpiderProduct> list = lejuSpiderService.grabSearchData(pageIndex,pageSize);
//    		//根据搜索页数据，抓取详情页，补全数据
//    		lejuSpiderService.grabDetailData(pageIndex,pageSize,list,false,0);
//    	}
	}
	
	
	
	/**
	 * 按城市抓取 新浪 乐居 的数据
	 * @param city    
	 * @return_type void
	 * @author bjzhouling
	 */
	public void grabXinlangDataByCity(String city){
    	SpObserver.putCity(city);
    	Integer pageIndex = 1 ;
    	Integer pageSize = SpiderConstant.PAGESIZE_XINLANG;
    	// 获取总页数
    	Integer pageCount = 0;
		try {
			pageCount = xinlangSpiderService.getPageCountByType(SpiderCityEnums.getCityEnumsByCity(SpObserver.getCity()), SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHINFO, null, 1);
		} catch (Exception e) {
			pageCount = 0;
			e.printStackTrace();
		}
    	if(pageCount == 0){
    		System.out.println("---新浪乐居-"+city+"获取的总页数为0 ！！---");
    		return;
    	}
    	System.out.println("---> 新浪乐居 "+city+" 总页数为:"+pageCount+"页。");
    	
    	//开始抓取数据，走多线程
    	submitGrabData(pageCount,pageIndex,pageSize,xinlangSpiderService,false,0);
    	//开始抓取数据，不走多线程
//    	pageCount = 5 ;//设定一个小值，用于观察测试
//    	for( ;pageIndex<=pageCount; pageIndex++){
//    		//抓取搜索页数据
//    		System.out.println("----------正在抓取第  "+pageIndex+"  页的数据，总页数为:"+pageCount+"页！------------");
//    		List<SpiderProduct> list = xinlangSpiderService.grabSearchData(pageIndex,pageSize);
//    		//根据搜索页数据，抓取详情页，补全数据
//    		xinlangSpiderService.grabDetailData(pageIndex,pageSize,list,false,0);
//    	}
	}
	
	
	public static void main(String[] args) {
		System.out.println("-----------spider抓取数据开始！----------");
		
		SpiderController controller = (SpiderController) ApplicationContextInit.getApplicationContext().getBean("spiderController");
//		String[] citys = {"bj", "sh", "gz", "sz" ,"fs"};
		String[] citys = { "bj"};
		for (String city : citys){
			SpObserver.putCity(city);
			
			//1.抓取搜房数据
			//1.1抓取搜房数据
//			controller.grabSoufangDataByCity(SpObserver.getCity(),false);
			
			//1.2单独抓取搜房的售完数据
			SpObserver.putCity(city);
	    	Integer pageIndex = 1 ;
	    	Integer pageSize = SpiderConstant.PAGESIZE_SOUFANG;
	    	if(city.equals("bj")){
	    		controller.grabSoufangDataByCityAndType(pageIndex,pageSize, SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHBJSHOUWANINFO);
	    	}else{
	    		controller.grabSoufangDataByCityAndType(pageIndex,pageSize, SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHSHOUWANINFO);
	    	}

			//2.抓取搜狐焦点数据
//			controller.grabJiaodianDataByCity(SpObserver.getCity());
			
			//3.抓取新浪乐居数据
//			controller.grabXinlangDataByCity(SpObserver.getCity());
			
			//4.抓取安居客数据
			//controller.grabLejuDataByCity(SpObserver.getCity());
		}
		
    	
    	System.out.println("-----------spider抓取数据结束！----------");
	}
	
	
}
