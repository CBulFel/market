package com.mark.market.ui;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mark.android_ui.Login;
import com.mark.android_ui.Login.LoginListener;
import com.mark.android_ui.MyprogressDialog;
import com.mark.market.R;
import com.mark.market.bean.Task;
import com.mark.market.bean.User;
import com.mark.market.logic.MainService;
import com.mark.market.util.LoginSessionUtil;

public class LoginActivity extends Activity implements MarketActivity,
		LoginListener {
	private static final String LOGIN_SUCCESS = "success";
	private static final String LOGIN_FAIL = "fail";
	private static final String TAG = "market->LoginActivity";
	private Login login;
	private MyprogressDialog loginprogress = null;

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// Actionbar设置
		ActionBar actionbar = getActionBar();
		/*
		 * ActionBar.LayoutParams Lparams = new ActionBar.LayoutParams(
		 * ActionBar.LayoutParams.MATCH_PARENT,
		 * ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER); View
		 * actionbarLayout = LayoutInflater.from(this).inflate( R.layout.title,
		 * null); TextView
		 * title=(TextView)actionbarLayout.findViewById(R.id.actionbar_title);
		 * title.setText("商品详情"); actionbar.setDisplayShowCustomEnabled(true);
		 * actionbar.setCustomView(actionbarLayout, Lparams);
		 */
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayHomeAsUpEnabled(true);
		// actionbar.setHomeAsUpIndicator(R.drawable.actionbar_backbtn);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setTitle("登录");

		login = (Login) findViewById(R.id.login_form);
		Intent intent = new Intent();
		intent.setClass(this, MainService.class);
		startService(intent);
		login.setLoginListener(this);
		loginprogress = new MyprogressDialog(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mark.android_ui.Login.LoginListener#loginauth()
	 */
	@Override
	public void loginauth() {
		// TODO Auto-generated method stub
		// 当前如果有登录用户就直接登录

		loginprogress.show();
		if (null != LoginSessionUtil.getLoginUser(this)) {
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			loginprogress.cancel();
			finish();
		}

		// 开启任务从本地数据库获取登录授权用户信息
		/*
		 * Task task = new Task(Task.GET_USERINFO, null);
		 * MainService.newTask(task);
		 * MainService.addActivty(LoginActivity.this);
		 */

		// 新开任务：登录操作
		if (login.getUname() != null && login.getUpwd() != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("account", login.getUname());
			params.put("pwd", login.getUpwd());
			Task task = new Task(Task.MARKET_LOGIN, params);
			MainService.newTask(this, task);
			Log.w(TAG, "new task!");
		} else {
			Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mark.market.ui.MarketAcitivity#refresh(int, java.lang.Object[])
	 */
	@Override
	public void refresh(int taskID, Object... objects) {
		// TODO Auto-generated method stub
		Log.w(TAG, "loginactivity->refresh!");
		loginprogress.cancel();
		if (objects != null) {
			String result = (String) objects[0];
			JSONObject json = JSON.parseObject(result);
			if (json.get("msg").toString().equals(LOGIN_SUCCESS)) {
				finish();
				User user = new User((String) json.get("uid"),
						(String) json.get("uname"), (String) json.get("upwd"),
						(String) json.get("uemail"));
				LoginSessionUtil.SaveLoginUser(this, user);
			} else if (json.get("msg").toString().equals(LOGIN_FAIL)) {

				Toast.makeText(this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "服务器返回数据异常，请重试...", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Log.w(TAG, "object is null,in loginactivity refresh()!");
			Toast.makeText(this, "网络连接错误，请检查网络", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent upIntent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this)
						.addNextIntentWithParentStack(upIntent)
						.startActivities();
			} else {
				upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
