package com.hrm.bean;

import java.util.Date;

public class Employee {
	private int eid;
	private Dept d_id;
	private Job j_id;
	private String name;
	private String card_id;//身份证号码
	private String address;
	private String post_code;//邮编
	private String tel;//电话
	private String phone;//手机
	private String qq_num;
	private String email;
	private int sex;
	private String party;//政治面貌
	private Date birthday;//生日
	private String race;//民族
	private String educate;//学历
	private String speciality;//专业
	private String hobby;//爱好
	private String remark;//备注
	private Date create_date;//创建日期
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public Dept getD_id() {
		return d_id;
	}
	public void setD_id(Dept d_id) {
		this.d_id = d_id;
	}
	public Job getJ_id() {
		return j_id;
	}
	public void setJ_id(Job j_id) {
		this.j_id = j_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQq_num() {
		return qq_num;
	}
	public void setQq_num(String qq_num) {
		this.qq_num = qq_num;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getEducate() {
		return educate;
	}
	public void setEducate(String educate) {
		this.educate = educate;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	
}
