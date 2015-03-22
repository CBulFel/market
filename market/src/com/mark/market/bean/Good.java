package com.mark.market.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author mazhao
 *
 *商品实体类
 */
public class Good {

	private static final long serialVersionUID = -8330686296777483266L;
	private String gid;
	private User user;
	private String gname;
	private String gcategory;
	private String gdegree;//新旧程度
	//价格区间
	private Double gprice;
	private Double gprePrice;
	
	private String gphone;
	private String gplace;
	private String gimg1;
	private String gimg2;
	private String gimg3;
	private String gimg4;
	private String gimg5;
	private String gimg6;
	private String gdescription;
	private Integer gcollectNum;
	private Integer gcommentNum;
	private Date gstartTime;
	private Date gendTime;
	private Boolean gvalid;//是否有效
	private Date gtime;
	private Set<GComment> gcomments = new HashSet<GComment>(0);
	public Good() {
		super();
	}
	public Good(String gid, User user, String gname, String gcategory,
			String gdegree, Double gprice, Double gprePrice, String gphone,
			String gplace, String gimg1, String gimg2, String gimg3,
			String gimg4, String gimg5, String gimg6, String gdescription,
			Integer gcollectNum, Integer gcommentNum, Date gstartTime,
			Date gendTime, Boolean gvalid, Date gtime, Set<GComment> gcomments) {
		super();
		this.gid = gid;
		this.user = user;
		this.gname = gname;
		this.gcategory = gcategory;
		this.gdegree = gdegree;
		this.gprice = gprice;
		this.gprePrice = gprePrice;
		this.gphone = gphone;
		this.gplace = gplace;
		this.gimg1 = gimg1;
		this.gimg2 = gimg2;
		this.gimg3 = gimg3;
		this.gimg4 = gimg4;
		this.gimg5 = gimg5;
		this.gimg6 = gimg6;
		this.gdescription = gdescription;
		this.gcollectNum = gcollectNum;
		this.gcommentNum = gcommentNum;
		this.gstartTime = gstartTime;
		this.gendTime = gendTime;
		this.gvalid = gvalid;
		this.gtime = gtime;
		this.gcomments = gcomments;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getGcategory() {
		return gcategory;
	}
	public void setGcategory(String gcategory) {
		this.gcategory = gcategory;
	}
	public String getGdegree() {
		return gdegree;
	}
	public void setGdegree(String gdegree) {
		this.gdegree = gdegree;
	}
	public Double getGprice() {
		return gprice;
	}
	public void setGprice(Double gprice) {
		this.gprice = gprice;
	}
	public Double getGprePrice() {
		return gprePrice;
	}
	public void setGprePrice(Double gprePrice) {
		this.gprePrice = gprePrice;
	}
	public String getGphone() {
		return gphone;
	}
	public void setGphone(String gphone) {
		this.gphone = gphone;
	}
	public String getGplace() {
		return gplace;
	}
	public void setGplace(String gplace) {
		this.gplace = gplace;
	}
	public String getGimg1() {
		return gimg1;
	}
	public void setGimg1(String gimg1) {
		this.gimg1 = gimg1;
	}
	public String getGimg2() {
		return gimg2;
	}
	public void setGimg2(String gimg2) {
		this.gimg2 = gimg2;
	}
	public String getGimg3() {
		return gimg3;
	}
	public void setGimg3(String gimg3) {
		this.gimg3 = gimg3;
	}
	public String getGimg4() {
		return gimg4;
	}
	public void setGimg4(String gimg4) {
		this.gimg4 = gimg4;
	}
	public String getGimg5() {
		return gimg5;
	}
	public void setGimg5(String gimg5) {
		this.gimg5 = gimg5;
	}
	public String getGimg6() {
		return gimg6;
	}
	public void setGimg6(String gimg6) {
		this.gimg6 = gimg6;
	}
	public String getGdescription() {
		return gdescription;
	}
	public void setGdescription(String gdescription) {
		this.gdescription = gdescription;
	}
	public Integer getGcollectNum() {
		return gcollectNum;
	}
	public void setGcollectNum(Integer gcollectNum) {
		this.gcollectNum = gcollectNum;
	}
	public Integer getGcommentNum() {
		return gcommentNum;
	}
	public void setGcommentNum(Integer gcommentNum) {
		this.gcommentNum = gcommentNum;
	}
	public Date getGstartTime() {
		return gstartTime;
	}
	public void setGstartTime(Date gstartTime) {
		this.gstartTime = gstartTime;
	}
	public Date getGendTime() {
		return gendTime;
	}
	public void setGendTime(Date gendTime) {
		this.gendTime = gendTime;
	}
	public Boolean getGvalid() {
		return gvalid;
	}
	public void setGvalid(Boolean gvalid) {
		this.gvalid = gvalid;
	}
	public Date getGtime() {
		return gtime;
	}
	public void setGtime(Date gtime) {
		this.gtime = gtime;
	}
	public Set<GComment> getGcomments() {
		return gcomments;
	}
	public void setGcomments(Set<GComment> gcomments) {
		this.gcomments = gcomments;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	
}
