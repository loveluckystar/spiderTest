package spiderTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.spiderTest.SpiderCityEnums;
import com.spiderTest.SpiderConstant;
import com.spiderTest.SpiderEnums;
import com.spiderTest.SpiderZiroomRunnable;
import com.spiderTest.domain.Testxml;
import com.spiderTest.util.ThreadPoolUtil;


/** 
 * @Description: TODO(������һ�仰��������������) 
 * @ClassName: Test 
 * @author bjlixing 
 */
public class TestSpider {
	@Autowired
	@Qualifier("testxml")
	private Testxml xml;
	@Test
	public void testSpider() {
		Integer pageIndex = 1;
		Integer pageSize = SpiderConstant.PAGESIZE_JIAODIAN;
		// ��ȡ��ҳ��
		Integer pageCount = 0;
		try {
			pageCount = jiaodianSpiderService.getPageCountByType(
					SpiderCityEnums.getCityEnumsByCity(SpObserver.getCity()),
					SpiderEnums.SPIDER_URLMAP_KEY_XFSEARCHINFO, null, 1);
		} catch (Exception e) {
			pageCount = 0;
			e.printStackTrace();
		}
		if (pageCount == 0) {
			System.out.println("---����-" + city + "��ȡ����ҳ��Ϊ0 ����---");
			return;
		}
		System.out.println("--->�Ѻ����� " + city + " ��ҳ��Ϊ:" + pageCount + "ҳ��");
		// pageCount = 10 ;//�趨һ��Сֵ�����ڹ۲���ԣ�����ץȡʱ��Ҫע�͵�����
		for (; pageIndex <= pageCount; pageIndex++) {
			
			ThreadPoolUtil.addTask(new SpiderZiroomRunnable(pageIndex, pageSize, service, SpObserver
					.getCity(), isSleep, sleepTime, pageCount));
		}
		
		// String pageUrl = "http://www.54new.com/seed-92132.html";
		// String xpath = "//*[@id=\"base\"]/UL/LI[6]/A";
		
		// pageUrl = "http://newhouse.fang.com/house/s/list/b80-b91/";
		// xpath =
		// "/HTML/BODY/DIV[2]/DIV[4]/DIV[2]/DIV[1]/DIV[2]/DIV/UL/DIV[position()>=1]/UL/LI/DIV[2]/P[1]";
		//
		// pageUrl = "http://newhouse.fs.fang.com/house/s/list/b80-b91/";
		//
		// xpath = "//*[@id='bx1']/DIV[2]/DIV[1]/DIV[position()<=10]/DL/DD[1]/DIV[1]/H4/A";
		// xpath = "//*[@id='bx1']/DIV[2]/DIV[1]/DIV[11]/UL/LI[1]/STRONG";
		
		// ApplicationContextUtil.getBean(ITestSpiderService.class).fetchByNeko(pageUrl,
		// xpath);
		// try {
		// // ITestSpiderService testSpiderService =
		// // ApplicationContextInit.getBean(ITestSpiderService.class);
		// // testSpiderService.fetchByHttpClient(pageUrl, xpath);
		// new TestSpiderService().fetchByHttpClient(pageUrl, xpath, null);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}
	
}
