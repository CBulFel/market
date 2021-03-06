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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mark.android_ui.CommentDialog;
import com.mark.android_ui.MyImgScroll;
import com.mark.android_util.AndroidShare;
import com.mark.market.R;
import com.mark.market.adapter.CommentsAdapter;
import com.mark.market.bean.GComment;
import com.mark.market.bean.Good;
import com.mark.market.bean.Task;
import com.mark.market.bean.User;
import com.mark.market.logic.MainService;
import com.mark.market.util.DoCollect;
import com.mark.market.util.LoginSessionUtil;
import com.mark.market.util.Tools;

@SuppressLint("InflateParams")
public class Gooddetail extends Activity implements MarketActivity,
		OnClickListener {
	private static String TAG = "market gooddetail";
	private ListView list_comments;
	private Good good = null;
	private MyImgScroll detail_img;
	private LinearLayout ovalLayout; // 下方的小圆点
	private List<View> listimgs; // 要滚动的 图片组
	private View headview;
	private ArrayList<GComment> comments = new ArrayList<GComment>();
	private ArrayList<String> unamelist = new ArrayList<String>();
	private ViewHolder holder = new ViewHolder();
	private CommentsAdapter comment_adapter;
	private CommentDialog commentdialog;
	private ActionBar actionbar;

	// 商品详情页，发布者，用户头像等。。。
	static class ViewHolder {
		public ImageView detail_userimg;
		public TextView detail_username;
		public TextView detail_time;
		public TextView detail_content;
		public TextView detail_place;
		public TextView detail_price;
		public TextView detail_preprice;
		public TextView detail_header_phone;
		public CheckBox detail_like;
		public LinearLayout detail_comment;
		public LinearLayout detail_share;
		public TextView detail_comment_text;
		public TextView detail_phone;

	}

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_gooddetail);
		// Actionbar设置
		actionbar = getActionBar();
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayHomeAsUpEnabled(true);
		// actionbar.setHomeAsUpIndicator(R.drawable.actionbar_backbtn);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setTitle("商品详情");

		LayoutInflater inflater = this.getLayoutInflater();
		headview = inflater.inflate(R.layout.gooddetail_header, null);

		detail_img = (MyImgScroll) headview.findViewById(R.id.detail_imgscroll);
		ovalLayout = (LinearLayout) headview.findViewById(R.id.detail_dot);

		holder.detail_userimg = (ImageView) headview
				.findViewById(R.id.detail_userimg);
		holder.detail_username = (TextView) headview
				.findViewById(R.id.detail_username);
		holder.detail_time = (TextView) headview.findViewById(R.id.detail_time);
		holder.detail_content = (TextView) headview
				.findViewById(R.id.detail_content);
		holder.detail_place = (TextView) headview
				.findViewById(R.id.detail_palce);
		holder.detail_price = (TextView) headview
				.findViewById(R.id.detail_price);
		holder.detail_preprice = (TextView) headview
				.findViewById(R.id.detail_preprice);
		holder.detail_header_phone = (TextView) headview
				.findViewById(R.id.detail_header_phone);
		holder.detail_comment_text = (TextView) findViewById(R.id.detail_comment);
		holder.detail_like = (CheckBox) findViewById(R.id.detail_like);
		holder.detail_comment = (LinearLayout) findViewById(R.id.comment);
		holder.detail_share = (LinearLayout) findViewById(R.id.share);
		holder.detail_phone = (TextView) findViewById(R.id.detail_phone);
		list_comments = (ListView) findViewById(R.id.detail_comments);
		// 获取intent中的Gid
		Intent intent = getIntent();
		good = (Good) intent.getSerializableExtra("good");
		init();
		// 新开任务通过商品ID获取商品详情
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pre", "g");
		params.put("id", good.getGid());
		Task task = new Task(Task.GET_DETAIL_BY_GID, params);
		MainService.newTask(this, task);

	}

	private void init() {
		if (good == null) {
			return;
		}
		try {
			holder.detail_username.setText(good.getUser().getUname());
			holder.detail_time.setText(Tools.formattime(good.getGtime()));
			holder.detail_content.setText(good.getGdescription());
			holder.detail_place.setText(good.getGplace());
			holder.detail_price.setText(good.getGprice().toString());
			holder.detail_preprice.setText(good.getGprePrice().toString());
			holder.detail_like.setText(good.getGcollectNum().toString());
			holder.detail_header_phone.setText(good.getGphone());
			holder.detail_comment_text
					.setText(good.getGcommentNum().toString());
		} catch (Exception e) {
			Log.e(TAG, "返回数据异常");
			Log.e(TAG, "catched:" + e.getMessage());
			Log.e(TAG, "cause:" + e.getCause());
		}

		holder.detail_like.setChecked(good.getGvalid());
		holder.detail_header_phone.setOnClickListener(this);
		holder.detail_phone.setOnClickListener(this);
		holder.detail_comment.setOnClickListener(this);
		holder.detail_share.setOnClickListener(this);
		holder.detail_like.setOnCheckedChangeListener(new DoCollect(
				Gooddetail.this, good));

		listimgs = new ArrayList<View>();
		String[] imageResId = new String[] { good.getGimg1(), good.getGimg2(),
				good.getGimg3(), good.getGimg4(), good.getGimg5(),
				good.getGimg6() };
		for (int i = 0; i < imageResId.length; i++) {
			if (imageResId[i] == null || imageResId[i].isEmpty()) {
				continue;
			}
			ImageView imageView = new ImageView(this);
			Tools.loadBitmap(MainService.MARKETHOST + imageResId[i], imageView,
					0, 0);
			Log.w(TAG, "img resID:" + imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_INSIDE);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					AlertDialog alert = new AlertDialog.Builder(Gooddetail.this)
							.create();
					alert.show();
					alert.setTitle("查看");
					alert.getWindow().setLayout(v.getWidth(), v.getHeight());
					alert.getWindow().setGravity(Gravity.CENTER);
					alert.getWindow().setBackgroundDrawable(
							((ImageView) v).getDrawable());
					/*
					 * ((ImageView) v).setScaleType(ScaleType.CENTER_INSIDE);
					 * alert.addContentView((ImageView) v, new LayoutParams
					 * (LayoutParams.WRAP_CONTENT,LayoutParams .WRAP_CONTENT));
					 */

				}
			});
			listimgs.add(imageView);
		}
		detail_img.start(this, listimgs, 0, ovalLayout,
				R.layout.ad_bottom_item, R.id.ad_item_v,
				R.drawable.dot_focused, R.drawable.dot_normal);
		// refreshcomments();

	}

	public void refreshcomments() {

		Log.w(TAG, "refreshcomments()-comments:" + comments.size());
		comment_adapter = new CommentsAdapter(this, comments, unamelist);
		list_comments.setAdapter(comment_adapter);
		list_comments.addHeaderView(headview);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			/*
			 * Intent upIntent = NavUtils.getParentActivityIntent(this); if
			 * (NavUtils.shouldUpRecreateTask(this, upIntent)) {
			 * TaskStackBuilder.create(this)
			 * .addNextIntentWithParentStack(upIntent) .startActivities(); }
			 * else { upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 * NavUtils.navigateUpTo(this, upIntent); } return true;
			 */
			break;
		}

		return super.onOptionsItemSelected(item);
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
		case R.id.detail_header_phone:

			Intent intent_dial = new Intent(Intent.ACTION_CALL,
					Uri.parse("tel:" + good.getGphone()));
			startActivity(intent_dial);
			break;
		case R.id.detail_phone:
			holder.detail_header_phone.performClick();
			break;
		case R.id.comment:
			Log.w(TAG, "gooddetail->comment");
			User user = new User();
			if ((user = LoginSessionUtil.getLoginUser(this)) != null) {
				commentdialog = new CommentDialog(this, user.getUid(),
						good.getGid());
				commentdialog.show();
			} else {
				Intent intent = new Intent();
				intent.setClass(this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.share:
			Log.w(TAG, "gooddetail->share");
			String sharemsg = getResources().getString(
					R.string.share_message_header)
					+ MainService.goodUrlhead + good.getGid();
			AndroidShare share = new AndroidShare(this, sharemsg,
					good.getGimg1());
			share.show();
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mark.market.ui.MarketAcitivity#refresh(int, java.lang.Object[])
	 */
	@Override
	public void refresh(int taskID, Object... objects) {
		// TODO Auto-generated method stub
		Log.w(TAG, "refresh get objects[0]:" + (String) objects[0]);
		if (objects[0] == null) {
			Toast.makeText(getApplicationContext(), "未收到数据", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		switch (taskID) {
		case Task.GET_DETAIL_BY_GID:

			try {
				String result = (String) objects[0];
				JSONObject js = JSON.parseObject(result);
				JSONArray json_unamel = js.getJSONArray("userList");
				unamelist = (ArrayList<String>) JSON.parseArray(
						json_unamel.toJSONString(), String.class);
				Log.w(TAG, json_unamel.toJSONString());
				JSONArray json_comments = js.getJSONArray("gCommentList");
				comments = (ArrayList<GComment>) JSON.parseArray(
						json_comments.toJSONString(), GComment.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.w(TAG, "JSON数据解析异常");
				Log.w(TAG, "catched:" + e.getMessage());
				Log.w(TAG, "cause:" + e.getCause());
			}

			refreshcomments();
			break;
		case Task.COMMENT:
			refreshcomments();
		}
	}
}
