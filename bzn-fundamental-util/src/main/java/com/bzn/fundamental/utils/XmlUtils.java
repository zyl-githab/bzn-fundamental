package com.bzn.fundamental.utils;

import org.apache.commons.lang3.ArrayUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * xml和javabean转换工具类
 * 
 * @author：fengli
 * @since：2016年8月16日 下午5:07:27
 * @version:
 */
public class XmlUtils<T> {

	/**
	 * 默认对象转为xml字符串
	 * 
	 * @param entry
	 * @return
	 */
	public String toDefaultXML(T entry) {
		XStream xStream = new XStream(new DomDriver());
		return xStream.toXML(entry);
	}

	/**
	 * 获取去掉类包名的xml字符串
	 * 
	 * @param entry
	 * @return
	 */
	public String toSimpleXML(T entry, Class<?>... clazzes) {
		XStream xStream = new XStream(new DomDriver());

		if (ArrayUtils.isNotEmpty(clazzes)) {
			for (Class<?> clazz : clazzes) {
				xStream.alias(clazz.getSimpleName(), clazz);
			}
		}

		return xStream.toXML(entry);
	}

	/**
	 * 生成根节点为root变量的xml
	 * 
	 * @param entry
	 * @param root
	 * @return
	 */
	public String toSimpleXML(T entry, String root, Class<?>... clazzes) {
		XStream xStream = new XStream(new DomDriver());

		if (ArrayUtils.isNotEmpty(clazzes)) {
			for (Class<?> clazz : clazzes) {
				xStream.alias(clazz.getSimpleName(), clazz);
			}
		}
		xStream.alias(root, entry.getClass());
		return xStream.toXML(entry);
	}

	/**
	 * 添加根节点对象转为xml字符串
	 * 
	 * @param entry
	 * @param root
	 * @return
	 */
	public String toXML(T entry, String root) {
		String str = toSimpleXML(entry);
		return "<" + root + ">" + str.replaceAll("\r| |\n", "") + "</" + root + ">";
	}

	/**
	 * 从xml字符串转为T对象
	 * 
	 * @param xml
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T fromXML(String xml, String root, Class<T> rootClazz, Class<?>... clazzes) {
		XStream xStream = new XStream(new DomDriver());
		if (ArrayUtils.isNotEmpty(clazzes)) {
			for (Class<?> clazz : clazzes) {
				xStream.alias(clazz.getSimpleName(), clazz);
			}
		}
		xStream.alias(root, rootClazz);
		return (T) xStream.fromXML(xml);
	}

	/**
	 * 去除根节点转为clazz对象
	 * 
	 * @param xml
	 * @param clazz
	 * @param root
	 * @return
	 */
	public T fromXML(String xml, Class<T> clazz, String root) {
		xml = xml.replaceFirst("<" + root + ">", "");
		xml = xml.substring(0, xml.lastIndexOf("<"));
		return fromXML(xml, root, clazz);
	}
}
