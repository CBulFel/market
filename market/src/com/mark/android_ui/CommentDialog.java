/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.android_ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mark.market.R;
import com.mark.market.bean.Task;
import com.mark.market.logic.MainService;

/**
 * @author mazhao
 * @describ
 */
public class CommentDialog extends Dialog implements
		android.view.View.OnClickListener {

	private TextView title;
	private EditText content;
	private Button cancel;
	private Button send;
	private String Uid;
	private String Gid;

	/**
	 * @param context
	 */
	public CommentDialog(Context context) {
		super(context,R.style.comment_dialog_theme);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param uid
	 * @param gid
	 */
	public CommentDialog(Context context, String uid, String gid) {
		super(context,R.style.comment_dialog_theme);
		Uid = uid;
		Gid = gid;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_comment);
		title = (TextView) findViewById(R.id.dialog_title);
		content = (EditText) findViewById(R.id.dialog_content);
		cancel = (Button) findViewById(R.id.dialog_cancel);
		send = (Button) findViewById(R.id.dialog_send);
		cancel.setOnClickListener(this);
		send.setOnClickListener(this);
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		super.cancel();
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
		case R.id.dialog_cancel: {
			this.cancel();
		}
			break;
		case R.id.dialog_send: {
			Log.e("market", "dialog_send clicked!");
			
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("uid", Uid);
			params.put("id", Gid);
			params.put("msg", new String(Base64.encodeBase64(content.getText().toString().getBytes())));
			Task task= new Task(Task.COMMENT, params);
			MainService.newTask(null, task);
			this.cancel();
			
		}
			break;
		}
	}

}
