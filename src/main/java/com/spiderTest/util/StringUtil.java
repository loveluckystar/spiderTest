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
 * 字符串处理类
 */
/**
 * @author Administrator
 * 
 */
public class StringUtil {
    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式  
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式  
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符  
	
	/**
	 * 取指定小数位的浮点数,不够小数位数时补零
	 * 
	 * @param floStr
	 * @return
	 */
	public static String paseFloat(String floStr, int location) {
		if (floStr == null)
			return "";
		int index = floStr.indexOf(".");
		// 如果没有小数点.则加一个小数点.
		if (index == -1) {
			floStr = floStr + ".";
		}
		index = floStr.indexOf(".");
		int leave = floStr.length() - index;
		// 如果小于指定位数则在后面补零
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
		// 如果没有小数点.则加一个小数点.
		if (index == -1) {
			floStr = floStr + ".";
		}
		index = floStr.indexOf(".");
		int leave = floStr.length() - index;
		// 如果小于指定位数则在后面补零
		for (; leave <= location; leave++) {
			floStr = floStr + "0";
		}
		return floStr.substring(0, index + location + 1);
	}

	/**
	 * 把字符型数字转换成整型.
	 * 
	 * @param str
	 *            字符型数字
	 * @return int 返回整型值。如果不能转换则返回默认值defaultValue.
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

	/** 取整数，默认值-1 */
	public static int getInt(String str) {
		return getInt(str, -1);
	}

	/**
	 * 判断一个字符串是否为数字,空字符串也不属于数字
	 */
	public static boolean isInt(String str) {
		if(isBlank(str)){
			return false;
		}
		return str.matches("\\d+");
	}

	/**
	 * 判断一个字符串是否空
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/** 判断指定的字符串是否是空串 */
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

	/** 针对字符串为NULL的处理 */
	public static String notNull(String str) {
		if (str == null) {
			str = "";
		}
		return str;
	}

	/**
	 * 判断2个字符串是否相等
	 */
	public static boolean isequals(String str1, String str2) {
		return str1.equals(str2);
	}

	/**
	 * 在长数字前补零
	 * 
	 * @param num
	 *            数字
	 * @param length
	 *            输出位数
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
	 * 在数字前补零
	 * 
	 * @param num
	 *            数字
	 * @param length
	 *            输出位数
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
	 * 使HTML的标签失去作用*
	 * 
	 * @param input
	 *            被操作的字符串
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
	 * 进行url encode
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
	 * 还原html标签
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
	 * 把数组合成字符串
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
	 * 把字符串分隔成数组
	 * 
	 * @param str
	 *            字符 如： 1/2/3/4/5
	 * @param seperator
	 *            分隔符号 如: /
	 * @return String[] 字符串树组 如: {1,2,3,4,5}
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
	 * 把字符串分隔成数组
	 * 
	 * @param str
	 *            字符 如： 1/2/3/4/5
	 * @param seperator
	 *            分隔符号 如: /
	 * @return String[] 字符串树组 如: {1,2,3,4,5}
	 */
	public static String[] splitJinghao(String str) {
		String regex = "##";
		if (StringUtil.isEmpty(str))
			return null;
		String[] ret = str.split(regex);
		return ret;
	}

	/**
	 * 按指定的分隔符分隔数据，有N个分隔符则返回一个N+1的数组
	 * 
	 * @param str
	 * @param seperator
	 * @return
	 */
	public static String[] splitHaveEmpty(String str, String seperator) {
		// 分隔符前后增加一个空白字符
		str = str.replaceAll(seperator, " " + seperator + " ");
		return str.split(seperator);
	}

	/**
	 * @param len
	 *            需要显示的长度(<font color="red">注意：长度是以byte为单位的，一个汉字是2个byte</font>)
	 * @param symbol
	 *            用于表示省略的信息的字符，如“...”,“>>>”等。
	 * @return 返回处理后的字符串
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
					counterOfDoubleByte++; // 通过判断字符的类型来进行截取
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
	 * 长度在多出len两个byte以内的，返回原字符串，否则调用getSub进行截取
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
	 * 按字节截取字符串。
	 * </p>
	 * 按照指定的有效编码格式，指定的字节长度，以及截断方向(右截断/左截断)。截取后不产生乱码。<br>
	 * 返回的字符串的字节长度将小于等于指定长度。可能为空字符串。<br>
	 * 
	 * @param original 原字符串
	 * @param charsetName 编码格式名
	 * @param byteLen 字节长度
	 * @param isRightTruncation 是否右截断。
	 * @return String
	 * @throws UnsupportedEncodingException
	 * @author ylx
	 * @date 2012-10-16 下午01:47:32
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
				// 右截断
				// 按照指定字节长度截断，再转成临时String，计算长度。
				tempLen = new String(bytes, 0, byteLen, charsetName).length();
				// 根据该长度右截取原字符串。
				result = original.substring(0, tempLen);
				// 超过预订字节长度，则去掉一个字符。
				if (result != null && !"".equals(result.trim())
						&& result.getBytes(charsetName).length > byteLen)
					result = original.substring(0, tempLen - 1);
			} else {
				// 左截断
				// 全字符长-左截预订点(注意必须是预定点，bytes.length-byteLen+1)所右截断的串的字符长+1，计算长度。(为了给左截串多留一个字符。)
				// tempLen = original.length()-new
				// String(bytes,0,bytes.length-byteLen+1,charsetName).length()+1;
				// 根据该长度左截取原字符串。注意起始下标计算方法。
				// result = original.substring(original.length()-tempLen);
				// 由于以上公式可以展开，由此得到简化版。
				tempLen = new String(bytes, 0, bytes.length - byteLen + 1, charsetName)
						.length() - 1;
				result = original.substring(tempLen);
				// 超过预订字节长度，则去掉一个字符(左截)。
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
	 * 按字节获取字符串的长度,一个汉字占二个字节
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

	/** 只取某一字符串的前几个字符，后面以...表示 */
	public static String getAbc(String str, int len) {
		return getAbc(str, len, "...");
	}

	/** 截取多少长度前的一断字符串 */
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
	 * 截取某字符串中两个字符串之间的一段字符串 eg:StringUtil.subBetween("yabczyabcz", "y", "z") =
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
	 * 截取某字符串中最后出现指定字符串之后的一段字符串 StringUtil.subAfterLast("abcba", "b") = "a"
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
	 * 截取某字符串中最后出现指定字符串之前的一段字符串 StringUtil.subBeforeLast("abcba", "b") = "abc"
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
	 * 截取某字符串中指定字符串之后的一段字符串 StringUtil.subAfter("abcba", "b") = "cba"
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
	 * 截取某字符串中指定字符串之前的一段字符串 StringUtil.subBefore("abcbd", "b") = "a"
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

	/** 判断两个字符串中是否含有相同的元素 */
	public static boolean containsNone(String str, String invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		return containsNone(str, invalidChars.toCharArray());
	}

	/** 判断字符串中是否含有字符数组中的元素 */
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

	/** 判断字符串中是否包含指定字符串 */
	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return (str.indexOf(searchStr) >= 0);
	}

	/**
	 * 判断字符串中是否包含指定字符串
	 * 
	 * @param str
	 *            源字符串
	 * @param split
	 *            分隔符
	 * @param searchStr
	 *            要查找的字符串
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
	 * 判断是不是一个合法的电子邮件地址
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
	 * 转换html特殊字符为html码
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
			if (sb.toString().replaceAll("&nbsp;", "").replaceAll("　", "")
					.trim().length() == 0) {
				return "";
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 批量转换html特殊字符为html码
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
	 *            : 输入的字符串
	 * @return 是否输入的是字符
	 */
	public boolean charIsLetter(String word) {
		boolean sign = true; // 初始化标志为为'true'
		for (int i = 0; i < word.length(); i++) { // 遍历输入字符串的每一个字符
			if (!Character.isLetter(word.charAt(i))) { // 判断该字符是否为英文字符
				sign = false; // 若有一位不是英文字符，则将标志位修改为'false'
			}
		}
		return sign;// 返回标志位结果
	}

	/**
	 * 生成小标题的正则表达式替换,mark是大标题的镭点标识 小标题的锚点标识按mark加出现的序号的方式生成，如第一个出现的小标题为：
	 * mark1,第个出现的为mark2，如此类推。 小标题示例:
	 * <ol>
	 * <li><span class="menuId">3.1</span><a href="#">历史著名运动员</a></li>
	 * <li><span class="menuId">3.2</span><a href="#">2004年奥运会著名运动员</a></li>
	 * <li><span class="menuId">3.3</span><a href="#">其他运动员</a></li>
	 * <li><span class="menuId">3.4</span><a href="#">其他著名人物</a></li>
	 * </ol>
	 * 返回一个字符串数组，序号1为解析后的数据，序号2为小题标数据
	 * 
	 * @param input
	 *            需要解析的原始数据
	 * @param mark
	 *            大标题的锚点标识
	 * @param bigProIndex
	 *            大标题的索引序号
	 * @return
	 */
	public static String[] findSpecData(String input, String mark,
			String bigProIndex) {
		// 用来存放处理解析后的文本数据
		StringBuffer sb = new StringBuffer();
		// 用来生成小项的锚点
		StringBuffer smallPro = new StringBuffer("<ol>").append("\n");
		// 用来存放小项的标号
		int index = 1;

		String regex = "(<div class=s_title>)(.*?)(</div>)";
		Matcher testM = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
				.matcher(input);
		while (testM.find()) {
			testM.appendReplacement(sb, "<div class=\"s_title\"><a name=\""
					+ mark + index + "\"></a>$2$3");
			// 小标题名称
			String smallName = testM.group(2);
			smallPro.append("<li><span class=\"menuId\" >").append(bigProIndex)
					.append(".").append(index).append("</span><a href=\"#")
					.append(mark).append(index).append("\">").append(smallName)
					.append("</a></li>").append("\n");
			// 索引号自加
			index++;
		}
		// 如果存在小标题，
		if (index != 1) {
			// 组装小标题
			smallPro.append("</ol>");
			// 生成带小标题锚点的数据
			testM.appendTail(sb);
			return new String[] { sb.toString(), smallPro.toString() };
		}
		return null;
	}

	/**
	 * 字符串截取
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param beginIndex
	 *            开始位置
	 * @param endIndex
	 *            结束位置
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
	 * 随机生成几个不同的数
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
	 * 按半角截取字符串,中文算两个半角字符
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
			int n = 0; // 表示当前的字节数
			int i = 2; // 要截取的字节数，从第3个字节开始
			for (; i < bytes.length && n < length; i++) {
				// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
				if (i % 2 == 1) {
					n++; // 在UCS2第二个字节时n加1
				} else {
					// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
					if (bytes[i] != 0) {
						n++;
					}
				}
			}
			// 如果i为奇数时，处理成偶数
			if (i % 2 == 1) {
				// 该UCS2字符是汉字时，去掉这个截一半的汉字
				if (bytes[i - 1] != 0)
					i = i - 1;
				// 该UCS2字符是字母或数字，则保留该字符
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
	 * 字符串截取
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param beginIndex
	 *            开始位置
	 * @param endIndex
	 *            结束位置
	 * @param endMark
	 *            在结束处加...符
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
	 * 去掉超链接和
	 * <P>
	 * </P>
	 * ,文章摘要使用
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
	 * 过滤指定的html标签（只去除标签，保留内容）
	 * 
	 * @param txt
	 *            - 进行过滤的文本
	 * @param tags
	 *            - 要过滤的标签
	 */
	public static String removeHtmlTag(String txt, String[] tags) {
		String result = txt;
		for (String tag : tags) { // 循环每个标签
			String[] regs = { "<" + tag + "\\s[^>]*>", "</" + tag + ">" }; // 包括开始标签和结束标签
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
	 * 过滤指定的html标签（包括标签内容），不支持同标签嵌套
	 * 
	 * @param txt
	 *            - 进行过滤的文本
	 * @param tags
	 *            - 要过滤的标签
	 */
	public static String removeHtmlTagAndContent(String txt, String[] tags) {
		String result = txt;
		for (String tag : tags) { // 循环每个标签
			String reg = "<" + tag + "(\\s[^>]*>|>)[\\w\\W]*?</" + tag + ">"; // 进行匹配的正则表达式
			Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(result);
			while (matcher.find()) {
				result = matcher.replaceAll("");
			}
		}
		return result;
	}

	/**
	 * 给超链接加上:target="_blank"
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

	// 获取26个字母
	public static char[] getEnChar() {
		char[] cs = new char[26];
		char c = 'A' - 1;
		for (int i = 0; c++ < 'Z'; i++) {
			cs[i] = c;
		}
		return cs;
	}

	// 判断是否在26个字母里面
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

	// 转化成大写
	public String toUpperCase(String str) {
		if (isBlank(str)) {
			return "";
		}
		return str.toUpperCase();
	}

	// 转化成大写
	public String toLowerCase(String str) {
		if (isBlank(str)) {
			return "";
		}
		return str.toLowerCase();
	}

	/**
	 * 根据大图获得小图地址
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
	 * 去掉html代码
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
	 * 把字符串切成每个字符
	 * 
	 * @param str
	 * @return
	 */
	public static char[] toArray(String str) {
		return str.toCharArray();
	}

	/**
	 * b代替a
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
	 * b代替a
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
	 * 把电话号码中间四位用****代替
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
	 * 根据价格获取价位
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
	 * 根据价格获取价位
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
		if (str == null || str.equals("待定"))
			return "待定";
		for (int i = 0, n = str.length(); i < n; i++) {
			if (str.substring(0, 1).equals("0"))
				str = str.substring(1, str.length());
		}
		if ("0".equals(str) || "".equals(str))
			str = "待定";
		return str;
	}

	/**
	 * 去除数字结尾多余的0和小数点。例如，<br>
	 * 输入4.00，得到4<br>
	 * 输入3.50，得到3.5
	 */
	public static String removeTrailingZeros(String num) {
		if (isEmpty(num)) {
			return num;
		}
		int end = num.length();
		if (num.indexOf(".") != -1) { // 有小数点，去除结尾多余的0
			while (num.charAt(end - 1) == '0') {
				end--;
			}
		}
		if (num.charAt(end - 1) == '.') { // 去除多余的小数点
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
	 * 判断2个字符串是否不相等
	 */
	public static boolean isNotEquals(String str1, String str2) {
		if ((isEmpty(str1) && !isEmpty(str2))
				|| (!isEmpty(str1) && isEmpty(str2)))// 一个空一个非空,不相等
			return true;
		if (isEmpty(str1) && isEmpty(str2))// 两个都为空,相等
			return false;
		if (str1.equals(str2))
			return false;
		else
			return true;
	}

	/**
	 * 获取最大最小值中间范围的随机数
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
	 * 判断是否只存在大写和数字
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
	 * 消除List<String>中的重复值
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
	 * 判断是否符合某个正则表达式
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
	 * 给数字每隔三位加逗号
	 * 
	 * @param num
	 * @return
	 */
	public static String getReadabilityNum(String num) {
		String result = "";
		StringBuffer sb = new StringBuffer(num);
		num = num.trim();
		if (num.indexOf("百万") != -1) {
			num = num.replace("百万", "").trim();
			num += "000000";
		} else if (num.indexOf("万") != -1) {
			num = num.replace("万", "").trim();
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

	// 取字符串回车的第一行
	public static String getFirstLine(String content) {
		if (content == null)
			return "";
		int index = content.indexOf("\r\n");
		if (index > 0) {
			return content.substring(0, index);
		}
		return content;
	}

	// 是否数字
	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	// 过滤所有html标签
	public static String Html2Text(String inputString) {
		if (StringUtil.isEmpty(inputString))
			return "";
		String htmlStr = inputString; // 含html标签的字符串
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
			String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
			String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}
	
	/**
	 * 根据字符串长度 获取对应字号(仅限顶部导航页使用)
	 * @param str
	 * @return    
	 * @return_type String
	 * @author bjzhouling
	 */
  public static String getFontSizeByStringLength(String str){
	  if(str==null || str.equals("")){
		  return "0";//0不是固定字号，只为传值进行区分
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
		  return "21";//21不是固定字号，只为传值进行区分
	  }else{
		  return "20";//20不是固定字号，只为传值进行区分
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
   * 提取电话号码
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
   * 去除HTML标签
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
      htmlStr = m_script.replaceAll(""); // 过滤script标签  

      Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
      Matcher m_style = p_style.matcher(htmlStr);  
      htmlStr = m_style.replaceAll(""); // 过滤style标签  

      Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
      Matcher m_html = p_html.matcher(htmlStr);  
      htmlStr = m_html.replaceAll(""); // 过滤html标签  

      Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);  
      Matcher m_space = p_space.matcher(htmlStr);  
      htmlStr = m_space.replaceAll(""); // 过滤空格回车标签  
      return htmlStr.trim(); // 返回文本字符串  
  } 
  
  
  /**
   * 将list转变为字符串
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
   * 返回正则匹配到的字符串
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
   * 处理文本格式为html格式（换行：\r\n改为<br/> , " "改为&nbsp;）
   * @param text
   * @return    
   * @return_type String
   * @author bjzhouling
   */
  public static String handleTextPatternToHtml(String text){
	  return text.replace("\r\n", "<br/>").replace(" ", "&nbsp;");
  }
  
  /**
   * 处理html格式为文本格式（换行：<br/>改为\r\n , &nbsp;改为" "）
   * @param text
   * @return    
   * @return_type String
   * @author bjzhouling
   */
  public static String handleHtmlPatternToText(String text){
	  return text.replace("<br/>", "\r\n").replace("&nbsp;", " ");
  }
  
  /*
   * MD5加密相关变量
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
   * 通过文件二进制数据计算md5值
   * 
   * @param fileData 文件二进制数据
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
   * 通过正则表达式获取指定字符串
   */
  public static String getMatcher(String regex, String source) {  
    String result = "";  
    Pattern pattern = Pattern.compile(regex);  
    Matcher matcher = pattern.matcher(source);  
    while (matcher.find()) {  
        result = matcher.group(1);//只取第一组  
    }  
    return result;
  }
  
  /*
   * 判断电话号码是否符合校验规则
   */
  public static boolean verifyMobile(String mobile) {
  	Pattern pattern = Pattern.compile("^1\\d{10}$");
		Matcher matcher = pattern.matcher(mobile);
		if (!matcher.find()) {// 验证手机号码
			return false;
		}else{
			return true;
		}
  }
  
  //判断str字符串中text出现的位置
  public static int indexOf(String str,String text){
  	return str.indexOf(text);
  }
}
