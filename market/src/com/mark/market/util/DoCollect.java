/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.mark.market.bean.Good;
import com.mark.market.bean.Task;
import com.mark.market.bean.User;
import com.mark.market.logic.MainService;
import com.mark.market.ui.LoginActivity;

/**
 * @author mazhao
 * @describ
 */
public class DoCollect implements OnCheckedChangeListener {

	private Good good;
	private Context context;

	public DoCollect(Context context, Good good) {
		super();
		this.good = good;
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged
	 * (android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		User user = null;
		if ((user = LoginSessionUtil.getLoginUser(context)) == null) {
			Intent intent = new Intent();
			intent.setClass(context, LoginActivity.class);
			context.startActivity(intent);
			return;

		}
		if (isChecked) {
			Toast.makeText(context, "收藏", Toast.LENGTH_SHORT).show();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pre", "g");
			params.put("uid", user.getUid());
			params.put("id", good.getGid());
			params.put("collect", "1");
			Task task = new Task(Task.COLLECT, params);
			MainService.newTask(null, task);

		} else {
			Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pre", "g");
			params.put("uid", user.getUid());
			params.put("id", good.getGid());
			params.put("unCollect", "1");
			Task task = new Task(Task.COLLECT, params);
			MainService.newTask(null, task);
		}

	}

}
