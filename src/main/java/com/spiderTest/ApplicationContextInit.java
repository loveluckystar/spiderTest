package com.spiderTest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextInit implements ApplicationContextAware {
  protected static final Logger logger = Logger.getLogger(ApplicationContextInit.class);

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;
	}

	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			logger.info("Load application context manual form xml file");
			applicationContext = (ApplicationContext) (new ClassPathXmlApplicationContext(
					"applicationContext.xml"));
		}
		return applicationContext;
	}
	
	public static <T> T getBean(String beanName){
		if (applicationContext == null) {
	    init();
		}
		if (null != applicationContext) {
			return (T) applicationContext.getBean(beanName);
		}
		return null;
	}
	
	
	public static<T> T getBean(Class<T> clazz)
	{
		if (applicationContext == null) {
	    init();
		}
		if (null != applicationContext) {
			return applicationContext.getBean(clazz);
		}
		return null;
	}
	
	private static void init() {
    if (applicationContext == null) {
    	 getApplicationContext();
    }
	}

}
