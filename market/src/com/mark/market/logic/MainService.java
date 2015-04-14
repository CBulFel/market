/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.mark.market.bean.Task;
import com.mark.market.ui.MarketAcitivity;

/**
 * @author mazhao Describe
 */
public class MainService extends Service implements Runnable {
	private static final String TAG="market";
	private static Queue<Task> tasks = new LinkedList<Task>();
	private static ArrayList<Activity> appActivity = new ArrayList<Activity>();
	private boolean isRun;
	private Handler handler;

	@SuppressLint("HandlerLeak")
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.w(TAG, "mainservice launched!");
		// 任务队列的 任务处理完成的消息
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Log.w(TAG, "get message"+msg.what);
				MarketAcitivity activity = null;
				switch (msg.what) {
				case Task.MARKET_LOGIN:
					activity = (MarketAcitivity) getActivityByName("LoginActivity");
					activity.refresh(Task.MARKET_LOGIN, msg.obj);
					break;
				case Task.GET_DETAIL_BY_GID:
					activity=(MarketAcitivity)getActivityByName("Gooddetail");
					activity.refresh(Task.GET_USERINFO,msg.obj);
					break;
				case Task.GET_GOODS:
					activity=(MarketAcitivity)getActivityByName("MainActivity");
					activity.refresh(Task.GET_GOODS, msg.obj);
					break;
				case Task.UPDATE_GOODS:
					break;
				case Task.LOADMORE:
					activity=(MarketAcitivity)getActivityByName("MainActivity");
					activity.refresh(Task.LOADMORE, msg.obj);
					break;
				default:
					break;
				}
			}

		};
		// 开启第二线程，做任务队列的循环操作
		isRun = true;
		Thread thread = new Thread(this);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run() 多线程 就是不断的从Task队列中取 Task，并做处理
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isRun) {
			if (!tasks.isEmpty()) {
				doTask(tasks.poll());
			}
		}
	}

	// UI线程新开任务的接口,需传入相应activity，方便refresh操作
	public static void newTask(Activity activity,Task task) {
		
		tasks.add(task);
		appActivity.add(activity);
	}

	// UI新开任务时，需要传入Activity实例，为了refresh操作
	/*public static void addContext(Context context) {
		appActivities.add(activity);
	}*/

	// 任务完成后，应该remove对应的Activity实例，防止下次同一Activity的不同实例refresh混淆
	public static void removeActivity(Context context) {
		appActivity.remove(context);
	}

	// 通过name获取新开任务时传递过来的Activity实例
	public Object getActivityByName(String name) {
		if (!appActivity.isEmpty()) {
			for (Activity activity : appActivity) {
				if (activity.getClass().getName().indexOf(name) > 0) {
					return activity;
				}
			}
		}
		return null;
	}

	/**
	 * @param task
	 *            Tasks处理Tasks堆栈中的task
	 * @throws IOException 
	 */
	private void doTask(Task task) {
		// TODO Auto-generated method stub
		Message msg = handler.obtainMessage();
		msg.what = task.getTaskID();

		switch (task.getTaskID()) {

		case Task.MARKET_LOGIN:
		{
			/*
			 * 登录任务
			 */
			Log.w(TAG, "login->do");
		/*try {
			msg.obj=HttpconnectUtil.getResult("192.168.1.158/8080/market/login/loginAction2_loginJSON.do", task.getParams());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		}
			break;
		case Task.GET_USERINFO:
			/*
			 *获得本地存储用户信息
			 */
			Log.w(TAG, "get userinfo->do");
			break;
		case Task.GET_GOODS:
			/*
			 * 获得商品列表
			 */
		{
			Log.w(TAG, "get goods->do");
			/*String url="192.168.1.158/8080/market/index/indexAction2_indexJSON.do";
			try {
				msg.obj=HttpconnectUtil.getResult(url, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}*/
		}

			break;
		case Task.GET_DETAIL_BY_GID:
			Log.w(TAG, "get detail by Gid->do");
			/*
			 * 获取商品详情
			 */
			break;
		case Task.LOADMORE:
			/*
			 * 加载更多
			 */
			Log.w(TAG, "loadmore->do");
			break;
		default:
			break;

		}
		handler.sendMessage(msg);
	}

}
