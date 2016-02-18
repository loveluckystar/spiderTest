package com.spiderTest;

public enum SpiderCityEnums {
	
	CITY_NAME_BEIJING("bj","bj","house","bj","bj"),
	CITY_NAME_SHANGHAI("sh","sh","sh","sh","sh"),
	CITY_NAME_GUANGZHOU("gz","gz","gz","gz","gz"),
	CITY_NAME_SHENZHEN("sz","sz","sz","sz","sz"),
	CITY_NAME_FOSHAN("fs","fs","fs","fs","fs");
	
	private String neteaseCity;
	
	private String soufangCity;
	
	private String jiaodianCity;
	
	private String lejuCity;
	
	private String xinlangCity;
	
	public String getNeteaseCity()
	{
		return neteaseCity;
	}
	
	public String getSoufangCity() {
		return soufangCity;
	}
	
	public String getJiaodianCity() {
		return jiaodianCity;
	}

	public String getLejuCity() {
		return lejuCity;
	}

	public String getXinlangCity() {
		return xinlangCity;
	}

	private SpiderCityEnums(String... args) {
		this.neteaseCity = args[0];
		this.soufangCity = args[1];
		this.jiaodianCity = args[2];
		this.lejuCity = args[3];
		this.xinlangCity = args[4];
	}
	
	public static SpiderCityEnums getCityEnumsByCity(String neteaseCity)
	{
		if(SpiderCityEnums.CITY_NAME_BEIJING.getNeteaseCity().equals(neteaseCity)){
			return SpiderCityEnums.CITY_NAME_BEIJING;
		}else if(SpiderCityEnums.CITY_NAME_SHANGHAI.getNeteaseCity().equals(neteaseCity)){
			return SpiderCityEnums.CITY_NAME_SHANGHAI;
		}else if(SpiderCityEnums.CITY_NAME_GUANGZHOU.getNeteaseCity().equals(neteaseCity)){
			return SpiderCityEnums.CITY_NAME_GUANGZHOU;
		}else if(SpiderCityEnums.CITY_NAME_SHENZHEN.getNeteaseCity().equals(neteaseCity)){
			return SpiderCityEnums.CITY_NAME_SHENZHEN;
		}else if(SpiderCityEnums.CITY_NAME_FOSHAN.getNeteaseCity().equals(neteaseCity)){
			return SpiderCityEnums.CITY_NAME_FOSHAN;
		}
		return SpiderCityEnums.CITY_NAME_BEIJING;
	}
}
