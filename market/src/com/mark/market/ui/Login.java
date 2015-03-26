package com.mark.market.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mark.market.R;

public class Login extends LinearLayout {

	private EditText login_name = null;
	private EditText login_pwd = null;
	private TextView login_reg = null;
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
		login_reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				login_reg.setTextColor(Color.GRAY);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				getContext().startActivity(intent);
			}
		});

	}

	public String getLogin_name() {
		return login_name.getText().toString();
	}

	public String getLogin_pwd() {
		return login_pwd.getText().toString();
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
