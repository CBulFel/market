package com.mark.market.ui;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mark.android_ui.Login;
import com.mark.android_ui.Login.LoginListener;
import com.mark.market.R;
import com.mark.market.bean.Task;
import com.mark.market.bean.User;
import com.mark.market.logic.MainService;
import com.mark.market.util.LoginSessionUtil;

public class LoginActivity extends Activity implements MarketAcitivity , LoginListener {

	private Login login;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		login = (Login) findViewById(R.id.login_form);
		login.setLoginListener(this);
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
		if (null != LoginSessionUtil.getLoginUser(this)) {
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}

		// 开启任务从本地数据库获取登录授权用户信息
		/*Task task = new Task(Task.GET_USERINFO, null);
		MainService.newTask(task);
		MainService.addActivty(LoginActivity.this);*/
		
		User user=new User(null, login.getUname(), login.getUpwd());
		//新开任务：登录操作
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("User", user);
		Task task =new Task(Task.MARKET_LOGIN,params);
		MainService.newTask(this,task);
		
		
	}

	/* (non-Javadoc)
	 * @see com.mark.market.ui.MarketAcitivity#refresh(int, java.lang.Object[])
	 */
	@Override
	public void refresh(int taskID, Object... objects) {
		// TODO Auto-generated method stub
		
	}

}
