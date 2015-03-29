/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.bean;

import java.util.Map;

import android.R.string;

/**
 * @author mazhao
 * @describ 任务实体类
 */
public class Task {
	//�登录
	public static final int MARKET_LOGIN=1;
	//获得登录用户信息
	public static final int GET_USERINFO=2;
	//�获得商品列表，同刷新
	public static final int GET_GOODS=3;
	//刷新商品列表
	public static final int UPDATE_GOODS=5;
	//加载更多
	public static final int LOADMORE=4;
	//任务ID
	private int taskID;
	//任务参数
	private Map<String, Object> Params;
	
	
	
	
	/**
	 * ���췽��
	 * @param taskID ����ID
	 * @param params �������
	 */
	public Task(int taskID, Map<String, Object> params) {
		super();
		this.taskID = taskID;
		Params = params;
	}
	
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public Map<String, Object> getParams() {
		return Params;
	}
	public void setParams(Map<String, Object> params) {
		Params = params;
	}
	
	
	
	
	
	
	
}
