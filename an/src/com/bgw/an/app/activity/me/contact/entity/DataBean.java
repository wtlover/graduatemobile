package com.bgw.an.app.activity.me.contact.entity;

public class DataBean {
	public int _id ;
	public String name;
	public String tel;
	public String address;
	public String email;
	public String photo;
	public String remark;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "DataBean [_id=" + _id + ", name=" + name + ", tel=" + tel
				+ ", address=" + address + ", email=" + email + ", photo="
				+ photo + ", remark=" + remark + "]";
	}
	
}
