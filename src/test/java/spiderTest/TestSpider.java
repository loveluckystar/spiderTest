package spiderTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.spiderTest.domain.Testxml;


/** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @ClassName: Test 
 * @author bjlixing 
 */
public class TestSpider {
	@Autowired
	@Qualifier("testxml")
	private Testxml xml;
	@Test
	public void testSpider() {
		System.out.println(xml.getTest1());
		System.out.println(xml.getTest2());
//		String pageUrl = "http://www.54new.com/seed-92132.html";
//		String xpath = "//*[@id=\"base\"]/UL/LI[6]/A";
		
//		pageUrl = "http://newhouse.fang.com/house/s/list/b80-b91/";
//		xpath = "/HTML/BODY/DIV[2]/DIV[4]/DIV[2]/DIV[1]/DIV[2]/DIV/UL/DIV[position()>=1]/UL/LI/DIV[2]/P[1]";
//		
//		pageUrl = "http://newhouse.fs.fang.com/house/s/list/b80-b91/";
//		
//		xpath = "//*[@id='bx1']/DIV[2]/DIV[1]/DIV[position()<=10]/DL/DD[1]/DIV[1]/H4/A";
//		xpath = "//*[@id='bx1']/DIV[2]/DIV[1]/DIV[11]/UL/LI[1]/STRONG";
		
		// ApplicationContextUtil.getBean(ITestSpiderService.class).fetchByNeko(pageUrl,
		// xpath);
//		try {
//			// ITestSpiderService testSpiderService =
//			// ApplicationContextInit.getBean(ITestSpiderService.class);
//			// testSpiderService.fetchByHttpClient(pageUrl, xpath);
//			new TestSpiderService().fetchByHttpClient(pageUrl, xpath, null);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
}
