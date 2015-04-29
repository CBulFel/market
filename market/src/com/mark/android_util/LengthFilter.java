/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.android_util;

import java.io.UnsupportedEncodingException;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

/**
 * @author mazhao
 * @describ
 */
public class LengthFilter implements InputFilter {

	private int maxlength = 0;
	private TextView length;
	private boolean isphone;
	/**
	 * @param maxlength
	 */
	public LengthFilter(int maxlength, TextView length) {
		super();
		this.maxlength = maxlength;
		this.length = length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.text.InputFilter#filter(java.lang.CharSequence, int, int,
	 * android.text.Spanned, int, int)
	 */
	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		// TODO Auto-generated method stub
		// 如果长度限制为0,即不限制长度,直接返回原字符
		if (maxlength == 0)
			return source;
		try {

			// 转换成中文字符集的长度
			int destLen = dest.toString().getBytes("GB18030").length;
			int sourceLen = source.toString().getBytes("GB18030").length;
			Log.e("filter", String.valueOf(destLen + sourceLen));
			if (length != null)
				length.setText(String.valueOf(destLen + sourceLen));
			// 如果超过maxlength个字符
			if (destLen + sourceLen >= maxlength) {
				return "";
			}

			// 如果按回退键
			if (source.length() < 1 && (dend - dstart >= 1)) {
				return dest.subSequence(dstart, dend - 1);
			}

			// 其他情况直接返回输入的内容
			return source;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
