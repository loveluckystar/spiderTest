package com.spiderTest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spiderTest.Spider;
import com.spiderTest.SpiderCityEnums;
import com.spiderTest.SpiderConstant;
import com.spiderTest.SpiderEnums;
import com.spiderTest.SpiderTemplate;
import com.spiderTest.ZiroomSpiderTemplate;
import com.spiderTest.domain.SpiderZiroomProduct;
import com.spiderTest.service.IZiroomSpiderService;
import com.spiderTest.util.Row;
import com.spiderTest.util.StringUtil;

/** 
 * @Description: TODO(������һ�仰��������������) 
 * @ClassName: SpiderService 
 * @author bjlixing 
 */
public class ZiroomSpiderService implements IZiroomSpiderService{
	
	protected Logger logger = LoggerFactory.getLogger(ZiroomSpiderService.class);
	//��ǰbean���Ƶ���ȡģ����
	private final static String SPIDER_NAME = "ziroomSpider";
	
	@Autowired
	@Qualifier(SPIDER_NAME)
	private ZiroomSpiderTemplate template;

	public List<SpiderZiroomProduct> getListByConditions(SpiderCityEnums cityEnums,
			Integer pageCount, Integer pageSize, SpiderEnums typeEnums) throws Exception {
		return getListByConditions(cityEnums, null, pageCount, pageSize, typeEnums);
	}

	/**
	 * ������ȡList
	 * @param targetid Ŀ��ID
	 * @param pageIndex ָ��ҳ��
	 * @param typeEnums SPIDER_URLMAP_KEY_*
	 * @return
	 * @throws Exception
	 */
	protected List<SpiderZiroomProduct> getListByConditions(SpiderCityEnums cityEnums, String targetId, Integer pageIndex, Integer pageSize, SpiderEnums typeEnums) throws Exception
	{
		return getListByConditions(cityEnums, targetId, pageIndex, pageSize, typeEnums, null);
	}
	
	/**
	 * ������ȡList
	 * @param targetid Ŀ��ID
	 * @param pageIndex ָ��ҳ��
	 * @param typeEnums SPIDER_URLMAP_KEY_*
	 * @return
	 * @throws Exception
	 */
	protected List<SpiderZiroomProduct> getListByConditions(SpiderCityEnums cityEnums, String targetId, Integer pageIndex, Integer pageSize, SpiderEnums typeEnums, String url) throws Exception
	{
		Spider spider = new Spider();
		
		//��ȡĿ��Url,���д���url,��ʹ�ô����url
		String targetUrl = null;
		if(StringUtil.isBlank(url)){
			targetUrl = getTargetUrlByType(cityEnums, pageIndex, pageSize, targetId, typeEnums);
		}else{
			targetUrl = url;
		}
		if(targetUrl == null){
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>+type:"+typeEnums.getAlias()+"+  targetUrl is NUll, please check the config files");
			return null;
		}
		System.out.println("spider---targetUrl:"+targetUrl+"---type:"+typeEnums.getAlias());
		//��ȡĿ��Document
		Document document = spider.getDocumentByHttpClient(targetUrl, getSpiderTemplate().getHeaderMap());
		if(document == null)
		{
			System.out.println("spider---targetUrl:"+targetUrl+">>>>>Doc��null");
			return null;
		}
		//ͨ��xpath��ȡNode�ڵ�
		Map<String, Map<String, String>> xPathMap = getXPathMapByType(typeEnums);
		System.out.println("spider---xPathMap:"+JSONObject.fromObject(xPathMap).toString());
		if(xPathMap != null)
		{
			Map<String, List<String>> attrValueMap = new HashMap<String, List<String>>();
			for(String target:xPathMap.keySet())
			{
				System.out.println("spider---target:"+target);
				Map<String, String> attrMap = xPathMap.get(target);
				if(SpiderEnums.SPIDER_COMMON_TARGET_PAGECOUNT.getAlias().equals(target))
				{
					//������ȡҳ���path
					continue;
				}
				
				List<String> attrValues = getAttrValuesFromXpath(attrMap.get(SpiderEnums.SPIDER_COMMON_ATTR_MAPTYPE.getAlias()), attrMap.get(SpiderEnums.SPIDER_COMMON_ATTR_XPATH.getAlias()), attrMap.get(SpiderEnums.SPIDER_COMMON_ATTR_ATTRNAME.getAlias()), document);
				//log.info("----------------->targetUrl="+targetUrl+",target=" + target +", attrMap=" + attrMap+", attrValues=" + attrValues);
				System.out.println("spider---attrValues:"+JSONArray.fromObject(attrValues).toString());
				attrValueMap.put(target, attrValues);
			}
			List<SpiderZiroomProduct> arrResuList = transFromAttrValueMap(cityEnums, attrValueMap);
			System.out.println("spider---arrResuList:"+JSONArray.fromObject(arrResuList).toString());
			return arrResuList;
		}
		return null;
	}
	
	/**
	 * �����õ�������ֵӳ���ϵ���䵽����
	 * @param attrValueMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<SpiderZiroomProduct> transFromAttrValueMap(SpiderCityEnums cityEnums, Map<String, List<String>> attrValueMap)
	{
		List<SpiderZiroomProduct> attList = new ArrayList<SpiderZiroomProduct>();
		
		if(attrValueMap != null)
		{
			List<Map<String, String>> valueMaps = null;
			for(String attrName:attrValueMap.keySet())
			{
				List<String> valuesList = attrValueMap.get(attrName);
				if(valueMaps == null)
				{
					valueMaps = new ArrayList<Map<String,String>>(valuesList.size());
				}
				for(int i=0; i<valuesList.size(); i++)
				{
					Map<String, String> finalMap = null;
					try 
					{
						finalMap = transFromAttrValue(attrName, valuesList.get(i));
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					finalMap.put("city", cityEnums.getNeteaseCity());
					if(valueMaps.size() == 0 || valueMaps.size()<=i)
					{
						valueMaps.add(i, finalMap);
					}
					else
					{
						for(String key:finalMap.keySet())
						{
							if(finalMap.get(key) != null
									&&!finalMap.get(key).isEmpty())
							{
								valueMaps.get(i).put(key, finalMap.get(key));
							}
						}
					}
				}
			}
			
			if(valueMaps != null)
			{
				for(Map<String, String> map:valueMaps)
				{
					
					try {
						Object object = ZiroomSpiderService.class.newInstance();
						BeanUtils.populate(object, map);
						attList.add((SpiderZiroomProduct)object);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return attList;
	}
	
	public SpiderZiroomProduct getObjectDetailByConditions(SpiderCityEnums cityEnums,
			String targetId, SpiderEnums typeEnums) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getPageCountByType(SpiderCityEnums cityEnums, SpiderEnums typeEnums,
			String pageUrlId, Integer pageSize) throws Exception {
		//��ȡĿ��Url��Ĭ�ϴ�0ҳץȡҳ
		String targetUrl = getTargetUrlByType(cityEnums, 1, pageSize,pageUrlId, typeEnums);
		if(targetUrl == null){
			logger.info(">>>>>>>>>>>>>>>>>>> type:"+typeEnums.getAlias()+" targetUrl��ȡΪ��");
			return 0;
		}
		Spider spider = new Spider();
		//��ȡĿ��Document
		Document document = spider.getDocumentByHttpClient(targetUrl, getSpiderTemplate().getHeaderMap());
		if(document == null){
			return 0;
		}
		//ͨ��xpath��ȡNode�ڵ�
		Map<String, Map<String, String>> xPathMap = getXPathMapByType(typeEnums);
		//�����ȡ����xpath·����Ϊ�գ������xpath·����document�л�ȡ��Ӧ�ڵ�����
		if(xPathMap != null){
			for(String target:xPathMap.keySet()){
				Map<String, String> attrMap = xPathMap.get(target);
				// ҳ�ſ��ܴ��ڲ�ͬλ��
				if(SpiderEnums.SPIDER_COMMON_TARGET_PAGECOUNT.getAlias().equals(target) || (SpiderEnums.SPIDER_COMMON_TARGET_PAGECOUNT.getAlias()+"Appendix").equals(target)){
					List<String> attrValues = new ArrayList<String>();
					if(StringUtil.isEmpty(attrMap.get(SpiderEnums.SPIDER_COMMON_ATTR_XPATH.getAlias()))){
						//ģ��Լ������xpathΪ�գ���ֱ��ȥattrName�µ�value
						attrValues.add(attrMap.get(SpiderEnums.SPIDER_COMMON_ATTR_ATTRNAME.getAlias()));
					}else{
						attrValues = getAttrValuesFromXpath(SpiderEnums.SPIDER_COMMON_MAPTYPE_SINGLE.getAlias(), attrMap.get(SpiderEnums.SPIDER_COMMON_ATTR_XPATH.getAlias()), 
								attrMap.get(SpiderEnums.SPIDER_COMMON_ATTR_ATTRNAME.getAlias()),
								document);
					}
					System.out.println("spider---attrValues�� "+JSONArray.fromObject(attrValues).toString());
					if(attrValues != null && attrValues.size() == 1){
						Map<String, String> map = transFromAttrValue(SpiderEnums.SPIDER_COMMON_TARGET_PAGECOUNT.getAlias(), attrValues.get(0));
						String strPageCount = map.get(SpiderEnums.SPIDER_COMMON_TARGET_PAGECOUNT.getAlias());
						return (strPageCount==null?1:Integer.parseInt(strPageCount));
					}else{
						if((SpiderEnums.SPIDER_COMMON_TARGET_PAGECOUNT.getAlias()).equals(target)){
							continue;
						}
					}
					break;
				}
			}
		}
		return null;
	}
	
	/**
	 * ����Dom��ĳ������,�����д���
	 * @return
	 */
	protected  Map<String, String> transFromAttrValue(String attrName, String attrValue){
		Map<String, String> valueMap = new HashMap<String, String>();
		if(StringUtils.isBlank(attrValue)){
			return valueMap;
		}
		attrValue = attrValue.trim();
		System.out.println("attrName:"+attrName+"--attrValue:"+attrValue);
		if(attrName.equals(SpiderConstant.PRODUCT_NAME)){// ¥����
			valueMap.put(SpiderConstant.PRODUCT_NAME, attrValue);
		}else if(attrName.equals(SpiderConstant.PRODUCT_ID)){// ¥��id
			valueMap.put(SpiderConstant.PRODUCT_ID, attrValue);
		}else if(attrName.equals(SpiderConstant.PRODUCT_PRICE)){// ¥�̼۸�
			valueMap.put(SpiderConstant.PRODUCT_PRICE, attrValue);
		}else if(attrName.equals(SpiderConstant.PRODUCT_DISTRICT)){// ¥������
			valueMap.put(SpiderConstant.PRODUCT_DISTRICT, attrValue);
		}else if(attrName.equals(SpiderConstant.PRODUCT_DISTRICT)){// ¥�̵���
			valueMap.put(SpiderConstant.PRODUCT_DISTRICT, attrValue);
		}else if(attrName.equals(SpiderEnums.SPIDER_COMMON_TARGET_PAGECOUNT.getAlias())){
			System.out.println("pagecount--attrValue:"+attrValue);
			int pageCount = StringUtil.getInt(attrValue, 0)/SpiderConstant.PAGESIZE_JIAODIAN;
			if(StringUtil.getInt(attrValue, 0)%SpiderConstant.PAGESIZE_JIAODIAN > 0){
				pageCount+=1;
			}
			valueMap.put(SpiderEnums.SPIDER_COMMON_TARGET_PAGECOUNT.getAlias(), pageCount+"");
		}else if(attrName.equals(SpiderEnums.SPIDER_COMMON_TARGET_PAGECOUNT.getAlias()+"Appendix")){
			int pageCount = StringUtil.getInt(attrValue, 0)/SpiderConstant.PAGESIZE_JIAODIAN;
			if(StringUtil.getInt(attrValue, 0)%SpiderConstant.PAGESIZE_JIAODIAN > 0){
				pageCount+=1;
			}
			valueMap.put(SpiderEnums.SPIDER_COMMON_TARGET_PAGECOUNT.getAlias(), pageCount+"");
		}
		return valueMap;
	}
	
	/**
	 * ��ȡ���Զ�Ӧ��value
	 * @param mapType ������attrNameӳ������,1-1,1-n
	 * @param xPath
	 * @param attrName
	 * @param document
	 * @return
	 */
	private List<String> getAttrValuesFromXpath(String mapType, String xPath, String attrName, Document document)
	{
		
		Spider spider = new Spider();
		NodeList nodeList = spider.getNodeList(document, xPath);
		
		List<String> attrValues = new ArrayList<String>();
		if(nodeList != null){
			if(SpiderEnums.SPIDER_COMMON_MAPTYPE_SINGLE.getAlias().equals(mapType)){
				for(int i=0; i<nodeList.getLength(); i++){
					Node node = nodeList.item(i);
					try {
						System.out.println("spider---nodetext:"+node.getTextContent().trim());
					} catch (Exception e) {
						e.printStackTrace();
					}
					Node node1 = (node.getAttributes()==null?null:node.getAttributes().getNamedItem(attrName));
					String attrValue = (StringUtil.isEmpty(attrName)?node.getTextContent().trim():(node1==null?"":node1.getTextContent()));
					attrValues.add(attrValue);
				}
			}else{
				String attrValue = "";
				for(int i=0; i<nodeList.getLength(); i++){
					Node node = nodeList.item(i);
					Node node1 = node.getAttributes().getNamedItem(attrName);
					if(i != 0){
						attrValue += SpiderEnums.SPIDER_SPLIT_KEY_INTERVAL.getAlias();
					}
					attrValue += (StringUtil.isEmpty(attrName)?node.getTextContent().trim():(node1==null?"":node1.getTextContent()));
				}
				if(!StringUtil.isEmpty(attrValue)){
					attrValues.add(attrValue);
				}
			}
		}else{
			System.out.println("spider---nodeList is null");
		}
		return attrValues;
	}
	
	/**
	 * �������ͻ�ȡXpathMap
	 * @param typeEnums SPIDER_URLMAP_KEY_*
	 * @return
	 */
	private Map<String, Map<String, String>> getXPathMapByType(SpiderEnums typeEnums)
	{
		SpiderTemplate template = getSpiderTemplate();
		return template.getXPathMapByType(typeEnums);
	}
	
	/**
	 * ��ȡUrl
	 * @param typeEnums SPIDER_URLMAP_KEY_*
	 * @param pageSize ����??
	 * @return
	 */
	private String getTargetUrlByType(SpiderCityEnums cityEnums, Integer pageIndex, Integer pageSize, String targetId, SpiderEnums typeEnums)
	{
		SpiderTemplate template = getSpiderTemplate();
		
		if(template==null){
			System.out.println("------ZiroomSpiderTemplateΪ��----------------");
			return null;
		}
		//��·??
		String rootUrl = template.getLoginMap().get(SpiderEnums.SPIDER_LOGINMAP_KEY_ROOTURL.getAlias());
		String targetUrl = template.getUrlMap().get(typeEnums.getAlias());
		if(targetUrl == null)
		{
			return null;
		}
		
		targetUrl=(targetUrl.startsWith("http")?targetUrl:rootUrl+targetUrl);
		if(pageIndex != null)
		{
			targetUrl = targetUrl.replace(SpiderEnums.SPIDER_MATCH_KEY_PAGEINDEX.getAlias(), String.valueOf(pageIndex));
		}
		if(pageSize != null)
		{
			targetUrl = targetUrl.replace(SpiderEnums.SPIDER_MATCH_KEY_PAGESIZE.getAlias(), String.valueOf(pageSize));
		}
		if(targetId != null)
		{
			targetUrl = targetUrl.replace(SpiderEnums.SPIDER_MATCH_KEY_ID.getAlias(), String.valueOf(targetId));
		}
		
		return targetUrl;
	}
	
	/**
	 * ��ȡ��ǰspidername
	 * @return
	 */
	protected  SpiderTemplate getSpiderTemplate(){
		return this.template;
	}

	public List<SpiderZiroomProduct> grabSearchData(Integer pageIndex, Integer pageSize) {
		return grabSearchData(pageIndex, pageSize, SpiderEnums.SPIDER_URLMAP_KEY_ZIROOMSEARCHINFO);
	}

	public List<SpiderZiroomProduct> grabSearchData(Integer pageIndex, Integer pageSize,
			SpiderEnums typeEnums) {
		List<SpiderZiroomProduct> list = null;
		try {
			list = this.getListByConditions(SpiderCityEnums.getCityEnumsByCity("bj"), pageIndex, pageSize, typeEnums);
		} catch (Exception e) {
			list = null;
			System.out.println("----getListByConditions����������----");
			e.printStackTrace();
		}
		for(int i=0;list!=null && list.size()>0 && i<list.size();i++){
			SpiderZiroomProduct product = list.get(i);
			product.setOrigin(SpiderConstant.ORIGIN_ZIROOM);
			product.setCity("bj");
			Row row = new Row();
			try {
				row.putAll(BeanUtils.describe(product));
				System.out.println("--"+JSONObject.fromObject(row).toString());
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			} 
		}
		return list;
	}

	public void grabDetailData(Integer pageIndex, Integer pageSize,
			List<SpiderZiroomProduct> list, boolean isSleep, int sleepTime) {
		// TODO Auto-generated method stub
		
	}
	
}
