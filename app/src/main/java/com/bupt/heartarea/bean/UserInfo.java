package com.bupt.heartarea.bean;

public class UserInfo {
	private int _id;
	private String uname;
	private String upwd;
	private int uimage;

	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInfo(int _id, String uname, String upwd, int uimage) {
		super();
		this._id = _id;
		this.uname = uname;
		this.upwd = upwd;
		this.uimage = uimage;
	}
	
	

	public UserInfo(String uname, String upwd, int uimage) {
		super();
		this.uname = uname;
		this.upwd = upwd;
		this.uimage = uimage;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	public int getUimage() {
		return uimage;
	}

	public void setUimage(int uimage) {
		this.uimage = uimage;
	}

}
