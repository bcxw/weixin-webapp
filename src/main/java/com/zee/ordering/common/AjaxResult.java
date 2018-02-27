package com.zee.ordering.common;

import java.io.Serializable;
/**
 * 封装简便操作的请求类
 * @author rygao
 */
public class AjaxResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8866854970520331747L;
	
	public static final Integer AJAX_STATUS_CODE_SUCCESS = 0 ; 
	public static final Integer AJAX_STATUS_CODE_WARN = 1 ; 
	public static final Integer AJAX_STATUS_CODE_ERROR = 2 ;
	public static final Integer AJAX_STATUS_CODE_USER_EXIST = 3 ;
	public static final Integer AJAX_STATUS_CODE_FUNCTION_EXIST_LEAF = 4 ;
	
	private Integer statusCode ; 
	private String message ;
	private Object data;
	
	public static AjaxResult success(){
		AjaxResult ajaxResult = new AjaxResult() ;
		ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
		ajaxResult.setMessage("操作成功");
		return ajaxResult ;
	}
	
	public static AjaxResult success(Object data){
		AjaxResult ajaxResult = new AjaxResult() ;
		ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
		ajaxResult.setData(data);
		return ajaxResult ;
	}
	
	public static AjaxResult success(String message){
		AjaxResult ajaxResult = new AjaxResult() ;
		ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
		ajaxResult.setMessage(message);
		return ajaxResult ;
	}
	
	public static AjaxResult error(){
		AjaxResult ajaxResult = new AjaxResult() ;
		ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_ERROR);
		ajaxResult.setMessage("操作失败");
		return ajaxResult ;
	}
	
	public static AjaxResult error(String message){
		AjaxResult ajaxResult = new AjaxResult() ;
		ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_ERROR);
		ajaxResult.setMessage(message);
		return ajaxResult ;
	}
	
	public static AjaxResult exist(){
		AjaxResult ajaxResult = new AjaxResult() ;
		ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_USER_EXIST);
		ajaxResult.setMessage("已存在");
		return ajaxResult ;
	}
	
	public static AjaxResult hasLeaf(){
		AjaxResult ajaxResult = new AjaxResult() ;
		ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_FUNCTION_EXIST_LEAF);
		ajaxResult.setMessage("菜单存在子节点");
		return ajaxResult ;
	}
	
	public static AjaxResult warn(){
		AjaxResult ajaxResult = new AjaxResult() ;
		ajaxResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_WARN);
		ajaxResult.setMessage("警告");
		return ajaxResult ;
	}
	
	public AjaxResult() {
		super();
	}

	public AjaxResult(Integer statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	} 
	
	
	
}
