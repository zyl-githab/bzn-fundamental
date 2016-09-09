package com.bzn.fundamental.mongodb.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.mongodb.core.query.Update;

/**
 * 属性拷贝
 * 
 * @author：fengli    
 * @since：2016年8月11日 下午5:00:54 
 * @version:
 */
public class UpdateBeanUtils {

	/**
	 * 将对象中的属性拷贝到Update中
	 * 
	 * @param source
	 * @param update
	 */
	public static void ObjToUpdate(Object source, Update update) {
		ObjToUpdate(source, update, null);
	}

	/**
	 * 将对象中的属性拷贝到Update中
	 * 
	 * @param source
	 * @param update
	 * @param exinclude 不需要拷贝的字段名称
	 */
	public static void ObjToUpdate(Object source, Update update, String[] exinclude) {
		if (source == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (update == null) {
			update = new Update();
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
				if (value == null) {
					continue;
				}
				update.set(name, value);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {

			}
		}
	}
}
