package spiderTest;

public interface ITestSpiderService {
    public void fetchByNeko(String pageUrl, String xpath) throws Exception;

    public void fetchByHttpClient(String pageUrl, String xpath, String attributeName) throws Exception;
}
