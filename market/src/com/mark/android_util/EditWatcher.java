/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.android_util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.mark.market.R;

/**
 * @author mazhao
 * @describ 输入的文本是否规范的监视器
 */
public class EditWatcher implements TextWatcher {

	public static final int INPUT_NORM = 1;
	public static final int INPUT_ILLEGAL = 2;
	private static final String TAG = "mark->editwatcher";
	private EditText text;
	private int max = 0;
	private boolean isphone = false;
	private boolean isfloat = false;
	private boolean nonumeric = false;

	
	
	
	/**
	 * @param text
	 * @param max
	 * @param nonumeric
	 */
	public EditWatcher(EditText text, int max, boolean nonumeric) {
		super();
		this.text = text;
		this.max = max;
		this.nonumeric = nonumeric;
	}

	/**
	 * @param text 要观察的文本框
	 * @param max
	 * @param isphone 是否是手机号码
	 * @param isfloat 是否是浮点数,即价格0-20000
	 * @param nonumeric 不含有特殊字符
	 */
	public EditWatcher(EditText text, int max, boolean isphone,
			boolean isfloat, boolean nonumeric) {
		super();
		this.text = text;
		this.max = max;
		this.isphone = isphone;
		this.isfloat = isfloat;
		this.nonumeric = nonumeric;
	}

	/**
	 * @param text
	 *            要观察的文本框
	 * @param isphone
	 *            是否是手机号码
	 * @param isfloat
	 *            是否是浮点数,即价格0-20000
	 */
	public EditWatcher(EditText text, boolean isphone, boolean isfloat) {
		super();
		this.text = text;
		this.isphone = isphone;
		this.isfloat = isfloat;
	}

	/**
	 * @param text
	 *            要监视的输入框
	 * @param max
	 *            限制的最大输入长度
	 */
	public EditWatcher(EditText text, int max) {
		super();
		this.text = text;
		this.max = max;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence,
	 * int, int, int)
	 */
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int,
	 * int, int)
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

		if (max != 0 && s.length() > max) {
			text.setError("超过长度限制");
			text.setBackgroundResource(R.drawable.edittext_error);
			text.setId(INPUT_ILLEGAL);
			Log.w(TAG, "超过长度限制");
			return;
		} else if (isfloat && s.length() > 0) {
			Float f;
			try {
				f = Float.parseFloat(s.toString());
			} catch (Exception e) {
				// TODO: handle exception
				Log.w(TAG, "folat 解析出错");
				text.setError("输入格式不规范");
				text.setBackgroundResource(R.drawable.edittext_error);
				text.setId(INPUT_ILLEGAL);
				return;
			}
			if (f < 0 || f > 20000) {
				Log.w(TAG, "float 超过数值范围");
				text.setBackgroundResource(R.drawable.edittext_error);
				text.setError("超过数值范围");
				text.setId(INPUT_ILLEGAL);
				return;
			}
		} else if (isphone && s.length() > 0) {
			char sperator = ' ';
			if (s.length() != 13 || s.charAt(8) != sperator
					|| ((s.charAt(3) != sperator) && (s.charAt(4) != sperator))) {
				text.setBackgroundResource(R.drawable.edittext_error);
				text.setError("电话或手机号不符合格式");
				text.setId(INPUT_ILLEGAL);
				return;
			}
		} else if (nonumeric && s.length() > 0) {
			String regex = "[?;,:.~!@#$%^&*<>]";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(s.toString());
			if (matcher.find()) {
				text.setError("不能含有特殊字符");
				text.setId(INPUT_ILLEGAL);
			}
		}

		text.setBackgroundResource(R.drawable.edittext);
		text.setId(INPUT_NORM);
	}
}
