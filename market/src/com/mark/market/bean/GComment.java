package com.mark.market.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mazhao
 * 
 *商品评论实体类 
 */
public class GComment implements Serializable{

	private static final long serialVersionUID = -9013370444421591452L;
	private String gcId;
	private Good goods;
	private String gcMsg;
	private Date gcTime;
	private Boolean gcIsNew;
	private String uid;
	
	public GComment() {
		super();
	}

	public GComment(String gcId, Good goods, String gcMsg, Date gcTime,
			Boolean gcIsNew, String uid) {
		super();
		this.gcId = gcId;
		this.goods = goods;
		this.gcMsg = gcMsg;
		this.gcTime = gcTime;
		this.gcIsNew = gcIsNew;
		this.uid = uid;
	}

	public String getGcId() {
		return gcId;
	}

	public void setGcId(String gcId) {
		this.gcId = gcId;
	}

	public Good getGoods() {
		return goods;
	}

	public void setGoods(Good goods) {
		this.goods = goods;
	}

	public String getGcMsg() {
		return gcMsg;
	}

	public void setGcMsg(String gcMsg) {
		this.gcMsg = gcMsg;
	}

	public Date getGcTime() {
		return gcTime;
	}

	public void setGcTime(Date gcTime) {
		this.gcTime = gcTime;
	}

	public Boolean getGcIsNew() {
		return gcIsNew;
	}

	public void setGcIsNew(Boolean gcIsNew) {
		this.gcIsNew = gcIsNew;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
