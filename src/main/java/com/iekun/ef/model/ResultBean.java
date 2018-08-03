package com.iekun.ef.model;

import java.io.Serializable;

/**
 * 存放执行结果、数据，用于返回操作
 * @author zhengxuebing
 *
 * @param <T>
 */
public class ResultBean<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	public static final int NO_PERMISSION = 2;
	
	private String msg = "success";
	
	private int code = SUCCESS;
	
	private T data;
	
	public ResultBean() {
		super();
	}
	
	public ResultBean(T data) {
		super();
		this.data = data;
	}
	
	public ResultBean(Throwable e) {
		super();
		this.msg = e.toString();
		this.code = FAIL;
	}
	
	public ResultBean(T data, String message) {
		super();
		this.data = data;
		this.msg = message;
		this.code = FAIL;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}
	
	
}
