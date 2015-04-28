package com.mark.market.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.android_ui.CircularImage;
import com.mark.market.R;
import com.mark.market.adapter.MyGoodsAdapter;
import com.mark.market.bean.Good;
import com.mark.market.bean.Task;
import com.mark.market.bean.User;
import com.mark.market.logic.MainService;
import com.mark.market.util.LoginSessionUtil;

public class Fragment_my extends Fragment implements OnClickListener,
		OnItemClickListener {

	private CircularImage headerimg;
	private TextView uname;
	private TextView my_null;
	private GridView mygoods;
	private List<Good> goods;
	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_my, null);
		headerimg = (CircularImage) view.findViewById(R.id.my_headimg);
		headerimg.setImageResource(R.drawable.header_img_default);
		uname = (TextView) view.findViewById(R.id.my_uname);
		my_null = (TextView) view.findViewById(R.id.my_null);
		mygoods = (GridView) view.findViewById(R.id.my_goods);
		if ((user = LoginSessionUtil.getLoginUser(getActivity())) != null) {
			init();
		} else {
			Intent intent = new Intent();
			intent.setClass(getActivity(), LoginActivity.class);
			startActivity(intent);
		}

		return view;
	}

	public void init() {
		uname.setText(user.getUname());
		mygoods.setVisibility(View.GONE);
		headerimg.setOnClickListener(this);
		mygoods.setOnItemClickListener(this);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", user.getUid());
		params.put("host", user.getUname());
		Task task = new Task(Task.GET_USERINFO, params);
		MainService.newTask(getActivity(), task);

	}

	public void loadsucess(List<Good> getedgoods) {
		if (getedgoods.isEmpty()) {
			my_null.setVisibility(View.VISIBLE);
		} else {
			my_null.setVisibility(View.GONE);
			goods = getedgoods;
			mygoods.setVisibility(View.VISIBLE);
			mygoods.setAdapter(new MyGoodsAdapter(getActivity(), goods));
		}
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
		case R.id.my_headimg:
			Toast.makeText(getActivity(), "暂不支持资料编辑功能,敬请期待V2.0~",
					Toast.LENGTH_SHORT).show();

			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.w("fragment_my", "clicked->" + position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("good", goods.get(position));
		Intent intent = new Intent();
		intent.setClass(getActivity(), Gooddetail.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
