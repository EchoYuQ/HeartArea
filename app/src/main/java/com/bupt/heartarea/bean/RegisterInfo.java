package com.bupt.heartarea.bean;

public class RegisterInfo {
	int code;
	String msg;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "RegisterInfo [code=" + code + ", msg=" + msg + "]";
	}
	
}
