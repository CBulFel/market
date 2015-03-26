/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.mark.market.bean.Task;

/**
 * @author mazhao Describe
 */
public class MainService extends Service implements Runnable {

	private static Queue<Task> tasks = new LinkedList<Task>();
	private static ArrayList<Activity> appActivities = new ArrayList<Activity>();
	private boolean isRun;
	private Handler handler;

	@SuppressLint("HandlerLeak")
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		// 任务队列的 任务处理完成的消息
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
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

	// UI线程新开任务的接口
	public static void newTask(Task task) {
		tasks.add(task);
	}

	// UI新开任务时，需要传入Activity实例，为了refresh操作
	public static void addActivty(Activity activity) {
		appActivities.add(activity);
	}

	// 任务完成后，应该remove对应的Activity实例，防止下次同一Activity的不同实例refresh混淆
	public static void reMoveActivty(Activity activity) {
		appActivities.remove(activity);
	}

	/**
	 * @param task
	 *            Tasks处理Tasks堆栈中的task
	 */
	private void doTask(Task task) {
		// TODO Auto-generated method stub
		Message msg = handler.obtainMessage();
		msg.what = task.getTaskID();

		switch (task.getTaskID()) {

		case Task.MARKET_LOGIN:
			/*
			 * 登录任务
			 */
			break;
		case Task.GET_USERINFO:
			/*
			 * 加载更多
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
