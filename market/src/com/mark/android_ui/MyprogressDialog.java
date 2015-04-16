/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.android_ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mark.market.R;

/**
 * @author mazhao
 * @describ
 */
public class MyprogressDialog extends Dialog {

	private String msg = null;
	private TextView msgview = null;
	private ImageView imgview = null;
	private AnimationDrawable animation = null;
	private static final String TAG = "progress dialog";

	/**
	 * @param context
	 */
	public MyprogressDialog(Context context) {
		super(context, R.style.CustomProgressDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.w(TAG, "progress dialog ----> onCreat()!");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadingdialog);
		msgview = (TextView) findViewById(R.id.dialog_msg);
		imgview = (ImageView) findViewById(R.id.dialog_img);
		animation = (AnimationDrawable) imgview.getDrawable();
		if (msg != null) {
			msgview.setText(msg);
		}
		animation.start();
	}

	public void setmsg(String msg1) {
		Log.w(TAG, "progress dialog---->setText()");
		msg = msg1;
	}

}
