package com.bupt.heartarea.bean;

public class LoginInfo {
	private String idcard_image_url;
	private String gender;
	private String id;
	private String name;
	private String nick;
	private String phone;
	private String sid;
	private int code;
	private String msg;
	
	public LoginInfo() {
	}
	
	

	public String getIdcard_image_url() {
		return idcard_image_url;
	}
	public void setIdcard_image_url(String idcard_image_url) {
		this.idcard_image_url = idcard_image_url;
	}
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
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
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	@Override
	public String toString() {
		return "LoginInfo [idcard_image_url=" + idcard_image_url + ", gender="
				+ gender + ", id=" + id + ", name=" + name + ", nick=" + nick
				+ ", phone=" + phone + ", code=" + code + ", msg=" + msg + "]";
	}
	
	
}
