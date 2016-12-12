package com.bzn.fundamental.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

public class ValidRequiredUtils<T> {

	/**
	 * 验证必填项属性
	 * 
	 * @param entry
	 * @param requiredFields
	 */
	public List<Map<String, Object>> validField(T entry, List<String> requiredFields) {
		if (entry == null || requiredFields == null) {
			return null;
		}
		Class<?> clazz = entry.getClass();
		Field[] fields = clazz.getDeclaredFields();
		if (ArrayUtils.isEmpty(fields)) {
			return null;
		}

		List<Map<String, Object>> errorList = new ArrayList<>();
		Map<String, Object> errorMap = null;
		for (Field field : fields) {
			field.setAccessible(true);
			String fieldName = field.getName();
			if (!requiredFields.contains(fieldName)) {
				continue;
			}
			try {
				Object value = field.get(entry);
				if (value == null) {
					errorMap = new HashMap<>();
					errorMap.put("message", fieldName + " is not null");
					errorList.add(errorMap);
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return errorList;
	}
}
