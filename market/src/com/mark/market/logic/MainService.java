/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.logic;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
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
import com.mark.market.ui.MarketActivity;
import com.mark.market.util.HttpconnectUtil;

/**
 * @author mazhao Describe
 */
public class MainService extends Service implements Runnable {
	public static final String MARKETHOST = "http://192.168.1.158:8080";
	public static final String goodUrlhead = "http://online.cumt.edu.cn:8080/2/toItemDetails/toItemDetailsAction_toItemDetails.do?pre=g&id=";
	private static String url_getByGid = "http://192.168.1.158:8080/market/toItemDetailsAction2_toItemDetailsJSON.do";
	private static String url_sale = "http://192.168.1.158:8080/market/post/postAction2_postJSON.do";
	private static String url_update = "http://192.168.1.158:8080/market/index/indexAction2_indexJSON.do";
	private static String url_login = "http://192.168.1.158:8080/market/login/loginAction2_loginJSON.do";
	private static String url_comment = "http://192.168.1.158:8080/market/comment/commentAction2_commentJSON.do";
	private static String url_search="http://192.168.1.158:8080/market/serach/searchAction2_searchJSON.do";
	private static final String TAG = "market->MainService";
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
				Log.w(TAG, "get message" + msg.what);
				Activity activity = null;
				switch (msg.what) {
				case Task.MARKET_LOGIN:
					activity = getActivityByName("LoginActivity");
					((MarketActivity) activity).refresh(Task.MARKET_LOGIN,
							msg.obj);
					break;
				case Task.GET_DETAIL_BY_GID:
					activity = getActivityByName("Gooddetail");
					((MarketActivity) activity).refresh(Task.GET_USERINFO,
							msg.obj);
					break;
				case Task.GET_GOODS:
					activity = getActivityByName("MainActivity");
					((MarketActivity) activity)
							.refresh(Task.GET_GOODS, msg.obj);
					break;
				case Task.UPDATE_GOODS:
					break;
				case Task.LOADMORE:
					activity = getActivityByName("MainActivity");
					((MarketActivity) activity).refresh(Task.LOADMORE, msg.obj);
					break;
				case Task.GOODS_SEARCH:
					activity = getActivityByName("SearchActivity");
					((MarketActivity) activity).refresh(Task.LOADMORE, msg.obj);
					break;
				case Task.SALE_GOOD:
					activity = getActivityByName("GoodsEdit");
					((MarketActivity) activity)
							.refresh(Task.SALE_GOOD, msg.obj);
				case Task.COMMENT:
					/*
					 * activity = (MarketAcitivity)
					 * getActivityByName("Gooddetail");
					 * activity.refresh(Task.SALE_GOOD, msg.obj);
					 */
					break;

				default:
					removeActivity(activity);
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
	public static void newTask(Activity activity, Task task) {

		tasks.add(task);
		if (activity != null)
			appActivity.add(activity);
	}

	// UI新开任务时，需要传入Activity实例，为了refresh操作
	/*
	 * public static void addContext(Context context) {
	 * appActivities.add(activity); }
	 */

	// 任务完成后，应该remove对应的Activity实例，防止下次同一Activity的不同实例refresh混淆
	public static void removeActivity(Activity activity) {
		if (activity != null) {
			appActivity.remove(activity);
			Log.w(TAG, activity.getLocalClassName() + "removed!");
		}
	}

	// 通过name获取新开任务时传递过来的Activity实例
	public Activity getActivityByName(String name) {
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
		try {
			switch (task.getTaskID()) {

			case Task.MARKET_LOGIN:
				// 登录任务
				Log.w(TAG, "login->do");
				msg.obj = HttpconnectUtil
						.getResult(url_login, task.getParams());
				break;
			case Task.GET_USERINFO:
				// 获得用户信息,用于用户页
				msg.obj = HttpconnectUtil.getResult("", task.getParams());
				break;
			case Task.GET_GOODS:
				// 获得商品列表
//				msg.obj = HttpconnectUtil.getResult(url_update, null);
				break;
			case Task.GET_DETAIL_BY_GID:
				// 获取商品详情
				/*msg.obj = HttpconnectUtil.getResult(url_getByGid,
						task.getParams());*/
				break;
			case Task.LOADMORE:
				// 加载更多

				break;
			case Task.GOODS_SEARCH:
				// 搜索商品
				msg.obj = HttpconnectUtil.getResult(url_search, task.getParams());
				break;
			case Task.SALE_GOOD:
				// 发布商品
				HttpconnectUtil.postfile(url_sale, task.getParams());
				break;
			case Task.COMMENT:
				// 评论
				msg.obj = HttpconnectUtil.getResult(url_comment,
						task.getParams());
			default:
				break;

			}
		} catch (ConnectException e) {
			// 网络连接错误connect refused异常
			Log.e(TAG, "catched:" + e.getCause());
		} catch (Exception e) {
			Log.e(TAG, "exception:" + e.getMessage());
		}

		handler.sendMessage(msg);
	}

}