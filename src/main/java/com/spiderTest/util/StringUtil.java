package com.spiderTest.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;

/**
 * �ַ���������
 */
/**
 * @author Administrator
 * 
 */
public class StringUtil {
    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // ����script��������ʽ  
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // ����style��������ʽ  
    private static final String regEx_html = "<[^>]+>"; // ����HTML��ǩ��������ʽ  
    private static final String regEx_space = "\\s*|\t|\r|\n";//����ո�س����з�  
	
	/**
	 * ȡָ��С��λ�ĸ�����,����С��λ��ʱ����
	 * 
	 * @param floStr
	 * @return
	 */
	public static String paseFloat(String floStr, int location) {
		if (floStr == null)
			return "";
		int index = floStr.indexOf(".");
		// ���û��С����.���һ��С����.
		if (index == -1) {
			floStr = floStr + ".";
		}
		index = floStr.indexOf(".");
		int leave = floStr.length() - index;
		// ���С��ָ��λ�����ں��油��
		for (; leave <= location; leave++) {
			floStr = floStr + "0";
		}
		return floStr.substring(0, index + location + 1);
	}

	public static String paseFloat(float flot, int location) {
		String floStr = String.valueOf(flot);
		if (floStr == null)
			return "";
		int index = floStr.indexOf(".");
		// ���û��С����.���һ��С����.
		if (index == -1) {
			floStr = floStr + ".";
		}
		index = floStr.indexOf(".");
		int leave = floStr.length() - index;
		// ���С��ָ��λ�����ں��油��
		for (; leave <= location; leave++) {
			floStr = floStr + "0";
		}
		return floStr.substring(0, index + location + 1);
	}

	/**
	 * ���ַ�������ת��������.
	 * 
	 * @param str
	 *            �ַ�������
	 * @return int ��������ֵ���������ת���򷵻�Ĭ��ֵdefaultValue.
	 */
	public static int getInt(String str, int defaultValue) {
		if (str == null)
			return defaultValue;
		if (isInt(str)) {
			return Integer.parseInt(str);
		} else {
			return defaultValue;
		}
	}

	public static int floatToInt(String flot) {
		float m;
		try {
			m = Float.parseFloat(flot);
		} catch (Exception e) {
			return 0;
		}
		return (int) m;
	}

	public static int floatToInt(float flot) {
		return (int) flot;
	}

	public static String getStr(String str, String defaultValue) {
		if (str == null || "".equals(str))
			return defaultValue;
		else
			return str;
	}

	/** ȡ������Ĭ��ֵ-1 */
	public static int getInt(String str) {
		return getInt(str, -1);
	}

	/**
	 * �ж�һ���ַ����Ƿ�Ϊ����,���ַ���Ҳ����������
	 */
	public static boolean isInt(String str) {
		if(isBlank(str)){
			return false;
		}
		return str.matches("\\d+");
	}

	/**
	 * �ж�һ���ַ����Ƿ��
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/** �ж�ָ�����ַ����Ƿ��ǿմ� */
	public static boolean isBlank(String str) {
		if (isEmpty(str))
			return true;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/** ����ַ���ΪNULL�Ĵ��� */
	public static String notNull(String str) {
		if (str == null) {
			str = "";
		}
		return str;
	}

	/**
	 * �ж�2���ַ����Ƿ����
	 */
	public static boolean isequals(String str1, String str2) {
		return str1.equals(str2);
	}

	/**
	 * �ڳ�����ǰ����
	 * 
	 * @param num
	 *            ����
	 * @param length
	 *            ���λ��
	 */
	public static String addzero(long num, int length) {
		String str = "";
		if (num < Math.pow(10, length - 1)) {
			for (int i = 0; i < (length - (num + "").length()); i++) {
				str += "0";
			}
		}
		str = str + num;
		return str;
	}

	public static boolean isFine(String str) {
		try {
			if (str == null || str.trim().length() == 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ������ǰ����
	 * 
	 * @param num
	 *            ����
	 * @param length
	 *            ���λ��
	 */
	public static String addzero(int num, int length) {
		String str = "";
		if (num < Math.pow(10, length - 1)) {
			for (int i = 0; i < (length - (num + "").length()); i++) {
				str += "0";
			}
		}
		str = str + num;
		return str;
	}

	/**
	 * ʹHTML�ı�ǩʧȥ����*
	 * 
	 * @param input
	 *            ���������ַ���
	 * @return String
	 */
	public static final String escapeHTMLTag(String input) {
		if (input == null)
			return "";
		input = input.trim().replaceAll("&", "&amp;");
		input = input.replaceAll("<", "&lt;");
		input = input.replaceAll(">", "&gt;");
		input = input.replaceAll("\n", "<br>");
		input = input.replaceAll("'", "&#39;");
		input = input.replaceAll("\"", "&quot;");
		input = input.replaceAll("\\\\", "&#92;");
		return input;
	}

	/**
	 * ����url encode
	 * 
	 * @param str
	 * @return
	 * @author chenyz
	 */
	public static String encodeURL(String str) {
		try {
			str = java.net.URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		return str;
	}

	/**
	 * ��ԭhtml��ǩ
	 * 
	 * @param input
	 * @return
	 */
	public static final String unEscapeHTMLTag(String input) {
		if (input == null)
			return "";
		input = input.trim().replaceAll("&amp;", "&");
		input = input.replaceAll("&lt;", "<");
		input = input.replaceAll("&gt;", ">");
		input = input.replaceAll("<br>", "\n");
		input = input.replaceAll("&#39;", "'");
		input = input.replaceAll("&quot;", "\"");
		input = input.replaceAll("&#92;", "\\\\");
		return input;
	}

	/**
	 * ������ϳ��ַ���
	 * 
	 * @param str
	 * @param seperator
	 * @return
	 */
	public static String toString(String[] str, String seperator) {
		if (str == null || str.length == 0)
			return "";
		StringBuffer buf = new StringBuffer();
		for (int i = 0, n = str.length; i < n; i++) {
			if (i != 0)
				buf.append(seperator);
			buf.append(str[i]);
		}
		return buf.toString();
	}

	/**
	 * ���ַ����ָ�������
	 * 
	 * @param str
	 *            �ַ� �磺 1/2/3/4/5
	 * @param seperator
	 *            �ָ����� ��: /
	 * @return String[] �ַ������� ��: {1,2,3,4,5}
	 */
	public static String[] split(String str, String seperator) {
		if (StringUtil.isEmpty(str))
			return null;
		StringTokenizer token = new StringTokenizer(str, seperator);
		int count = token.countTokens();
		String[] ret = new String[count];
		for (int i = 0; i < count; i++) {
			ret[i] = token.nextToken();
		}
		return ret;
	}

	/**
	 * ���ַ����ָ�������
	 * 
	 * @param str
	 *            �ַ� �磺 1/2/3/4/5
	 * @param seperator
	 *            �ָ����� ��: /
	 * @return String[] �ַ������� ��: {1,2,3,4,5}
	 */
	public static String[] splitJinghao(String str) {
		String regex = "##";
		if (StringUtil.isEmpty(str))
			return null;
		String[] ret = str.split(regex);
		return ret;
	}

	/**
	 * ��ָ���ķָ����ָ����ݣ���N���ָ����򷵻�һ��N+1������
	 * 
	 * @param str
	 * @param seperator
	 * @return
	 */
	public static String[] splitHaveEmpty(String str, String seperator) {
		// �ָ���ǰ������һ���հ��ַ�
		str = str.replaceAll(seperator, " " + seperator + " ");
		return str.split(seperator);
	}

	/**
	 * @param len
	 *            ��Ҫ��ʾ�ĳ���(<font color="red">ע�⣺��������byteΪ��λ�ģ�һ��������2��byte</font>)
	 * @param symbol
	 *            ���ڱ�ʾʡ�Ե���Ϣ���ַ����硰...��,��>>>���ȡ�
	 * @return ���ش������ַ���
	 */
	public static String getSub(String str, int len, String symbol) {
		if (str == null)
			return "";
		try {
			int counterOfDoubleByte = 0;
			byte[] b = str.getBytes("gbk");
			if (b.length <= len)
				return str;
			for (int i = 0; i < len; i++) {
				if (b[i] < 0)
					counterOfDoubleByte++; // ͨ���ж��ַ������������н�ȡ
			}
			if (counterOfDoubleByte % 2 == 0)
				str = new String(b, 0, len, "gbk") + symbol;
			else
				str = new String(b, 0, len - 1, "gbk") + symbol;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	/**
	 * �����ڶ��len����byte���ڵģ�����ԭ�ַ������������getSub���н�ȡ
	 * @param str
	 * @param len
	 * @param symbol
	 * @return    
	 * @return_type String
	 * @author bjzhouling
	 */
	public static String getSubAndAdditionalOneWord(String str, int len, String symbol){
		if(isBlank(str)){
			return "";
		}
		try {
			byte[] b = str.getBytes("gbk");
			if(b.length<= len+2){
				return str;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		return getSub(str, len, symbol);
	}
	
	/**
	 * <p>
	 * ���ֽڽ�ȡ�ַ�����
	 * </p>
	 * ����ָ������Ч�����ʽ��ָ�����ֽڳ��ȣ��Լ��ضϷ���(�ҽض�/��ض�)����ȡ�󲻲������롣<br>
	 * ���ص��ַ������ֽڳ��Ƚ�С�ڵ���ָ�����ȡ�����Ϊ���ַ�����<br>
	 * 
	 * @param original ԭ�ַ���
	 * @param charsetName �����ʽ��
	 * @param byteLen �ֽڳ���
	 * @param isRightTruncation �Ƿ��ҽضϡ�
	 * @return String
	 * @throws UnsupportedEncodingException
	 * @author ylx
	 * @date 2012-10-16 ����01:47:32
	 */
	public static String subStrByByteLen(String original, String charsetName,
			int byteLen, boolean isRightTruncation, String symbol) {
		String result = "";
		try {
			if (original == null || "".equals(original.trim()))
				return "";
			if (charsetName == null || "".equals(charsetName))
				charsetName = "gbk";
			byte[] bytes = original.getBytes(charsetName);
			if (byteLen <= 0)
				return "";
			if (byteLen >= bytes.length)
				return original;
			
			int tempLen = 0;
			result = "";
			if (isRightTruncation) {
				// �ҽض�
				// ����ָ���ֽڳ��Ƚضϣ���ת����ʱString�����㳤�ȡ�
				tempLen = new String(bytes, 0, byteLen, charsetName).length();
				// ���ݸó����ҽ�ȡԭ�ַ�����
				result = original.substring(0, tempLen);
				// ����Ԥ���ֽڳ��ȣ���ȥ��һ���ַ���
				if (result != null && !"".equals(result.trim())
						&& result.getBytes(charsetName).length > byteLen)
					result = original.substring(0, tempLen - 1);
			} else {
				// ��ض�
				// ȫ�ַ���-���Ԥ����(ע�������Ԥ���㣬bytes.length-byteLen+1)���ҽضϵĴ����ַ���+1�����㳤�ȡ�(Ϊ�˸���ش�����һ���ַ���)
				// tempLen = original.length()-new
				// String(bytes,0,bytes.length-byteLen+1,charsetName).length()+1;
				// ���ݸó������ȡԭ�ַ�����ע����ʼ�±���㷽����
				// result = original.substring(original.length()-tempLen);
				// �������Ϲ�ʽ����չ�����ɴ˵õ��򻯰档
				tempLen = new String(bytes, 0, bytes.length - byteLen + 1, charsetName)
						.length() - 1;
				result = original.substring(tempLen);
				// ����Ԥ���ֽڳ��ȣ���ȥ��һ���ַ�(���)��
				if (result != null && !"".equals(result.trim())
						&& result.getBytes(charsetName).length > byteLen)
					result = original.substring(tempLen + 1);
			}
			 result = result + symbol;
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * ���ֽڻ�ȡ�ַ����ĳ���,һ������ռ�����ֽ�
	 * 
	 * @param str
	 * @return
	 */
	public static int getLen(String str) {
		try {
			byte[] b = str.getBytes("gbk");
			return b.length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getStrLen(String str)
	{
		if(StringUtil.isBlank(str))
		{
			return 0;
		}
		return str.length();
	}

	public static String getSub(String str, int len) {
		return getSub(str, len, "");
	}

	/** ֻȡĳһ�ַ�����ǰ�����ַ���������...��ʾ */
	public static String getAbc(String str, int len) {
		return getAbc(str, len, "...");
	}

	/** ��ȡ���ٳ���ǰ��һ���ַ��� */
	public static String getAbc(String str, int len, String symbol) {
		if (str == null)
			return null;
		if (len < 0)
			return "";
		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(0, len).concat(symbol);
		}
	}

	public static String getReplace(String str, String former, String replace) {
		if (str != null)
			return str.replace(former, replace);
		return null;
	}

	/**
	 * ��ȡĳ�ַ����������ַ���֮���һ���ַ��� eg:StringUtil.subBetween("yabczyabcz", "y", "z") =
	 * "abc"
	 */
	public static String subBetween(String str, String open, String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		int start = str.indexOf(open);
		if (start != -1) {
			int end = str.indexOf(close, start + open.length());
			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	/**
	 * ��ȡĳ�ַ�����������ָ���ַ���֮���һ���ַ��� StringUtil.subAfterLast("abcba", "b") = "a"
	 */
	public static String subAfterLast(String str, String separator) {
		if (str == null || str.length() == 0) {
			return str;
		}
		if (separator == null || separator.length() == 0) {
			return "";
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1 || pos == (str.length() - separator.length())) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * ��ȡĳ�ַ�����������ָ���ַ���֮ǰ��һ���ַ��� StringUtil.subBeforeLast("abcba", "b") = "abc"
	 */
	public static String subBeforeLast(String str, String separator) {
		if (str == null || separator == null || str.length() == 0
				|| separator.length() == 0) {
			return str;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * ��ȡĳ�ַ�����ָ���ַ���֮���һ���ַ��� StringUtil.subAfter("abcba", "b") = "cba"
	 */
	public static String subAfter(String str, String separator) {
		if (str == null || str.length() == 0) {
			return str;
		}
		if (separator == null) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * ��ȡĳ�ַ�����ָ���ַ���֮ǰ��һ���ַ��� StringUtil.subBefore("abcbd", "b") = "a"
	 */
	public static String subBefore(String str, String separator) {
		if (str == null || separator == null || str.length() == 0) {
			return str;
		}
		if (separator.length() == 0) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/** �ж������ַ������Ƿ�����ͬ��Ԫ�� */
	public static boolean containsNone(String str, String invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		return containsNone(str, invalidChars.toCharArray());
	}

	/** �ж��ַ������Ƿ����ַ������е�Ԫ�� */
	public static boolean containsNone(String str, char[] invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		int strSize = str.length();
		int validSize = invalidChars.length;
		for (int i = 0; i < strSize; i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < validSize; j++) {
				if (invalidChars[j] == ch) {
					return false;
				}
			}
		}
		return true;
	}

	/** �ж��ַ������Ƿ����ָ���ַ��� */
	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return (str.indexOf(searchStr) >= 0);
	}

	/**
	 * �ж��ַ������Ƿ����ָ���ַ���
	 * 
	 * @param str
	 *            Դ�ַ���
	 * @param split
	 *            �ָ���
	 * @param searchStr
	 *            Ҫ���ҵ��ַ���
	 * @return
	 */
	public static boolean contains(String str, String split, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		String[] s = str.split(split);
		for (int i = 0; s != null && i < s.length; i++) {
			if (s[i].equals(searchStr))
				return true;
		}
		return false;
	}

	/**
	 * �ж��ǲ���һ���Ϸ��ĵ����ʼ���ַ
	 * 
	 * @param email
	 * @return boolean
	 */
	static java.util.regex.Pattern emailer;

	public static boolean isEmail(String email) {
		if (emailer == null) {
			String check = "^([a-z0-9A-Z]+[-|\\._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			emailer = java.util.regex.Pattern.compile(check);
		}
		java.util.regex.Matcher matcher = emailer.matcher(email);
		return matcher.matches();
	}

	/**
	 * ת��html�����ַ�Ϊhtml��
	 * 
	 * @param str
	 * @return
	 */
	public static String htmlSpecialChars(String str) {
		try {
			if (str == null) {
				return "";
			}
			StringBuffer sb = new StringBuffer();
			char ch = ' ';
			for (int i = 0; i < str.length(); i++) {
				ch = str.charAt(i);
				if (ch == '&') {
					sb.append("&amp;");
				} else if (ch == '<') {
					sb.append("&lt;");
				} else if (ch == '>') {
					sb.append("&gt;");
				} else if (ch == '"') {
					sb.append("&quot;");
				} else if (ch == '\'') {
					sb.append("&#039;");
				} else if (ch == '(') {
					sb.append("&#040;");
				} else if (ch == ')') {
					sb.append("&#041;");
				} else if (ch == '@') {
					sb.append("&#064;");
				} else {
					sb.append(ch);
				}
			}
			if (sb.toString().replaceAll("&nbsp;", "").replaceAll("��", "")
					.trim().length() == 0) {
				return "";
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * ����ת��html�����ַ�Ϊhtml��
	 * 
	 * @param row
	 * @return
	 * @author chenyz
	 */
	@SuppressWarnings("unchecked")
	public static Row htmlSpecialRow(Row row) {
		Iterator it = row.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			row.put(entry.getKey(),
					StringUtil.htmlSpecialChars(row.gets(entry.getKey())));
		}
		return row;
	}

	/**
	 * 
	 * @param word
	 *            : ������ַ���
	 * @return �Ƿ���������ַ�
	 */
	public boolean charIsLetter(String word) {
		boolean sign = true; // ��ʼ����־ΪΪ'true'
		for (int i = 0; i < word.length(); i++) { // ���������ַ�����ÿһ���ַ�
			if (!Character.isLetter(word.charAt(i))) { // �жϸ��ַ��Ƿ�ΪӢ���ַ�
				sign = false; // ����һλ����Ӣ���ַ����򽫱�־λ�޸�Ϊ'false'
			}
		}
		return sign;// ���ر�־λ���
	}

	/**
	 * ����С�����������ʽ�滻,mark�Ǵ������ص��ʶ С�����ê���ʶ��mark�ӳ��ֵ���ŵķ�ʽ���ɣ����һ�����ֵ�С����Ϊ��
	 * mark1,�ڸ����ֵ�Ϊmark2��������ơ� С����ʾ��:
	 * <ol>
	 * <li><span class="menuId">3.1</span><a href="#">��ʷ�����˶�Ա</a></li>
	 * <li><span class="menuId">3.2</span><a href="#">2004����˻������˶�Ա</a></li>
	 * <li><span class="menuId">3.3</span><a href="#">�����˶�Ա</a></li>
	 * <li><span class="menuId">3.4</span><a href="#">������������</a></li>
	 * </ol>
	 * ����һ���ַ������飬���1Ϊ����������ݣ����2ΪС�������
	 * 
	 * @param input
	 *            ��Ҫ������ԭʼ����
	 * @param mark
	 *            ������ê���ʶ
	 * @param bigProIndex
	 *            �������������
	 * @return
	 */
	public static String[] findSpecData(String input, String mark,
			String bigProIndex) {
		// ������Ŵ����������ı�����
		StringBuffer sb = new StringBuffer();
		// ��������С���ê��
		StringBuffer smallPro = new StringBuffer("<ol>").append("\n");
		// �������С��ı��
		int index = 1;

		String regex = "(<div class=s_title>)(.*?)(</div>)";
		Matcher testM = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
				.matcher(input);
		while (testM.find()) {
			testM.appendReplacement(sb, "<div class=\"s_title\"><a name=\""
					+ mark + index + "\"></a>$2$3");
			// С��������
			String smallName = testM.group(2);
			smallPro.append("<li><span class=\"menuId\" >").append(bigProIndex)
					.append(".").append(index).append("</span><a href=\"#")
					.append(mark).append(index).append("\">").append(smallName)
					.append("</a></li>").append("\n");
			// �������Լ�
			index++;
		}
		// �������С���⣬
		if (index != 1) {
			// ��װС����
			smallPro.append("</ol>");
			// ���ɴ�С����ê�������
			testM.appendTail(sb);
			return new String[] { sb.toString(), smallPro.toString() };
		}
		return null;
	}

	/**
	 * �ַ�����ȡ
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param beginIndex
	 *            ��ʼλ��
	 * @param endIndex
	 *            ����λ��
	 * @return
	 */
	public static String substr(String str, int beginIndex, int endIndex) {
		if (isBlank(str)) {
			return "";
		}
		if (endIndex == -1) {
			return str.substring(beginIndex);
		}

		if (endIndex > str.length()) {
			endIndex = str.length();
		}
		return str.substring(beginIndex, endIndex);
	}

	/**
	 * ������ɼ�����ͬ����
	 * 
	 * @param lenth
	 * @param num
	 * @return
	 */
	public static int[] random5(int lenth, int num) {
		Random rd = new Random();
		HashSet<Integer> set = new HashSet<Integer>();
		while (true) {
			int i = rd.nextInt(lenth);
			set.add(Integer.valueOf(i));
			if (set.size() == num) {
				break;
			}
		}
		Iterator<Integer> iter = set.iterator();
		int jj[] = new int[num];
		int i = 0;
		while (iter.hasNext()) {
			jj[i] = iter.next().intValue();
			++i;
		}
		return jj;
	}

	/**
	 * ����ǽ�ȡ�ַ���,��������������ַ�
	 * 
	 * @param s
	 * @param length
	 * @return
	 */
	public static String bSubstr(String s, int length,String endMark) {
		if (isBlank(s)) {
			return "";
		}
		try {
			byte[] bytes = s.getBytes("Unicode");
			int m = bytes.length;
			int n = 0; // ��ʾ��ǰ���ֽ���
			int i = 2; // Ҫ��ȡ���ֽ������ӵ�3���ֽڿ�ʼ
			for (; i < bytes.length && n < length; i++) {
				// ����λ�ã���3��5��7�ȣ�ΪUCS2�����������ֽڵĵڶ����ֽ�
				if (i % 2 == 1) {
					n++; // ��UCS2�ڶ����ֽ�ʱn��1
				} else {
					// ��UCS2����ĵ�һ���ֽڲ�����0ʱ����UCS2�ַ�Ϊ���֣�һ�������������ֽ�
					if (bytes[i] != 0) {
						n++;
					}
				}
			}
			// ���iΪ����ʱ�������ż��
			if (i % 2 == 1) {
				// ��UCS2�ַ��Ǻ���ʱ��ȥ�������һ��ĺ���
				if (bytes[i - 1] != 0)
					i = i - 1;
				// ��UCS2�ַ�����ĸ�����֣��������ַ�
				else
					i = i + 1;
			}
			String str = new String(bytes, 0, i, "Unicode");
			if(m > length && !isBlank(endMark)){
				str += endMark;
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �ַ�����ȡ
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param beginIndex
	 *            ��ʼλ��
	 * @param endIndex
	 *            ����λ��
	 * @param endMark
	 *            �ڽ�������...��
	 * @return
	 */
	public String substr(String str, int beginIndex, int endIndex,
			String endMark) {
		if (isBlank(str)) {
			return "";
		}
		if (endIndex == -1) {
			return str.substring(beginIndex);
		}

		if (endIndex > str.length()) {
			endIndex = str.length();
		}
		String restr = str.substring(beginIndex, endIndex);
		if (endIndex < str.length()) {
			restr = restr + endMark;
		}
		return restr;
	}

	/**
	 * ȥ�������Ӻ�
	 * <P>
	 * </P>
	 * ,����ժҪʹ��
	 * 
	 * @param str
	 * @return
	 */
	public static String filtHref(String str) {
		if (str == null)
			return "";
		String regex = "<[a|A] href=\".*?>(.*?)</[a|A]>";
		java.util.regex.Pattern pattern = null;
		pattern = java.util.regex.Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			String ss = matcher.group(1);
			str = str.replaceAll("<[a|A] href=\".*?>" + ss + "</[a|A]>", ss);
		}

		regex = "<[p|P] [^>]*?>(.*?)</[p|P]>";
		pattern = java.util.regex.Pattern.compile(regex);
		matcher = pattern.matcher(str);
		while (matcher.find()) {
			String ss = matcher.group(1);
			str = str.replaceAll("<[p|P] [^>]*?>" + ss + "</[p|P]>", ss);
		}
		return str;
	}

	/**
	 * ����ָ����html��ǩ��ֻȥ����ǩ���������ݣ�
	 * 
	 * @param txt
	 *            - ���й��˵��ı�
	 * @param tags
	 *            - Ҫ���˵ı�ǩ
	 */
	public static String removeHtmlTag(String txt, String[] tags) {
		String result = txt;
		for (String tag : tags) { // ѭ��ÿ����ǩ
			String[] regs = { "<" + tag + "\\s[^>]*>", "</" + tag + ">" }; // ������ʼ��ǩ�ͽ�����ǩ
			for (String reg : regs) {
				Pattern pattern = Pattern
						.compile(reg, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(result);
				while (matcher.find()) {
					result = matcher.replaceAll("");
				}
			}
		}
		return result;
	}

	/**
	 * ����ָ����html��ǩ��������ǩ���ݣ�����֧��ͬ��ǩǶ��
	 * 
	 * @param txt
	 *            - ���й��˵��ı�
	 * @param tags
	 *            - Ҫ���˵ı�ǩ
	 */
	public static String removeHtmlTagAndContent(String txt, String[] tags) {
		String result = txt;
		for (String tag : tags) { // ѭ��ÿ����ǩ
			String reg = "<" + tag + "(\\s[^>]*>|>)[\\w\\W]*?</" + tag + ">"; // ����ƥ���������ʽ
			Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(result);
			while (matcher.find()) {
				result = matcher.replaceAll("");
			}
		}
		return result;
	}

	/**
	 * �������Ӽ���:target="_blank"
	 * 
	 * @param str
	 * @return
	 */
	public static String addHrefBlank(String str) {
		if (str == null)
			return "";
		String regex = "<[a|A] href=\"([^>]*?)>.*?</[a|A]>";
		java.util.regex.Pattern pattern = null;
		pattern = java.util.regex.Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			String ss = matcher.group(1);
			if (ss.indexOf("_blank") == -1) {
				str = str.replaceAll(ss, ss + "  target=\"_blank\"");
			}
		}
		return str;
	}

	// ��ȡ26����ĸ
	public static char[] getEnChar() {
		char[] cs = new char[26];
		char c = 'A' - 1;
		for (int i = 0; c++ < 'Z'; i++) {
			cs[i] = c;
		}
		return cs;
	}

	// �ж��Ƿ���26����ĸ����
	public static boolean isInChar(String c) {
		boolean in = false;
		char[] ch = getEnChar();
		for (int i = 0; i < ch.length; i++) {
			if (c.equals(ch[i] + "")) {
				in = true;
				break;
			}
		}
		return in;
	}

	// ת���ɴ�д
	public String toUpperCase(String str) {
		if (isBlank(str)) {
			return "";
		}
		return str.toUpperCase();
	}

	// ת���ɴ�д
	public String toLowerCase(String str) {
		if (isBlank(str)) {
			return "";
		}
		return str.toLowerCase();
	}

	/**
	 * ���ݴ�ͼ���Сͼ��ַ
	 * 
	 * @param imgurl
	 * @return
	 */
	public static String getSmallImg(String imgurl) {
		int len = imgurl.lastIndexOf("/");
		if (len > 1)
			return imgurl.substring(0, len + 1) + "t_"
					+ imgurl.substring(len + 1, imgurl.length());
		else
			return imgurl;
	}

	/**
	 * ȥ��html����
	 * 
	 * @param html
	 * @return
	 */
	public static String trimHtml(String html) {
		Parser parser = Parser.createParser(html, "GBK");
		if (parser != null) {
			StringBean sb = new StringBean();
			try {
				parser.visitAllNodesWith(sb);
				html = sb.getStrings();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return html;
	}

	/**
	 * ���ַ����г�ÿ���ַ�
	 * 
	 * @param str
	 * @return
	 */
	public static char[] toArray(String str) {
		return str.toCharArray();
	}

	/**
	 * b����a
	 * 
	 * @param str
	 * @param a
	 * @param b
	 * @return
	 */
	public static String replaceStr(String str, String a, String b) {
		if (isBlank(str)) {
			return "";
		}
		return str.replaceAll(a, b);
	}

	/**
	 * b����a
	 * 
	 * @param str
	 * @param a
	 * @param b
	 * @return
	 */
	public static String replaceJinghao(String str, String a) {
		if (isBlank(str)) {
			return "";
		}
		return str.replaceAll("##", a);
	}

	public static int indexof4Str(String str, String che) {
		if (str != null)
			return str.indexOf(che);
		return -1;
	}

	/**
	 * �ѵ绰�����м���λ��****����
	 * 
	 * @param str
	 * @param a
	 * @param b
	 * @return
	 */
	public static String hideTelephone(String telephone) {
		if (isBlank(telephone)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		if (telephone.length() > 2)
			sb.append(telephone.substring(0, 3));
		sb.append("****");
		if (telephone.length() > 10)
			sb.append(telephone.substring(7, 11));
		return sb.toString();
	}

	/**
	 * ���ݼ۸��ȡ��λ
	 * 
	 * @param price
	 * @return
	 */
	public static String getSamePrice(String price) {
		if (StringUtil.isEmpty(price)) {
			return "[000000 TO 999999]";
		}
		try {
		  price = price.trim();
			int p = Integer.parseInt(price);
			int range = 0;
			if (p > 0 && p <= 8000)
				range = 1000;
			else if (p > 8000 && p <= 15000)
				range = 2000;
			else if (p > 15000 && p <= 25000)
				range = 3000;
			else if (p > 25000 && p <= 50000)
				range = 5000;

			if (range > 0) {
				int lprice = p - range;
				int hprice = p + range;
				if (lprice < 0)
					lprice = 0;
				return "[" + addzero(lprice, 6) + " TO " + addzero(hprice, 6)
						+ "]";
			} else {
				return "[050000 TO 999999]";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "[000000 TO 999999]";
	}

	/**
	 * ���ݼ۸��ȡ��λ
	 * 
	 * @param price
	 * @return
	 */
	public static String getSamePrice(String price, String type) {
		if (StringUtil.isEmpty(price)) {
			return "[000000 TO 999999]";
		}
		try {
		  price = price.trim();
			int p = Integer.parseInt(price);
			int range = 0;
			if (p > 0 && p <= 10000)
				range = 1000;
			else if (p > 10000 && p <= 20000)
				range = 2000;
			else if (p > 20000 && p <= 30000)
				range = 3000;
			else if (p > 30000 && p <= 50000)
				range = 5000;
			else if (p > 50000)
				range = 10000;
			if (range > 0) {
				int lprice = p - range;
				int hprice = p + range;
				if (lprice < 0)
					lprice = 0;
				if (type.equals("tohigh")) {
					return "[" + addzero(p, 6) + " TO " + addzero(hprice, 6)
							+ "]";
				} else if (type.equals("tolow")) {
					return "[" + addzero(lprice, 6) + " TO " + addzero(p, 6)
							+ "]";
				}
			} else {
				return "[050000 TO 999999]";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "[000000 TO 999999]";
	}

	public static String delZero(String str) {
		if (str == null || str.equals("����"))
			return "����";
		for (int i = 0, n = str.length(); i < n; i++) {
			if (str.substring(0, 1).equals("0"))
				str = str.substring(1, str.length());
		}
		if ("0".equals(str) || "".equals(str))
			str = "����";
		return str;
	}

	/**
	 * ȥ�����ֽ�β�����0��С���㡣���磬<br>
	 * ����4.00���õ�4<br>
	 * ����3.50���õ�3.5
	 */
	public static String removeTrailingZeros(String num) {
		if (isEmpty(num)) {
			return num;
		}
		int end = num.length();
		if (num.indexOf(".") != -1) { // ��С���㣬ȥ����β�����0
			while (num.charAt(end - 1) == '0') {
				end--;
			}
		}
		if (num.charAt(end - 1) == '.') { // ȥ�������С����
			end--;
		}
		return num.substring(0, end);
	}

	public static String getStrConcat(String str, String defaultOne,
			String defaultTwo) {
		if (str == null || "".equals(str))
			return "";
		else
			return defaultOne + str + defaultTwo;
	}

	/**
	 * �ж�2���ַ����Ƿ����
	 */
	public static boolean isNotEquals(String str1, String str2) {
		if ((isEmpty(str1) && !isEmpty(str2))
				|| (!isEmpty(str1) && isEmpty(str2)))// һ����һ���ǿ�,�����
			return true;
		if (isEmpty(str1) && isEmpty(str2))// ������Ϊ��,���
			return false;
		if (str1.equals(str2))
			return false;
		else
			return true;
	}

	/**
	 * ��ȡ�����Сֵ�м䷶Χ�������
	 * 
	 * @param max
	 * @param min
	 * @return
	 * @author chenyz
	 */
	public static int getRangeRandom(int max, int min) {
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	/**
	 * �ж��Ƿ�ֻ���ڴ�д������
	 * 
	 * @param str
	 * @return
	 * @author chenyz
	 */
	public static boolean checkMatchCase(String str) {
		Pattern p = Pattern.compile("[A-Z0-9]+");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static String checkCase(String str) {
		if (isEmpty(str))
			return str;
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			if (checkMatchCase(str.charAt(i) + "")) {
				result += "B";
			} else {
				result += "S";
			}
		}
		return str + result;
	}

	public static String urlEncoder(String str, String charset) {
		try {
			return URLEncoder.encode(str, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String percent(String str) {
		try {
			return new DecimalFormat("##%").format(Double.valueOf(str));
		} catch (Exception e) {
			e.printStackTrace();
			return "0%";
		}
	}

	public static boolean isHanzi(String str) {
		Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher m = p.matcher(str);
		while (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * ����List<String>�е��ظ�ֵ
	 * 
	 * @param arlList
	 * @return
	 * @author chenyz
	 */
	@SuppressWarnings("unchecked")
	public static List<String> removeDuplicate(List<String> arlList) {
		try {
			HashSet h = new HashSet(arlList);
			arlList.clear();
			arlList.addAll(h);
			return arlList;
		} catch (Exception e) {
			return arlList;
		}
	}

	/**
	 * �ж��Ƿ����ĳ��������ʽ
	 * 
	 * @param arg0
	 * @param pattern
	 * @return
	 * @return boolean
	 */
	public static boolean isMatch(String arg0, String pattern) {
		return arg0.matches(pattern);
	}

	/**
	 * 
	 * @Title trim trim
	 * @param arg0
	 * @return
	 * @return String
	 */
	public static String trim(String arg0) {
		return arg0.trim();
	}

	/**
	 * ������ÿ����λ�Ӷ���
	 * 
	 * @param num
	 * @return
	 */
	public static String getReadabilityNum(String num) {
		String result = "";
		StringBuffer sb = new StringBuffer(num);
		num = num.trim();
		if (num.indexOf("����") != -1) {
			num = num.replace("����", "").trim();
			num += "000000";
		} else if (num.indexOf("��") != -1) {
			num = num.replace("��", "").trim();
			num += "0000";
		}
		result = packageReadabilityNum(num);
		return result;
	}

	private static String packageReadabilityNum(String input) {
		String result = "";
		if (input.length() > 3) {
			result += packageReadabilityNum(input.substring(0,
					input.length() - 3));
			result += "," + input.substring(input.length() - 3);
		} else {
			result += input;
		}
		return result;
	}

	// ȡ�ַ����س��ĵ�һ��
	public static String getFirstLine(String content) {
		if (content == null)
			return "";
		int index = content.indexOf("\r\n");
		if (index > 0) {
			return content.substring(0, index);
		}
		return content;
	}

	// �Ƿ�����
	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	// ��������html��ǩ
	public static String Html2Text(String inputString) {
		if (StringUtil.isEmpty(inputString))
			return "";
		String htmlStr = inputString; // ��html��ǩ���ַ���
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;

		try {
			String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // ����script��������ʽ{��<script[^>]*?>[//s//S]*?<///script>
			String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // ����style��������ʽ{��<style[^>]*?>[//s//S]*?<///style>
			String regEx_html = "<[^>]+>"; // ����HTML��ǩ��������ʽ
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // ����script��ǩ

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // ����style��ǩ

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // ����html��ǩ

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // ����html��ǩ

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// �����ı��ַ���
	}
	
	/**
	 * �����ַ������� ��ȡ��Ӧ�ֺ�(���޶�������ҳʹ��)
	 * @param str
	 * @return    
	 * @return_type String
	 * @author bjzhouling
	 */
  public static String getFontSizeByStringLength(String str){
	  if(str==null || str.equals("")){
		  return "0";//0���ǹ̶��ֺţ�ֻΪ��ֵ��������
	  }
	  int length = str.length();
	  if(length<=5){
		  return "44";
	  }else if(length==6){
		  return "36";
	  }else if(length==7){
		  return "32";
	  }else if(length==8){
		  return "28";
	  }else if(length==9){
		  return "25";
	  }else if(length==10){
		  return "22";
	  }else if(length>10 && length<=20){
		  return "21";//21���ǹ̶��ֺţ�ֻΪ��ֵ��������
	  }else{
		  return "20";//20���ǹ̶��ֺţ�ֻΪ��ֵ��������
	  }
  }
  
  public static String substrByLength(String str,int start,int size,String markCode){
  	String finalStr = "";
  	if(str == null){
  		for(int i=0;i<size;i++){
  			finalStr = finalStr + " ";
  			return finalStr;
  		}
  	}
  	int length = start+size-1;
  	if(length>str.length()){
  		finalStr= str.substring(start, str.length());
  	}else{
  		finalStr = str.substring(start, length);
  	}
  	
  	for(int i=0;i<size-finalStr.length()+1;i++){
  		finalStr = finalStr + markCode;
  	}
  	return finalStr;
  }

  /**
   * ��ȡ�绰����
   * @param text
   * @return
   * @author ylx
   */
  public static String getTelnumbers(String text){
		Pattern pattern = Pattern
				.compile("(\\d{7,14}-\\d{3,4})|((\\d{3,4}|\\d{3,4}-|\\s)?\\d{7,14})");
		Matcher matcher = pattern.matcher(text);
		StringBuffer bf = new StringBuffer(64);
		while (matcher.find()) {
			bf.append(matcher.group()).append(",");
		}
		int len = bf.length();
		if (len > 0) {
			bf.deleteCharAt(len - 1);
		}
		return bf.toString();
  }
  
  /**
   * ȥ��HTML��ǩ
   * @Description: TODO
   * @param htmlStr
   * @return    
   * @return_type String
   * @author bjzhouling
   */
  public static String delHTMLTag(String htmlStr) {  
	  if(htmlStr==null || htmlStr.equals("")){
		  return "";
	  }
      Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
      Matcher m_script = p_script.matcher(htmlStr);  
      htmlStr = m_script.replaceAll(""); // ����script��ǩ  

      Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
      Matcher m_style = p_style.matcher(htmlStr);  
      htmlStr = m_style.replaceAll(""); // ����style��ǩ  

      Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
      Matcher m_html = p_html.matcher(htmlStr);  
      htmlStr = m_html.replaceAll(""); // ����html��ǩ  

      Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);  
      Matcher m_space = p_space.matcher(htmlStr);  
      htmlStr = m_space.replaceAll(""); // ���˿ո�س���ǩ  
      return htmlStr.trim(); // �����ı��ַ���  
  } 
  
  
  /**
   * ��listת��Ϊ�ַ���
   * @param list
   * @param separator
   * @return
   */
  public static String getStringFromList(List<String> list,String separator)
  {
	  String result = "";
	  if(list!=null)
	  {
		  for(String str:list)
		  {
			  result = result + str.trim() + separator;
		  }
		  int index = result.lastIndexOf(separator);
		  if(index>=0)
		  {
			  result = result.substring(0,index);
		  }
	  }
	  return result;
  }
  
  /**
   * ��������ƥ�䵽���ַ���
   * @param str
   * @param regex
   * @return    
   * @return_type String
   * @author bjzhouling
   */
  public static String getMatchStr(String str,String regex){
	  Pattern pattern = Pattern.compile(regex);
	  Matcher matcher = pattern.matcher(str);
	  StringBuffer buffer = new StringBuffer();
	  while(matcher.find()){
		  buffer.append(matcher.group());
	  }
	  return buffer.toString();
  }
  
  /**
   * �����ı���ʽΪhtml��ʽ�����У�\r\n��Ϊ<br/> , " "��Ϊ&nbsp;��
   * @param text
   * @return    
   * @return_type String
   * @author bjzhouling
   */
  public static String handleTextPatternToHtml(String text){
	  return text.replace("\r\n", "<br/>").replace(" ", "&nbsp;");
  }
  
  /**
   * ����html��ʽΪ�ı���ʽ�����У�<br/>��Ϊ\r\n , &nbsp;��Ϊ" "��
   * @param text
   * @return    
   * @return_type String
   * @author bjzhouling
   */
  public static String handleHtmlPatternToText(String text){
	  return text.replace("<br/>", "\r\n").replace("&nbsp;", " ");
  }
  
  /*
   * MD5������ر���
   */
	public static final ThreadLocal<MessageDigest> DIGESTER_CONTEXT = new ThreadLocal<MessageDigest>() {
		protected synchronized MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}
	};

	public static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f' };
  
  /**
   * ͨ���ļ����������ݼ���md5ֵ
   * 
   * @param fileData �ļ�����������
   * @return md5
   */
  public static String getMd5(byte[] fileData) {
      MessageDigest digester = DIGESTER_CONTEXT.get();
      digester.update(fileData);
      byte[] md5Bytes = digester.digest();

      int length = md5Bytes.length;
      char[] md5String = new char[length * 2];
      int k = 0;
      for (int i = 0; i < length; i++) {
          byte b = md5Bytes[i];
          md5String[k++] = HEX_DIGITS[b >>> 4 & 0xf];
          md5String[k++] = HEX_DIGITS[b & 0xf];
      }
      return new String(md5String);
  }
  
  /*
   * ͨ��������ʽ��ȡָ���ַ���
   */
  public static String getMatcher(String regex, String source) {  
    String result = "";  
    Pattern pattern = Pattern.compile(regex);  
    Matcher matcher = pattern.matcher(source);  
    while (matcher.find()) {  
        result = matcher.group(1);//ֻȡ��һ��  
    }  
    return result;
  }
  
  /*
   * �жϵ绰�����Ƿ����У�����
   */
  public static boolean verifyMobile(String mobile) {
  	Pattern pattern = Pattern.compile("^1\\d{10}$");
		Matcher matcher = pattern.matcher(mobile);
		if (!matcher.find()) {// ��֤�ֻ�����
			return false;
		}else{
			return true;
		}
  }
  
  //�ж�str�ַ�����text���ֵ�λ��
  public static int indexOf(String str,String text){
  	return str.indexOf(text);
  }
}
