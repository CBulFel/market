package com.mark.market.ui;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;

import com.mark.market.R;
import com.mark.market.bean.Task;

@SuppressLint({ "NewApi", "InflateParams" })
public class MainActivity extends FragmentActivity implements OnClickListener,
		MarketAcitivity {

	private static final String TAG = "market";
	private Fragment_home fragment_home;
	private Fragment_my fragment_my;
	// 定义tab布局
	private FrameLayout layout_home, layout_my;
	// 定义图像组件
	private ImageView image_home, image_my;
	// 定义 中间发布商品按钮
	private Button tab_plus;
	private SearchView searchView;

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

		initView();
		initData();

		// 默认进入home页面
		clickHomeBtn();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
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
		Log.w(TAG, "refresh UI!->MainActivity");
		switch (taskID) {
		case Task.GET_GOODS: {
			Log.w(TAG, "refresh UI! -> after refresh");
			fragment_home.refreshcomplete();
		}
			break;
		case Task.LOADMORE: {
			Log.w(TAG, "refresh UI! -> after loadmore");
			fragment_home.loadmorecomplete();
		}
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem search = menu.findItem(R.id.menu_search);
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
		// 获取SearchView对象
		searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		if (searchView == null) {
			Log.e("SearchView", "Fail to get Search View.");
			return true;
		}
		// 简单配置searchview
		// searchView.setIconified(true);
		searchView.setSubmitButtonEnabled(true);// 显示搜索提交按钮
		// 利用反射配置searchview
		try {
			initsearchView();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 获取搜索服务管理器
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		// searchable activity的component name，由此系统可通过intent进行唤起
		ComponentName cn = new ComponentName(this, SearchActivity.class);
		// 通过搜索管理器，从searchable
		// activity中获取相关搜索信息，就是searchable的xml设置。如果返回null，表示该activity不存在，或者不是searchable
		SearchableInfo info = searchManager.getSearchableInfo(cn);
		if (info == null) {
			Log.e("SearchableInfo", "Fail to get search info.");
		}
		// 将searchable activity的搜索信息与search view关联
		searchView.setSearchableInfo(info);

		return true;
	}

	/**
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @describe 利用反射对searchview进行自定义，修改背景等
	 */
	@SuppressWarnings("deprecation")
	private void initsearchView() throws NoSuchFieldException,
			IllegalAccessException, IllegalArgumentException {
		// TODO Auto-generated method stub
		Class<?> argClass = searchView.getClass();
		// 配置mSearchPlate背景
		Field ownField = argClass.getDeclaredField("mSearchPlate");
		// setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
		ownField.setAccessible(true);
		View mView = (View) ownField.get(searchView);
		mView.setBackground(getResources().getDrawable(R.drawable.search_palte));
		// 配置搜索按钮
		ownField = argClass.getDeclaredField("mGoButton");
		ownField.setAccessible(true);
		ImageView subView = (ImageView) ownField.get(searchView);
		subView.setImageDrawable(getResources().getDrawable(
				R.drawable.search_btn));

	}

}
