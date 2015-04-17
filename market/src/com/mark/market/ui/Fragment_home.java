package com.mark.market.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mark.android_ui.MyImgScroll;
import com.mark.android_ui.MyListView;
import com.mark.android_ui.MyprogressDialog;
import com.mark.android_ui.MyListView.MyListViewListener;
import com.mark.market.R;
import com.mark.market.adapter.GoodsAdapter;
import com.mark.market.bean.Good;
import com.mark.market.bean.Task;
import com.mark.market.logic.MainService;

@SuppressLint("InflateParams")
public class Fragment_home extends Fragment implements MyListViewListener {
	private static final String TAG = "market";
	// listview控件
	public static MyListView marketListView;
	private GoodsAdapter mAdapter;
	private List<Good> goods = new ArrayList<Good>();
	private MyprogressDialog progressdialog=null;
	// ImgScroll控件
	MyImgScroll myPager; // 图片滚动控件
	LinearLayout ovalLayout; // 下方的小圆点
	private List<View> listImgs; // 要滚动的 图片组

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_home, null);
		marketListView = (MyListView) view.findViewById(R.id.marketlist);

		View mview = inflater.inflate(R.layout.scrollimg, null);
		myPager = (MyImgScroll) mview.findViewById(R.id.martket_home_imgscroll);
		ovalLayout = (LinearLayout) mview.findViewById(R.id.vb);
		progressdialog=new MyprogressDialog(getActivity());
		progressdialog.show();
		// 初始化组件
		init();
/*		// 设置imgScroll开始滚动
		myPager.start(getActivity(), listImgs, 4000, ovalLayout,
				R.layout.ad_bottom_item, R.id.ad_item_v,
				R.drawable.dot_focused, R.drawable.dot_normal);*/
		marketListView.addHeaderView(mview);
		return view;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	private void init() {

		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("index", "20");
		Task task = new Task(Task.GET_GOODS, params);
		MainService.newTask(getActivity(), task);*/
		/*
		 * imgScroll初始化
		 */

		listImgs = new ArrayList<View>();
		int[] imageResId = new int[] { R.drawable.banner1,
				R.drawable.banner2, R.drawable.banner3,
				R.drawable.banner4 };
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// 点击监听事件
					Toast.makeText(getActivity(),
							"点击了:" + myPager.getCurIndex(), Toast.LENGTH_SHORT)
							.show();
				}
			});
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			listImgs.add(imageView);
		}

		/*
		 * Listview初始化
		 */
		/*for (int i = 0; i <= 12; i++)
			goods.add(new Good());
		mAdapter = new GoodsAdapter(getActivity(), goods);
		marketListView.setPullLoadEnable(true);
		marketListView.setMyListViewListener(this);
		marketListView.setAdapter(mAdapter);*/
		
	}

	@Override
	public void onRefresh() {
		// 下拉刷新操作
		//progressdialog.show();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("index", "20");
		Task task = new Task(Task.GET_GOODS, params);
		MainService.newTask(getActivity(), task);

		
	}

	@Override
	public void onLoadMore() {
		
		// 加载更多操作
		//progressdialog.show();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("index", "20");
		Task task = new Task(Task.LOADMORE, params);
		MainService.newTask(getActivity(), task);

	}

	public void refreshcomplete(List<Good> goods_new) {
		
		// 刷新完成后的操作
		//开始图片滚动
		progressdialog.cancel();
		myPager.start(getActivity(), listImgs, 4000, ovalLayout,
				R.layout.ad_bottom_item, R.id.ad_item_v,
				R.drawable.dot_focused, R.drawable.dot_normal);
		

		goods=goods_new;
		marketListView.setAdapter(new GoodsAdapter(getActivity(), goods));
		marketListView.stopRefresh();
		marketListView.stopLoadMore();
		marketListView.setRefreshTime("刚刚");
		Log.w(TAG, "refresh sucess!");
	}

	public void loadmorecomplete(List<Good> goods_more) {
		// 加载更多完成后的操作
		progressdialog.cancel();
		mAdapter.addgoods(goods_more);
		marketListView.setAdapter(mAdapter);
		marketListView.stopRefresh();
		marketListView.stopLoadMore();
		Log.w(TAG, "loadmore success!");
	}

}
