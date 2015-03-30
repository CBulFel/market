package com.mark.android_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mark.android_util.MD5Util;
import com.mark.market.R;
import com.mark.market.bean.User;

public class Login extends LinearLayout implements OnClickListener {

	private EditText login_name = null;
	private EditText login_pwd = null;
	private TextView login_reg = null;
	private Button login_btn = null;
	private LoginListener mloginlistener;
	private String url = "http://online.cumt.edu.cn:8080/2/registration.jsp";

	@SuppressLint("NewApi")
	public Login(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public Login(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO
	}

	public Login(Context context) {
		super(context);
		// TODO
	}

	@Override
	protected void onFinishInflate() {
		// TODO
		super.onFinishInflate();
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.login, this);
		login_name = (EditText) view.findViewById(R.id.login_name);
		login_pwd = (EditText) view.findViewById(R.id.login_passwd);
		login_reg = (TextView) view.findViewById(R.id.login_register);
		login_btn = (Button) view.findViewById(R.id.login_btn);
		login_btn.setOnClickListener(this);
		login_reg.setOnClickListener(this);

	}
	
	public static User getUser(){
		User user=new User();
		return user;
		
		
	}

	public String getUname() {
		return login_name.getText().toString();
	}

	public String getUpwd() {
		return MD5Util.MD5(login_pwd.getText().toString());
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setLoginListener(LoginListener l) {
		this.mloginlistener = l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_register:
			login_reg.setTextColor(Color.GRAY);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			getContext().startActivity(intent);
			break;
		case R.id.login_btn:
			mloginlistener.loginauth();
			break;
		default:
			break;
		}

	}

	/*
	 * @Describe 提供接口，进行登录认证，以及认证过后，存入数据库等操作
	 */
	public interface LoginListener {
		// @Describe 登录认证
		public void loginauth();

	}

}
