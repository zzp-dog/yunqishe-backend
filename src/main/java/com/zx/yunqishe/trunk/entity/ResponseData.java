package com.zx.yunqishe.trunk.entity;

import java.util.HashMap;
import java.util.Map;
/**
 * 自定义ajax响应数据对象
 * @author Admin
 *
 */
public class ResponseData {
	//状态码
	private int status ;
	//结果提示
	private String tip ;
	//用户自定义数据,以键值对的形式出现
	private Map<String,Object> data ;
	
	/**
	 * ajax处理失败<br>
	 * 默认tip
	 * @return
	 */
	public static ResponseData error() {
		ResponseData responseData = new ResponseData();
		responseData.tip = "操作失败";
		responseData.status = 400;
		return responseData;
	}
	/**
	 * ajax处理失败<br>
	 * <strong>
	 *  该对象生成时一般会添加自定义键值对提示详细错误原因<br>
	 * 	建议使用ResponseData.error(String str).add(String key,Object value)
	 * </strong><br>
	 * 不建议单独使用ResponseData.error(String str)封装普通错误提示<br>
	 * 普通错误提示建议使用new UserExce(String str)封装<br>
	 * @return ajaxMsg
	 */
	public static ResponseData error(String str) {
		ResponseData responseData = new ResponseData();
		responseData.tip = str;
		responseData.status = 400;
		return responseData;
	}
	/**
	 * ajax处理成功（默认提示）
	 * @return
	 */
	public static ResponseData success() {
		ResponseData responseData = new ResponseData();
		responseData.tip = "操作成功";
		responseData.status = 200;
		return responseData;
	}
	
	/**
	 * ajax处理成功（带自定义提示参数）
	 * @param str 自定义成功提示
	 * @return ajaxMsg
	 */
	public static ResponseData success(String str) {
		ResponseData responseData = new ResponseData();
		responseData.tip = str;
		responseData.status = 200;
		return responseData;
	}
	/**
	 * 添加额外的数据，可链式添加
	 * @param key 
	 * @param value
	 * @return ajaxMsg
	 */
	public ResponseData add(String key,Object value) {
		if(data==null)
		data = new HashMap<String,Object>();
		data.put(key, value);
		return this;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public Map<String, Object> getDAta() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
