package com.zyh.controller.consult.vo;

import com.github.pagehelper.PageInfo;

public class ConsultQueryVO  extends PageInfo{
	private String createuser;
	
	private String phone;
	
	private String state;
	
	private String userid;

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
