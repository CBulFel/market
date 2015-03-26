/**
 * 
 */
package com.mark.market.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author mazhao
 * 
 *					用户实体类
 */
public class User {

	
	private static final long serialVersionUID = 4034229220029303154L;
	private String uid;
	private int ultUid;
	
	private String uname;
	private String upwd;
	private String usex;
	private String uphone;
	private String uemail;
	//ע��ʱ��
	private Date utime;
	
	private Boolean uvalid;
	
	private String ugids;
	private Set<Good> goodses = new HashSet<Good>(0);
	
	public User(String uid, int ultUid, String uname, String upwd, String usex,
			String uphone, String uemail, Date utime, Boolean uvalid,
			String ugids, Set<Good> goodses) {
		super();
		this.uid = uid;
		this.ultUid = ultUid;
		this.uname = uname;
		this.upwd = upwd;
		this.usex = usex;
		this.uphone = uphone;
		this.uemail = uemail;
		this.utime = utime;
		this.uvalid = uvalid;
		this.ugids = ugids;
		this.goodses = goodses;
	}
	/*
	private Set<UserMsg> userMsgs = new HashSet<UserMsg>(0);
	private Set<Needs> needses = new HashSet<Needs>(0);
	private Set<AdminMsg> adminMsgs = new HashSet<AdminMsg>(0);
	private Set<BackMsg> backMsgs = new HashSet<BackMsg>(0);
	*/
	
	
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getUltUid() {
		return ultUid;
	}
	public void setUltUid(int ultUid) {
		this.ultUid = ultUid;
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
	public String getUsex() {
		return usex;
	}
	public void setUsex(String usex) {
		this.usex = usex;
	}
	public String getUphone() {
		return uphone;
	}
	public void setUphone(String uphone) {
		this.uphone = uphone;
	}
	public String getUemail() {
		return uemail;
	}
	public void setUemail(String uemail) {
		this.uemail = uemail;
	}
	public Date getUtime() {
		return utime;
	}
	public void setUtime(Date utime) {
		this.utime = utime;
	}
	public Boolean getUvalid() {
		return uvalid;
	}
	public void setUvalid(Boolean uvalid) {
		this.uvalid = uvalid;
	}
	public String getUgids() {
		return ugids;
	}
	public void setUgids(String ugids) {
		this.ugids = ugids;
	}
	public Set<Good> getGoodses() {
		return goodses;
	}
	public void setGoodses(Set<Good> goodses) {
		this.goodses = goodses;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
