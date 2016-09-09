package com.bzn.fundamental.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Java属性拷贝
 * 
 * @author：fengli
 * @since：2016年8月10日 下午3:59:29
 * @version:
 */
public class BeanUtils {

	/**
	 * 对象属性拷贝 注意:只拷贝非空字段
	 * 
	 * @param source
	 * @param target
	 */
	public static void copyProperties(Object source, Object target) {

		if (target == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (source == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}

		BeanUtilsBean beanUtil = BeanUtilsBean.getInstance();
		PropertyUtilsBean propertyUtilsBean = beanUtil.getPropertyUtils();

		PropertyDescriptor[] origDescriptors = propertyUtilsBean.getPropertyDescriptors(source);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			try {
				Object value = propertyUtilsBean.getSimpleProperty(source, name);
				if (value != null) {// 只拷贝非空字段
					beanUtil.copyProperty(target, name, value);
				}
			} catch (Exception e) {
				// Should not happen
			}
		}
	}

	/**
	 * 将对象中的属性拷贝到Map中
	 * 
	 * @param source
	 * @param map
	 * @throws ReflectiveOperationException
	 */
	public static void Obj2Map(Object source, Map<String, Object> map)
			throws ReflectiveOperationException {
		Obj2Map(source, map, null);
	}

	/**
	 * 将对象中的属性拷贝到Map中
	 * 
	 * @param source
	 * @param map
	 * @param exinclude 不需要转换的字段名称
	 * @throws ReflectiveOperationException
	 */
	public static void Obj2Map(Object source, Map<String, Object> map, String[] exinclude)
			throws ReflectiveOperationException {
		if (source == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (map == null) {
			map = new HashMap<>();
		}

		BeanUtilsBean beanUtil = BeanUtilsBean.getInstance();
		PropertyUtilsBean propertyUtilsBean = beanUtil.getPropertyUtils();

		PropertyDescriptor[] origDescriptors = propertyUtilsBean.getPropertyDescriptors(source);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			if (!propertyUtilsBean.isReadable(source, name)) {
				continue;
			}
			if (exinclude != null && ArrayUtils.contains(exinclude, name)) {
				continue;
			}
			try {
				Object value = propertyUtilsBean.getSimpleProperty(source, name);
				map.put(name, value);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw e;
			}
		}
	}

}
