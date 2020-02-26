package com.zx.yunqishe.common.exception;

import com.zx.yunqishe.entity.ResponseData;

/**
 * 自定义异常类
 * 分为普通异常提示和带自定义响应数据ResponseData两种异常提示
 * 普通异常提示不建议使用带响应数据对象ResponseData生成
 * --虽然带响应数据对象也可以生成普通异常提示，但new一个对象需要代价
 * @author Admin
 *
 */
public class UserException extends Exception{
	private static final long serialVersionUID = 1L;
	private ResponseData responseData;
	private String msg ;

	/**
	 * 通过一个普通错误信息产生自定义异常
	 * @param msg
	 */
	public UserException(String msg) {
		super(msg);
		this.msg = msg;
	}
	/**
	 * 通过产生一个手动封装的响应数据对象产生自定义异常
	 * @param responseData
	 */
	public UserException(ResponseData responseData) {
		super(responseData.getTip());
		this.responseData = responseData;
	}
	/**
	 * 获取自定义异常中的响应数据对象
	 * @return responseData
	 */
	public ResponseData getResponseData() {
		return responseData;
	}
	/**
	 * 设置自定义异常中的响应数据对象
	 * 一般情况不会用到
	 * @param responseData
	 */
	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}
	/**
	 * 获取一般的错误提示信息（不带响应数据）
	 * @return msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * 设置一般的错误提示信息（不带响应数据）
	 * 一般情况不会用到
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
