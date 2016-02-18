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
			System.out.println("----------"+city+":����ץȡ��  "+pageIndex+"  ҳ�����ݣ�------------");
		}else{
			System.out.println("----------"+city+":����ץȡ��  "+pageIndex+"  ҳ�����ݣ���ҳ��Ϊ:"+pageCount+"ҳ��------------");
		}
		try {
			//ץȡ����ҳ����
			List<SpiderZiroomProduct> list = service.grabSearchData(pageIndex,pageSize);
			//��������ҳ���ݣ�ץȡ����ҳ����ȫ����
			service.grabDetailData(pageIndex,pageSize,list,isSleep,sleepTime);
		} catch (Exception e) {
			if(e instanceof java.net.ConnectException){
				try {	//�����쳣 sleep 10����
					Thread.currentThread().sleep(1000*60*10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
	}
	
}
