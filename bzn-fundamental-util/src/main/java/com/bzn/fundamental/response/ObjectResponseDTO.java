package com.bzn.fundamental.response;

/**
 * App响应DTO对象, 最终会序列化成JSON格式, 例如: </br>
 * { "code" : "xx", "message" : "xxxx", "data":{} } </br>
 */
public class ObjectResponseDTO extends ResponseDTO {

	// 业务数据, 复杂数据形式, 以 json 格式描述的数据字段如: {"a" : "av", "b" : "bv"}
	private Object data;

	/**
	 * @param code 响应码
	 * @param message 响应信息
	 * @param data 响应数据, 类型为POJO, 序列化后作为data中的json对象
	 */
	public ObjectResponseDTO(String code, String message, Object data) {
		super(code, message);
		this.data = data;
	}

	public ObjectResponseDTO(Object data) {
		super(CODE_SUCCESS, "");
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}