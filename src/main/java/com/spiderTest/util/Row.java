/**
 * 
 */
package com.spiderTest.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wzxie <�ֶ�,ֵ> �����޸���һ�£�������ȫȡ��HashMap
 */
@SuppressWarnings("unchecked")
public class Row extends HashMap {
	private static final long serialVersionUID = 1L;

	protected List<Object> ordering = new ArrayList<Object>(); // <�ֶ�>

	protected Map<String, String> functionMap = null; // <�ֶ�,����>

	/**
	 * 
	 */
	public Row() {
	}

	/** ֱ����һ��Map��������һ��Rowʹ�� */
	public Row(Map map) {
		super(map);
    for(Object obj : map.keySet()){
      ordering.add(obj);
    }
	}


	/**
	 * �����ֶ��������ַ���ֵ
	 * 
	 * @param name
	 * @return
	 */
	public String gets(Object name) {
		try {
			if (get(name) != null)
				return get(name).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * �����ֶ��������ַ���ֵ,��Ϊnull�෵��Ĭ��ֵ;
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public String gets(Object name, String defaultValue) {
		try {
			if (get(name) != null && !"".equals(get(name).toString()))
				return get(name).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	/**
	 * @param name
	 * @return
	 */
	public int getInt(Object name) {
		return getInt(name,-1);
	}

	/**
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public int getInt(Object name, int defaultValue) {
		Object o = get(name);
		if (o != null) {
			try {
				return StringUtil.getInt(o.toString(), defaultValue);
			} catch (Exception e) {
			}
		}
		return defaultValue;
	}

	/**
	 * @param which
	 * @param defaultValue
	 * @return
	 */
	public int getInt(int which, int defaultValue) {
		Object key = ordering.get(which);
		return getInt(key, defaultValue);
	}

	/**
	 * @param name
	 * @return
	 */
	public float getFloat(Object name) {
		return Float.valueOf(get(name).toString()).floatValue();
	}

	/**
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public float getFloat(Object name, float defaultValue) {
		Object o = get(name);
		if (o != null)
			try {
				return Float.valueOf(o.toString()).floatValue();
			} catch (Exception e) {
			}
		return defaultValue;
	}

	/**
	 * @param which
	 * @param defaultValue
	 * @return
	 */
	public float getFloat(int which, float defaultValue) {
		Object key = ordering.get(which);
		return getFloat(key, defaultValue);
	}

	/**
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public long getLong(Object name, long defaultValue) {
		Object o = get(name);
		if (o != null)
			try {
				return Long.valueOf(o.toString()).longValue();
			} catch (Exception e) {
			}
		return defaultValue;
	}

	/**
	 * @param which
	 * @return
	 */
	public Object get(int which) {
		Object key = ordering.get(which);
		return get(key);
	}

	/**
	 * @param which
	 * @return
	 */
	public Object getKey(int which) {
		Object key = ordering.get(which);
		return key;
	}

  public String[] getKeys() {
		Set keys = this.keySet();
		Iterator iter = keys.iterator();
		String[] strs = new String[keys.size()];
		int i = 0;
		while (iter.hasNext()) {
			strs[i] = iter.next().toString();
			i++;
		}
		return strs;
	}

	/**
	 * 
	 */
  public void dump() {
		for (Iterator e = keySet().iterator(); e.hasNext();) {
			String name = (String) e.next();
			Object value = get(name);
			System.out.println(name + "=" + value + ", ");
		}
	}

  public String dumpToString() {
		StringBuffer sb = new StringBuffer();
		for (Iterator e = keySet().iterator(); e.hasNext();) {
			String name = (String) e.next();
			Object value = get(name);
			sb.append(value).append(",");
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc) �����˸����put����
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(Object name, Object value) {
		if (!containsKey(name)) {
			ordering.add(name); // ������������
		}
		super.put(name, value);
		if (name instanceof String && functionMap != null && functionMap.containsKey(name))
			functionMap.remove(name);
		return value;
	}

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public int putInt(Object name, int value) {
		super.put(name, Integer.valueOf(value));
		return value;
	}

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public float putFloat(Object name, float value) {
		super.put(name, new Float(value));
		return value;
	}

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public String putFunction(String name, String value) {
		if (functionMap == null)
			functionMap = new HashMap<String, String>();
		if (this != null && this.containsKey(name)) {
			// ordering.remove(name);
			remove(name);
		}
		functionMap.put(name, value);
		return value;
	}

	/**
	 * @param name
	 * @return
	 */
	public String getFunction(String name) {
		return functionMap.get(name);
	}

  public Map getFunctionMap() {
		return this.functionMap;
	}

	/**
	 * @param fmap
	 */
	public void setFunctionMap(HashMap<String, String> fmap) {
		if (fmap != null && fmap.size() > 0) {
			if (functionMap == null)
				functionMap = new HashMap<String, String>();
			this.functionMap.putAll(fmap);
		}
	}

	/*
	 * (non-Javadoc) �����˸����putAll����
	 * 
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map otherMap) {
		Set keySet = otherMap.keySet();
		for (Object name : keySet)
			ordering.add(name);
		super.putAll(otherMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public Object remove(Object name) {
		if (ordering.remove(name))
			return super.remove(name);
		return null;
	}

	/*
	 * (non-Javadoc) �����˸����clear����
	 * 
	 * @see java.util.Map#clear()
	 */
	public void clear() {
		super.clear();
		ordering = null;
	}

	/**
	 * �õ��ж������ֶεĸ���
	 * 
	 * @return
	 */
	public int length() {
		return size();
	}

}