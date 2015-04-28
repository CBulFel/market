/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.android_util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author mazhao
 * @describ
 */
public class EditWatcher implements TextWatcher {

	private EditText text;
	private int max = 0;
	private boolean isphone;

	/**
	 * @param text
	 * @param max
	 * @param isphone
	 */
	public EditWatcher(EditText text, int max, boolean isphone) {
		super();
		this.text = text;
		this.max = max;
		this.isphone = isphone;
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

	}

}
