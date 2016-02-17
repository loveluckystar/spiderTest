package com.spiderTest.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.xml.transform.TransformerException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.spiderTest.util.CustomGetMethod;
import com.spiderTest.util.Row;
import com.sun.org.apache.xpath.internal.XPathAPI;

@SuppressWarnings("restriction")
public class Spider {
	
	private HttpClient httpclient = new HttpClient(new MultiThreadedHttpConnectionManager());
	
	/**
	 * 模拟登陆
	 * 
	 * @param row
	 */
	public void SimulateLogin(Row row) {
	}
	
	/**
	 * @param pageUrl
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public Document getDocumentByHttpClient(String pageUrl, Map<String, String> headerMap)
			throws Exception {
		return this.getDocumentByHttpClient(pageUrl, headerMap, "GBK");
	}
	
	/*
	 * httpclient请求pageUrl，加入请求头，设置编码
	 */
	public Document getDocumentByHttpClient(String pageUrl, Map<String, String> headerMap,
			String encoding) throws Exception {
		long start = System.currentTimeMillis();
		Document doc = null;
		// System.out.println(" pageUrl: "+pageUrl+" ,headerMap: "+headerMap);
		CustomGetMethod get = new CustomGetMethod(pageUrl, headerMap);
		// System.out.println("---> "+get.toString());
		try {
			// httpclient.setTimeout(1000*60);
			get.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000 * 60);
			// 设置请求重试处理，用的是默认的重试处理：请求三次
			get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			// System.out.println(get.getName()+","+get.getStatusCode()+","+get.toString());
			int statusCode = httpclient.executeMethod(get);
			System.out.println("spider---statusCode:" + statusCode);
			// System.out.println("statusCode:"+statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + get.getStatusLine());
			} else {
				InputStream inputStream = get.getResponseBodyAsStream();
				if (inputStream != null) {
					if (get.getResponseHeader("Content-Encoding") != null
							&& get.getResponseHeader("Content-Encoding").getValue().toLowerCase()
									.indexOf("gzip") > -1) {
						// For GZip response
						InputStream is = get.getResponseBodyAsStream();
						inputStream = new GZIPInputStream(is);
					}
					boolean bTest = false;
					if (bTest) {
						pageUrl = pageUrl.substring(pageUrl.length() - 14, pageUrl.length() - 1);
						pageUrl = pageUrl.replace("/", "-");
						File file = new File("d:\\" + "111" + ".html");
						if (file.exists()) {
							file = new File("d:\\" + "111" + ".html");
						}
						OutputStream os = new FileOutputStream(file);
						int bytesRead = 0;
						byte[] buffer = new byte[819200];
						while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
							os.write(buffer, 0, bytesRead);
						}
						os.close();
						inputStream = new FileInputStream(file);
					}
					
					InputSource inputSource = null;
					if (encoding != null && !encoding.equals("")) {
						inputSource = new InputSource(new InputStreamReader(inputStream, encoding));
					} else { // 默认GBK编码
						inputSource = new InputSource(new InputStreamReader(inputStream, "GBK"));
					}
					
					DOMParser domParser = this.getDOMParser(get.getResponseCharSet());
					synchronized (this) {
						domParser.parse(inputSource);
						doc = domParser.getDocument();
						// System.out.println(doc.getElementsByTagName("html").item(0).getTextContent());
					}
				}
			}
			long end = System.currentTimeMillis();
			// System.out.println("---->getDocumentByHttpClient, cost=" + (end - start) +
			// " mills.");
			return doc;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public DOMParser getDOMParser(String encoding) {
		DOMParser parser = new DOMParser();
		try {
			parser.setProperty("http://cyberneko.org/html/properties/default-encoding",
					encoding);
			parser.setFeature("http://xml.org/sax/features/namespaces", false);
			return parser;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Document getDocumentByNeko(String pageUrl, String encoding) {
		long start = System.currentTimeMillis();
		DOMParser parser = this.getDOMParser(encoding);
		try {
			parser.parse(pageUrl);
			Document doc = parser.getDocument();
			long end = System.currentTimeMillis();
			// System.out.println("---->getDocumentByNeko, cost=" + (end - start) + " mills.");
			return doc;
		} catch (Exception e) {
			// System.out.println("异常url??+pageUrl);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getSingleNodeContent(Node doc, String xpath) {
		Node node = getNode(doc, xpath);
		if (node != null) {
			System.out.println("-->getSingleNodeContent, node=" + node);
			return node.getTextContent().trim();
		}
		return null;
	}
	
	public String getSingleNodeAttributeContent(Node doc, String xpath, String attributeName) {
		Node node = getNode(doc, xpath);
		if (node != null) {
			try {
				return node.getAttributes().getNamedItem(attributeName).getTextContent().trim();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Node getNode(Node doc, String xpath) {
		if (doc != null) {
			Node node;
			try {
				node = XPathAPI.selectSingleNode(doc, xpath);
				System.out.println("-->getNode, node="
						+ (node == null ? "null" : node.getNodeName()));
				return node;
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public NodeList getNodeList(Node doc, String xpath) {
		if (doc != null) {
			NodeList nodeList;
			try {
				nodeList = XPathAPI.selectNodeList(doc, xpath);
				for (int i = 0; i < nodeList.getLength(); i++) {
					System.out.println(nodeList.item(i).getNodeValue());
				}
				return nodeList;
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
}
