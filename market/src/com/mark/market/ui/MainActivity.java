package com.mark.market.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mark.android_ui.CommentDialog;
import com.mark.android_ui.MyprogressDialog;
import com.mark.android_ui.SmoothProgressDrawable;
import com.mark.android_util.UpdateManager;
import com.mark.market.R;
import com.mark.market.bean.Good;
import com.mark.market.bean.Task;
import com.mark.market.logic.MainService;
import com.mark.market.util.Androidstatus;
import com.mark.market.util.LoginSessionUtil;
import com.mark.market.util.Tools;

public class MainActivity extends FragmentActivity implements OnClickListener,
		MarketActivity {

	private static long exitTime = 0;
	private static long waitTime = 2000;
	private static final String TAG = "market->MainActivity";
	public static int pageNum = 1;
	private boolean success = true;
	private ProgressBar title_progress;
	private MyprogressDialog homeprogress;
	private Fragment_home fragment_home;
	private Fragment_my fragment_my;
	// 定义tab布局
	private FrameLayout layout_home, layout_my;
	// 定义图像组件
	private ImageView image_home, image_my;
	// 定义 中间发布商品按钮
	private Button tab_plus;
	private TextView loadfailed;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 自定义actionbar
		ActionBar actionbar = this.getActionBar();
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayShowTitleEnabled(false);
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayUseLogoEnabled(false);
		actionbar.setDisplayHomeAsUpEnabled(false);
		// actionbar.setIcon(R.drawable.actionbar_backbtn);
		setContentView(R.layout.activity_main);
		// 设置自定义actionbar视图
		ActionBar.LayoutParams Lparams = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		View actionbarLayout = LayoutInflater.from(this).inflate(
				R.layout.title, null);
		actionbar.setDisplayShowCustomEnabled(true);
		actionbar.setCustomView(actionbarLayout, Lparams);
		title_progress = (ProgressBar) findViewById(R.id.title_progress);
		homeprogress = new MyprogressDialog(this);

		// 启动service
		Intent intent = new Intent();
		intent.setClass(this, MainService.class);
		startService(intent);
		// 检查更新
		MainService.newTask(this, new Task(Task.VERSION_UPDATE, null));
		// 初始化文件夹
		Tools.initdir();
		title_progress
				.setIndeterminateDrawable(new SmoothProgressDrawable.Builder(
						this).interpolator(
						new AccelerateDecelerateInterpolator()).build());
		title_progress.setVisibility(View.VISIBLE);
		// 判断是否有网络可用
		if (Androidstatus.isNetworkAvaliable(getApplicationContext()))
			Toast.makeText(getApplicationContext(), "没有可用网络",
					Toast.LENGTH_SHORT).show();
		initView();
		initData();
		// 默认进入home页面
		clickHomeBtn();

	}

	/*
	 * 初始化view
	 */
	private void initView() {
		// 获取各个组件
		layout_home = (FrameLayout) findViewById(R.id.layout_home);
		layout_my = (FrameLayout) findViewById(R.id.layout_my);

		image_home = (ImageView) findViewById(R.id.image_home);
		image_my = (ImageView) findViewById(R.id.image_my);
		tab_plus = (Button) findViewById(R.id.tab_plus);
		loadfailed = (TextView) findViewById(R.id.loadfailed);

	}

	/*
	 * 初始化数据这里主要是添加监听事件
	 */

	private void initData() {

		layout_home.setOnClickListener(this);
		layout_my.setOnClickListener(this);
		tab_plus.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.layout_home:
			clickHomeBtn();
			break;
		case R.id.layout_my:
			clickMyBtn();
			break;
		case R.id.tab_plus:
			clickPlusBtn();
			break;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		if (event.getAction() == MotionEvent.ACTION_DOWN && !success) {
			title_progress.setVisibility(View.VISIBLE);
			homeprogress.show();
			loadfailed.setVisibility(View.GONE);
			success = true;
			if (layout_home.isSelected())
				fragment_home.onRefresh();
			if (layout_my.isSelected())
				fragment_my.init();
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - exitTime >= waitTime) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = currentTime;
			} else {
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
 * 
 */
	private void clickPlusBtn() {
		// TODO Auto-generated method stub
		// 点击我想卖按钮，进入发布商品页
		Intent intent = new Intent(this, GoodsEdit.class);
		startActivity(intent);
	}

	/**
 * 
 */
	private void clickHomeBtn() {
		// TODO Auto-generated method stub
		if (!layout_home.isSelected()) {
			title_progress.setVisibility(View.VISIBLE);
			homeprogress.show();
			// 实例化Fragment_home
			fragment_home = new Fragment_home();
			// 进行Fragment切换
			FragmentTransaction fragmentTransaction = this
					.getSupportFragmentManager().beginTransaction();
			fragmentTransaction.setCustomAnimations(R.animator.slide_in_right,
					R.animator.slide_out_left);
			// 替换Fragment
			fragmentTransaction.replace(R.id.frame_content, fragment_home);
			// 提交更改
			fragmentTransaction.commit();

			layout_home.setSelected(true);
			image_home.setSelected(true);

			layout_my.setSelected(false);
			image_my.setSelected(false);
		}

	}

	private void clickMyBtn() {
		// TODO Auto-generated method stub
		if (!layout_my.isSelected()) {
			title_progress.setVisibility(View.VISIBLE);
			homeprogress.show();
			// 实例化Fragment_home
			fragment_my = new Fragment_my();
			// 进行Fragment切换
			FragmentTransaction fragmentTransaction = this
					.getSupportFragmentManager().beginTransaction();
			fragmentTransaction.setCustomAnimations(R.animator.slide_in_left,
					R.animator.slide_out_right);
			// 替换Fragment
			fragmentTransaction.replace(R.id.frame_content, fragment_my);
			// 提交更改
			fragmentTransaction.commit();
			layout_my.setSelected(true);
			image_my.setSelected(true);
			layout_home.setSelected(false);
			image_home.setSelected(false);
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
		title_progress.setVisibility(View.GONE);
		homeprogress.cancel();

		if (objects[0] == null) {
			Toast.makeText(this, "未得到返回数据", Toast.LENGTH_SHORT).show();
			success = false;
			loadfailed.setVisibility(View.VISIBLE);
			fragment_home.loadfailed();
			return;
		}

		List<Good> goodsjson = new ArrayList<Good>();
		int pagenum = 0;
		int version = 0;
		String url_update = null;
		try {
			String result = (String) objects[0];
			JSONArray jsonarray = null;
			JSONObject json = JSON.parseObject(result);
			if (json.containsKey("goods")) {
				jsonarray = json.getJSONArray("goods");
				goodsjson = JSON.parseArray(jsonarray.toJSONString(),
						Good.class);
			}
			if (json.containsKey("pageNum"))
				pagenum = (int) json.getIntValue("pageNum");
			if (json.containsKey("version"))
				version = json.getIntValue("version");
			if (json.containsKey("url"))
				url_update = json.getString("url");

		} catch (Exception e) { // TODO Auto-generated catch block
			Log.e(TAG, "MainActivity JSON 解析异常");
			Log.e(TAG, e.getMessage());
			Log.e(TAG, e.getCause() + "");
		}

		switch (taskID) {
		case Task.VERSION_UPDATE:
			if (version == 0 || url_update == null)
				return;
			else {
				try {
					Log.w(TAG, "version code:" + version);
					PackageManager manager = getPackageManager();
					PackageInfo info = manager.getPackageInfo(getPackageName(),
							0);
					if (info.versionCode < version) {
						Log.w(TAG, "current version code:" + info.versionCode);

						UpdateManager updatemanager = new UpdateManager(this,
								url_update);
						updatemanager.checkUpdateInfo();
					} else {
						Toast.makeText(this, "已是最新版本~", Toast.LENGTH_SHORT)
								.show();
					}
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case Task.GET_GOODS:
			pageNum = 2;
			fragment_home.refreshcomplete(goodsjson);
			break;
		case Task.LOADMORE:
			if (pagenum == pageNum) {
				// 页号正常
				Log.w(TAG, "pagenum right");
				pageNum++;
				fragment_home.loadmorecomplete(goodsjson);
			} else if (pagenum == (pageNum - 1)) {
				// 最后一页,返回同上一页的页号
				Log.w(TAG, "pagenum last");
				fragment_home.loadmore_last();
			} else {
				// 其他页号,错误页号,直接丢弃,然后重新获取
				Log.w(TAG, "pagenum error,reloadmore");
				fragment_home.onLoadMore();
			}

			break;
		case Task.GET_USERINFO:
			fragment_my.loadsucess(goodsjson);
			break;
		}
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_about:
			Intent intent = new Intent();
			intent.setClass(this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_exit:
			finish();
			break;
		case R.id.menu_update:
			MainService.newTask(this, new Task(Task.VERSION_UPDATE, null));
			break;
		case R.id.menu_ulogin:
			if (LoginSessionUtil.getLoginUser(getApplicationContext()) != null) {
				LoginSessionUtil.removeLoginUser(getApplicationContext());
				Toast.makeText(getApplicationContext(), "已退出登录",
						Toast.LENGTH_SHORT).show();
			} else {
				Intent intentLogin = new Intent();
				intentLogin.setClass(this, LoginActivity.class);
				startActivity(intentLogin);

			}
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem search = menu.findItem(R.id.menu_search);
		MenuItem ulogin = menu.findItem(R.id.menu_ulogin);
		if (LoginSessionUtil.getLoginUser(getApplicationContext()) != null) {
			ulogin.setTitle("退出登录");
		} else {
			ulogin.setTitle("登录");
		}
		search.setOnActionExpandListener(new OnActionExpandListener() {

			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						SearchActivity.class);
				startActivity(intent);
				return false;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		return true;
	}

}
