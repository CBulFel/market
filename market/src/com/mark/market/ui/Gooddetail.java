package com.mark.market.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.android_ui.MyImgScroll;
import com.mark.market.R;
import com.mark.market.adapter.CommentsAdapter;
import com.mark.market.bean.GComment;
import com.mark.market.bean.Task;
import com.mark.market.logic.MainService;

@SuppressLint("InflateParams")
public class Gooddetail extends Activity implements MarketAcitivity {

	private ListView list_comments;

	private MyImgScroll detail_img;
	private LinearLayout ovalLayout; // 下方的小圆点
	private List<View> listimgs; // 要滚动的 图片组
	private LinkedList<GComment> comments = new LinkedList<GComment>();
	private ViewHolder holder = new ViewHolder();
	private String Gid;

	// 商品详情页，发布者，用户头像等。。。
	static class ViewHolder {
		public ImageView detail_userimg;
		public TextView detail_username;
		public TextView detail_time;
		public TextView detail_content;
		public TextView detail_place;
		public TextView detail_price;
		public TextView detail_preprice;

	}

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_gooddetail);
		// Actionbar设置
		ActionBar actionbar = getActionBar();
		/*ActionBar.LayoutParams Lparams = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		View actionbarLayout = LayoutInflater.from(this).inflate(
				R.layout.title, null);
		TextView title=(TextView)actionbarLayout.findViewById(R.id.actionbar_title);
		title.setText("商品详情");
		actionbar.setDisplayShowCustomEnabled(true);
		actionbar.setCustomView(actionbarLayout, Lparams);*/
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayHomeAsUpEnabled(true);
		//actionbar.setHomeAsUpIndicator(R.drawable.actionbar_backbtn);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setTitle("商品详情");
		
		
		LayoutInflater inflater = this.getLayoutInflater();
		View view = inflater.inflate(R.layout.gooddetail_header, null);
		
		detail_img = (MyImgScroll) view.findViewById(R.id.detail_imgscroll);
		ovalLayout = (LinearLayout) view.findViewById(R.id.detail_dot);

		holder.detail_userimg = (ImageView) view
				.findViewById(R.id.detail_userimg);
		holder.detail_username = (TextView) view
				.findViewById(R.id.detail_username);
		holder.detail_time = (TextView) view.findViewById(R.id.detail_time);
		holder.detail_content = (TextView) view
				.findViewById(R.id.detail_content);
		holder.detail_place = (TextView) view.findViewById(R.id.detail_palce);
		holder.detail_price = (TextView) view.findViewById(R.id.detail_price);
		holder.detail_preprice = (TextView) view
				.findViewById(R.id.detail_preprice);
		list_comments = (ListView) findViewById(R.id.detail_comments);
		// 获取intent中的Gid
		Intent intent = getIntent();
		Gid = intent.getStringExtra("Gid");
		// 新开任务通过商品ID获取商品详情
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Gid", Gid);
		Task task = new Task(Task.GET_DETAIL_BY_GID, params);
		MainService.newTask(this, task);
		init();

		detail_img.start(this, listimgs, 0, ovalLayout,
				R.layout.ad_bottom_item, R.id.ad_item_v,
				R.drawable.dot_focused, R.drawable.dot_normal);
		list_comments.addHeaderView(view);
	}

	private void init() {
		listimgs = new ArrayList<View>();
		int[] imageResId = new int[] { R.drawable.scrollimg_img1,
				R.drawable.scrollimg_img2, R.drawable.scrollimg_img3,
				R.drawable.scrollimg_img4, R.drawable.scrollimg_img5 };
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					AlertDialog alert = new AlertDialog.Builder(Gooddetail.this)
							.create();
					alert.show();
					alert.setTitle("查看");
					alert.getWindow().setLayout(v.getWidth(), v.getHeight());
					alert.getWindow().setGravity(Gravity.CENTER);
					alert.getWindow().setBackgroundDrawable(
							((ImageView) v).getDrawable());
				}
			});
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_INSIDE);
			listimgs.add(imageView);
		}
		GComment comment = new GComment();
		for (int i = 0; i < 10; i++) {
			comments.add(comment);
		}
		CommentsAdapter adapter = new CommentsAdapter(this, comments);
		list_comments.setAdapter(adapter);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mark.market.ui.MarketAcitivity#refresh(int, java.lang.Object[])
	 */
	@Override
	public void refresh(int taskID, Object... objects) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent upIntent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this)
						.addNextIntentWithParentStack(upIntent)
						.startActivities();
			} else {
				upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
