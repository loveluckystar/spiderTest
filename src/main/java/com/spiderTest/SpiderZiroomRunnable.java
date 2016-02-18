package com.spiderTest;

import java.util.List;

import com.spiderTest.domain.SpiderZiroomProduct;
import com.spiderTest.service.IZiroomSpiderService;



public class SpiderZiroomRunnable implements Runnable{
	public int pageIndex;
	public int pageSize;
	public IZiroomSpiderService service;
	public String city;
	public boolean isSleep;
	public int sleepTime=0;
	public int pageCount = 0;

    public SpiderZiroomRunnable(int pageIndex, int pageSize, IZiroomSpiderService service, String city,boolean isSleep,int sleepTime){
    	this(pageIndex, pageSize, service, city, isSleep, sleepTime, 0);
    }
	
    public SpiderZiroomRunnable(int pageIndex, int pageSize, IZiroomSpiderService service, String city,boolean isSleep,int sleepTime,int pageCount){
    	this.pageIndex = pageIndex;
    	this.pageSize = pageSize;
    	this.service = service;
    	this.city = city;
    	this.isSleep = isSleep;
    	this.sleepTime = sleepTime;
    	this.pageCount = pageCount;
    }
	
	public void run(){
		if(pageCount==0){
			System.out.println("----------"+city+":正在抓取第  "+pageIndex+"  页的数据！------------");
		}else{
			System.out.println("----------"+city+":正在抓取第  "+pageIndex+"  页的数据，总页数为:"+pageCount+"页！------------");
		}
		try {
			//抓取搜索页数据
			List<SpiderZiroomProduct> list = service.grabSearchData(pageIndex,pageSize);
			//根据搜索页数据，抓取详情页，补全数据
			service.grabDetailData(pageIndex,pageSize,list,isSleep,sleepTime);
		} catch (Exception e) {
			if(e instanceof java.net.ConnectException){
				try {	//连接异常 sleep 10分钟
					Thread.currentThread().sleep(1000*60*10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
	}
	
}
