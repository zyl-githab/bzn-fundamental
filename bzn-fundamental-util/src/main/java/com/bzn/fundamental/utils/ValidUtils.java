package com.bzn.fundamental.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

/**
 * 后台验证工具类
 * 
 * @author：fengli
 * @since：2016年8月25日 上午11:04:05
 * @version:
 */
public class ValidUtils {

	/**
	 * logger:日志工具类
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidUtils.class);

	/**
	 * 获取验证错误信息
	 * 
	 * @param errors
	 * @return
	 */
	public static List<Map<String, Object>> getValidErrorMsg(Errors errors) {

		List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
		List<ObjectError> allErrors = errors.getAllErrors();
		if (allErrors != null) {

			for (ObjectError error : allErrors) {
				Map<String, Object> errorMap = new HashMap<String, Object>();
				Class<?> clazz = error.getClass();

				try {
					Field field = clazz.getDeclaredField("field");
					field.setAccessible(true);
					errorMap.put("field", field.get(error));
				} catch (Exception e) {
					LOGGER.error("get fileValue error", e);
				}

				errorMap.put("code", error.getCode());
				errorMap.put("message", error.getDefaultMessage());
				errorMap.put("objectName", error.getObjectName());

				errorList.add(errorMap);
			}
		}

		return errorList;
	}

	public static List<Map<String, Object>> validate(Object targetObject) {
		Validator validator = new Validator();
		List<ConstraintViolation> errors = validator.validate(targetObject);

		if (CollectionUtils.isEmpty(errors)) {
			return null;
		}

		List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
		for (ConstraintViolation error : errors) {
			Map<String, Object> errorMap = new HashMap<String, Object>();
			if ("-1".equals(error.getErrorCode())) {
				errorMap.put("message", error.getMessage());
			} else {
				String first = error.getMessage().split(" ")[0];
				errorMap.put("message", StringUtils.substringAfterLast(first, ".") + " "
						+ StringUtils.substringAfter(error.getMessage(), " "));
			}
			errorList.add(errorMap);
		}

		return errorList;
	}

	public static String getValidErrorMsg(List<Map<String, Object>> paramList) {
		StringBuilder sb = new StringBuilder("");
		for (Map<String, Object> paramMap : paramList) {
			sb.append(paramMap.get("message")).append(",");
		}
		return sb.toString();
	}

}
