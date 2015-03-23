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
 * @describ ����ʵ����
 */
public class Task {
	//��½
	public static final int MARKET_LOGIN=1;
	//����û���Ϣ
	public static final int GET_USERINFO=2;
	//�����Ʒ�б�
	public static final int GET_GOODS=3;
	//������Ʒ�б�
	public static final int UPDATE_GOODS=5;
	//���ظ���
	public static final int LOADMORE=4;
	//����ID
	private int taskID;
	//�������
	private Map<string ,Object> Params;
	
	
	
	
	/**
	 * ���췽��
	 * @param taskID ����ID
	 * @param params �������
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
