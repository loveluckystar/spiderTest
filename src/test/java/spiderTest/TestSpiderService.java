package spiderTest;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spiderTest.Spider;
import com.spiderTest.util.BCConvertUtil;

public class TestSpiderService implements ITestSpiderService {
	protected Logger log = Logger.getLogger(this.getClass());
	
	public void fetchByNeko(String pageUrl, String xpath) throws Exception {
//		Spider spider = new Spider();
//		Document doc = spider.getDocumentByHttpClient(pageUrl, null);
	}
	
	public void fetchByHttpClient(String pageUrl, String xpath, String attributeName)
			throws Exception {
		Spider spider = new Spider();
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Host", "house.focus.cn");
		headerMap.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0");
		headerMap.put("Accept-Encoding", "gzip,deflate,sdch");
		Document doc = spider.getDocumentByHttpClient(pageUrl, headerMap, "utf8");
		NodeList nodelist = spider.getNodeList(doc, xpath);
		System.out.println(nodelist.getLength());
		if (nodelist != null) {
			for (int i = 0; i < nodelist.getLength(); i++) {
				
				Node node = nodelist.item(i);
				String attrValue = null;
				if (attributeName != null && !attributeName.equals("")) {
					Node node1 = (node.getAttributes() == null ? null : node.getAttributes()
							.getNamedItem(attributeName));
					attrValue = node1.getTextContent();
					System.out.println(attributeName + ":   " + attrValue);
				} else {
					attrValue = node.getTextContent();
					attrValue = BCConvertUtil.qj2bj(attrValue);
					// attrValue = ChineseConverterUtil.convert(attrValue,
					// ChineseConverterUtil.TRADITIONAL_TO_SIMPLE);
					attrValue = attrValue.replace("\n", "#interval#");
					attrValue = attrValue.replaceAll("(" + "#interval#" + ")+\\s+", "#interval#");
					attrValue = attrValue.replaceAll("(" + "#interval#" + ")+", "#interval#");
					// attrValue = attrValue.replace(":", "#interval#");
					System.out.println(attrValue);
				}
			}
		}
		System.out.println(":>>>>>>>>>>>>>>end");
	}
}
