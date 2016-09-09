package com.bzn.fundamental.response;

/**
 * 返回传输对象
 * 
 * @author：fengli
 * @since：2016年9月1日 下午5:02:08
 * @version:
 */
public class ResponseDTO {

	public static String CODE_SUCCESS = "0";
	public static String CODE_FAIL = "9999";

	// 返回调用结果状态代码, 见附1接口调用状态码表<br>
	private String code;

	// 返回调用结果描述
	private String message;

	public ResponseDTO(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param message 错误消息
	 * @return 错误响应对象
	 */
	public static ResponseDTO Error(String message) {
		return new ResponseDTO(CODE_FAIL, message);
	}

	/**
	 * @param message 成功消息
	 * @return 成功响应对象
	 */
	public static ResponseDTO Success(String message) {
		return new ResponseDTO(CODE_SUCCESS, message);
	}

	public static ResponseDTO Success() {
		return new ResponseDTO(CODE_SUCCESS, "");
	}

	public static ResponseDTO Empty() {
		return new ResponseDTO("", "");
	}
}
