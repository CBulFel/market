/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.mark.market.bean.Task;

/**
 * @author mazhao
 * @describ
 */
public class MainService extends Service implements Runnable {

	private static Queue<Task> tasks = new LinkedList<Task>();
	private static ArrayList<Activity> appActivities = new ArrayList<Activity>();
	private boolean isRun;
	private Handler handler;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		// 任务队列，
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				// super.handleMessage(msg);
				switch (msg.what) {
				case Task.MARKET_LOGIN:
					break;
				case Task.GET_USERINFO:
					break;
				case Task.GET_GOODS:
					break;
				case Task.UPDATE_GOODS:
					break;
				case Task.LOADMORE:
					break;
				default:
					break;
				}
			}

		};
		isRun=true;
		Thread thread=new Thread(this);
		thread.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRun){
			if(!tasks.isEmpty()){
				doTask(tasks.poll());
			}
		}
	}

	//UI线程新开任务的接口
	public static void newTask(Task task)
	{
		tasks.add(task);
	}
	
	/**
	 * @param task Tasks堆栈中的task
	 */
	private void doTask(Task task) {
		// TODO Auto-generated method stub
		Message msg=handler.obtainMessage();
		msg.what=task.getTaskID();
		
		switch(task.getTaskID())
		{
		
		case Task.MARKET_LOGIN:
			/*
			 * 登陆操作
			*/
			break;
		case Task.GET_USERINFO:
			/*
			 * 获得用户信息
			*/
			break;
		case Task.GET_GOODS:
			/*
			 * 获得商品列表
			*/
			break;
		case Task.UPDATE_GOODS:
			/*
			 * 更新商品列表
			*/
			break;
		case Task.LOADMORE:
			/*
			 * 加载更多
			*/
			break;
		default:
			break;
		
		
		}
		
		
		
		
	}

}
