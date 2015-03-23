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
	//登陆
	public static final int MARKET_LOGIN=1;
	//获得用户信息
	public static final int GET_USERINFO=2;
	//获得商品列表
	public static final int GET_GOODS=3;
	//更新商品列表
	public static final int UPDATE_GOODS=5;
	//加载更多
	public static final int LOADMORE=4;
	//任务ID
	private int taskID;
	//任务参数
	private Map<string ,Object> Params;
	
	
	
	
	/**
	 * 构造方法
	 * @param taskID 任务ID
	 * @param params 任务参数
	 */
	public Task(int taskID, Map<string, Object> params) {
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
	public Map<string, Object> getParams() {
		return Params;
	}
	public void setParams(Map<string, Object> params) {
		Params = params;
	}
	
	
	
	
	
	
	
}
