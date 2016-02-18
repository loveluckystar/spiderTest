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
	 * ������ץȡ �ѷ� ������
	 * @param city    
	 * @param isGrabShouwanData �Ƿ�ץȡ��������� 
	 * @return_type void
	 * @author bjzhouling
	 */
	public void grabSoufangDataByCity(String city,Boolean isGrabShouwanData){
    	SpObserver.putCity(city);
    	Integer pageIndex = 1 ;
    	Integer pageSize = SpiderConstant.PAGESIZE_SOUFANG;
    	//SouFangSpiderServiceImpl service = (SouFangSpiderServiceImpl)ApplicationContextInit.getApplicationContext().getBean("souFangSpiderServiceImpl");
    	
    	//����ҳ���Ϊ���ۡ����ۺ�����ץȡ�� �����ͷǱ������������ͬ
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
	 * ������ץȡ �ѷ�ĳһҳ ������
	 * @param city    
	 * @param pageIndex �ڼ�ҳ
	 * @param typeEnums ץȡ��ҳ������
	 * @return_type void
	 * @author bjzhouling
	 */
	public void grabSoufangDataByCityAndPageIndex(String city, int pageIndex, SpiderEnums typeEnums){
    	if(pageIndex > 0 ){
    		SpObserver.putCity(city);
        	Integer pageSize = SpiderConstant.PAGESIZE_SOUFANG;
        	//SouFangSpiderServiceImpl service = (SouFangSpiderServiceImpl)ApplicationContextInit.getApplicationContext().getBean("souFangSpiderServiceImpl");
        	
        	System.out.println("----------����ץȡ"+typeEnums+"�ĵ�  "+pageIndex+"  ҳ�����ݣ�------------");
			List<SpiderProduct> list = soufangSpiderService.grabSearchData(pageIndex,pageSize,typeEnums);
			if(!typeEnums.equals(SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHSHOUWANINFO)){
				soufangSpiderService.grabDetailData(pageIndex,pageSize,list,true,150);
			}
    	}
	}
	
	
	/**
	 * �����к����ͣ�����״̬��ץȡ �ѷ� ������
	 * @param pageIndex
	 * @param pageSize
	 * @param typeEnums    
	 * @return_type void
	 * @author bjzhouling
	 */
	public void grabSoufangDataByCityAndType(Integer pageIndex,Integer pageSize, SpiderEnums typeEnums){
		// ��ȡ��ҳ��
    	Integer pageCount = 0;
		try {
			pageCount = soufangSpiderService.getPageCountByType(SpiderCityEnums.getCityEnumsByCity(SpObserver.getCity()), typeEnums, null, 1);
		} catch (Exception e) {
			pageCount = 0;
			e.printStackTrace();
		}
    	if(pageCount == 0){
    		System.out.println("----"+typeEnums+"��ȡ����ҳ��Ϊ0 ����---");
    		return;
    	}
    	System.out.println("---> �ѷ� "+SpObserver.getCity()+"  "+typeEnums+"��ҳ��Ϊ:"+pageCount+"ҳ��");
    	
		//��ʼץȡ���ݣ����߶��߳�
//		pageCount = 5 ;//�趨һ��Сֵ�����ڹ۲����
		for( ;pageIndex<=pageCount; pageIndex++){
			//ץȡ����ҳ����
			System.out.println("----------"+SpObserver.getCity()+":����ץȡ"+typeEnums+"�ĵ�  "+pageIndex+"  ҳ�����ݣ���ҳ��Ϊ:"+pageCount+"ҳ��------------");
			List<SpiderProduct> list = soufangSpiderService.grabSearchData(pageIndex,pageSize,typeEnums);
			//��������ҳ���ݣ�ץȡ����ҳ����ȫ���� (����״̬�Ĳ�ץ)
			if(!typeEnums.equals(SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHSHOUWANINFO) && !typeEnums.equals(SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHBJSHOUWANINFO)){
				soufangSpiderService.grabDetailData(pageIndex,pageSize,list,true,2000);
			}
		}
		//��ʼץȡ���ݣ��߶��߳�
		//submitGrabData(pageCount,pageIndex,pageSize,soufangSpiderService,true,3000);
	}
	
	
	/**
	 * ��ʼץȡ����
	 * @param pageCount
	 * @param pageIndex
	 * @param pageSize    
	 * @return_type void
	 * @author bjzhouling
	 */
	public void submitGrabData(int pageCount,int pageIndex,int pageSize,IXfSpiderService service,boolean isSleep,int sleepTime){
    	//pageCount = 10 ;//�趨һ��Сֵ�����ڹ۲���ԣ�����ץȡʱ��Ҫע�͵�����
    	for( ;pageIndex<=pageCount; pageIndex++){
    		
    		ThreadPoolUtil.addTask(new SpiderRunnable(pageIndex,pageSize,service, SpObserver.getCity(),isSleep,sleepTime,pageCount));
    	}
	}
	
	/**
	 * ������ץȡ �Ѻ����� ������
	 * @param city    
	 * @return_type void
	 * @author bjzhouling
	 */
	public void grabJiaodianDataByCity(String city){
    	SpObserver.putCity(city);
    	Integer pageIndex = 1 ;
    	Integer pageSize = SpiderConstant.PAGESIZE_JIAODIAN;
    	// ��ȡ��ҳ��
    	Integer pageCount = 0;
		try {
			pageCount = jiaodianSpiderService.getPageCountByType(SpiderCityEnums.getCityEnumsByCity(SpObserver.getCity()), SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHINFO, null, 1);
		} catch (Exception e) {
			pageCount = 0;
			e.printStackTrace();
		}
    	if(pageCount == 0){
    		System.out.println("---����-"+city+"��ȡ����ҳ��Ϊ0 ����---");
    		return;
    	}
    	System.out.println("--->�Ѻ����� "+city+" ��ҳ��Ϊ:"+pageCount+"ҳ��");
    	
    	//��ʼץȡ���ݣ��߶��߳�
    	submitGrabData(pageCount,pageIndex,pageSize,jiaodianSpiderService,false,0);
    	//��ʼץȡ���ݣ����߶��߳�
//    	pageCount = 2 ;//�趨һ��Сֵ�����ڹ۲����
//    	for( ;pageIndex<=pageCount; pageIndex++){
//    		//ץȡ����ҳ����
//    		System.out.println("----------����ץȡ��  "+pageIndex+"  ҳ�����ݣ���ҳ��Ϊ:"+pageCount+"ҳ��------------");
//    		List<SpiderProduct> list = jiaodianSpiderService.grabSearchData(pageIndex,pageSize);
//    		//��������ҳ���ݣ�ץȡ����ҳ����ȫ����
//    		jiaodianSpiderService.grabDetailData(pageIndex,pageSize,list,false,0);
//    	}
	}
	
	/**
	 * ������ץȡ ���ӿ� ������
	 * @param city    
	 * @return_type void
	 * @author bjzhouling
	 */
	public void grabLejuDataByCity(String city){
    	SpObserver.putCity(city);
    	Integer pageIndex = 1 ;
    	Integer pageSize = SpiderConstant.PAGESIZE_LEJU;
    	// ��ȡ��ҳ��
    	Integer pageCount = 0;
		try {
			pageCount = lejuSpiderService.getPageCountByType(SpiderCityEnums.getCityEnumsByCity(SpObserver.getCity()), SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHINFO, null, 1);
		} catch (Exception e) {
			pageCount = 0;
			e.printStackTrace();
		}
    	if(pageCount == 0){
    		System.out.println("---���ӿ�-"+city+"��ȡ����ҳ��Ϊ0 ����---");
    		return;
    	}
    	System.out.println("---> ��ҳ��Ϊ:"+pageCount+"ҳ��");
    	
    	//��ʼץȡ���ݣ��߶��߳�
    	submitGrabData(pageCount,pageIndex,pageSize,lejuSpiderService,false,0);
    	//��ʼץȡ���ݣ����߶��߳�
//    	pageCount = 2 ;//�趨һ��Сֵ�����ڹ۲����
//    	for( ;pageIndex<=pageCount; pageIndex++){
//    		//ץȡ����ҳ����
//    		System.out.println("----------����ץȡ��  "+pageIndex+"  ҳ�����ݣ���ҳ��Ϊ:"+pageCount+"ҳ��------------");
//    		List<SpiderProduct> list = lejuSpiderService.grabSearchData(pageIndex,pageSize);
//    		//��������ҳ���ݣ�ץȡ����ҳ����ȫ����
//    		lejuSpiderService.grabDetailData(pageIndex,pageSize,list,false,0);
//    	}
	}
	
	
	
	/**
	 * ������ץȡ ���� �־� ������
	 * @param city    
	 * @return_type void
	 * @author bjzhouling
	 */
	public void grabXinlangDataByCity(String city){
    	SpObserver.putCity(city);
    	Integer pageIndex = 1 ;
    	Integer pageSize = SpiderConstant.PAGESIZE_XINLANG;
    	// ��ȡ��ҳ��
    	Integer pageCount = 0;
		try {
			pageCount = xinlangSpiderService.getPageCountByType(SpiderCityEnums.getCityEnumsByCity(SpObserver.getCity()), SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHINFO, null, 1);
		} catch (Exception e) {
			pageCount = 0;
			e.printStackTrace();
		}
    	if(pageCount == 0){
    		System.out.println("---�����־�-"+city+"��ȡ����ҳ��Ϊ0 ����---");
    		return;
    	}
    	System.out.println("---> �����־� "+city+" ��ҳ��Ϊ:"+pageCount+"ҳ��");
    	
    	//��ʼץȡ���ݣ��߶��߳�
    	submitGrabData(pageCount,pageIndex,pageSize,xinlangSpiderService,false,0);
    	//��ʼץȡ���ݣ����߶��߳�
//    	pageCount = 5 ;//�趨һ��Сֵ�����ڹ۲����
//    	for( ;pageIndex<=pageCount; pageIndex++){
//    		//ץȡ����ҳ����
//    		System.out.println("----------����ץȡ��  "+pageIndex+"  ҳ�����ݣ���ҳ��Ϊ:"+pageCount+"ҳ��------------");
//    		List<SpiderProduct> list = xinlangSpiderService.grabSearchData(pageIndex,pageSize);
//    		//��������ҳ���ݣ�ץȡ����ҳ����ȫ����
//    		xinlangSpiderService.grabDetailData(pageIndex,pageSize,list,false,0);
//    	}
	}
	
	
	public static void main(String[] args) {
		System.out.println("-----------spiderץȡ���ݿ�ʼ��----------");
		
		SpiderController controller = (SpiderController) ApplicationContextInit.getApplicationContext().getBean("spiderController");
//		String[] citys = {"bj", "sh", "gz", "sz" ,"fs"};
		String[] citys = { "bj"};
		for (String city : citys){
			SpObserver.putCity(city);
			
			//1.ץȡ�ѷ�����
			//1.1ץȡ�ѷ�����
//			controller.grabSoufangDataByCity(SpObserver.getCity(),false);
			
			//1.2����ץȡ�ѷ�����������
			SpObserver.putCity(city);
	    	Integer pageIndex = 1 ;
	    	Integer pageSize = SpiderConstant.PAGESIZE_SOUFANG;
	    	if(city.equals("bj")){
	    		controller.grabSoufangDataByCityAndType(pageIndex,pageSize, SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHBJSHOUWANINFO);
	    	}else{
	    		controller.grabSoufangDataByCityAndType(pageIndex,pageSize, SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHSHOUWANINFO);
	    	}

			//2.ץȡ�Ѻ���������
//			controller.grabJiaodianDataByCity(SpObserver.getCity());
			
			//3.ץȡ�����־�����
//			controller.grabXinlangDataByCity(SpObserver.getCity());
			
			//4.ץȡ���ӿ�����
			//controller.grabLejuDataByCity(SpObserver.getCity());
		}
		
    	
    	System.out.println("-----------spiderץȡ���ݽ�����----------");
	}
	
	
}
