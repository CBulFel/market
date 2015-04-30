package com.mark.market.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mark.android_ui.MyprogressDialog;
import com.mark.market.R;
import com.mark.market.bean.Good;
import com.mark.market.bean.Task;
import com.mark.market.logic.MainService;
import com.mark.market.util.SearchSuggestionsProvider;

public class SearchActivity extends FragmentActivity implements MarketActivity {

	private static String TAG = "market searchactivity";
	private SearchView searchView;
	private String queryString = null;
	private Fragment_searchsug fragment_suggest;
	private Fragment_searchres fragment_result;
	private MyprogressDialog progress_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		// getActionBar().hide();
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setTitle("搜索");
		gosugfrg();
		progress_search = new MyprogressDialog(this);
		Intent intent = getIntent();
		// 如果是通过ACTION_SEARCH来调用，即如果通过搜索调用
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// 获取搜索内容
			queryString = intent.getStringExtra(SearchManager.QUERY);

		}
		doSearchQuery();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			/*Intent upIntent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this)
						.addNextIntentWithParentStack(upIntent)
						.startActivities();
			} else {
				upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, upIntent);
			}*/
			finish();
			return true;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.search, menu);
		// 使searchview默认展开
		MenuItem search = menu.findItem(R.id.menu_search);
		search.expandActionView();

		search.setOnActionExpandListener(new OnActionExpandListener() {

			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchActivity.this,
						MainActivity.class);
				startActivity(intent);
				return false;
			}
		});
		// 获取SearchView对象
		searchView = (SearchView) menu.findItem(R.id.menu_search)
				.getActionView();
		if (queryString != null)
			searchView.setQuery(queryString, false);
		searchView.setSubmitButtonEnabled(true);// 显示搜索提交按钮
		// 找到搜索按钮对应的LinearLayout
		int searchPlateId = searchView.getContext().getResources()
				.getIdentifier("android:id/submit_area", null, null);
		LinearLayout searchPlate = (LinearLayout) searchView
				.findViewById(searchPlateId);
		// 拿到搜索图标的imageview,这样就可以修改图片了
		((ImageView) searchPlate.getChildAt(0))
				.setImageResource(R.drawable.search_btn);
		searchView
				.setOnQueryTextFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						Log.w(TAG, "text onfocuschange:" + hasFocus);
						if (hasFocus)
							gosugfrg();
					}
				});
		/*
		 * // 利用反射配置searchview
		 * 
		 * try { initsearchView(); } catch (NoSuchFieldException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (IllegalAccessException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IllegalArgumentException e) { //
		 * TODOAuto-generated catch block e.printStackTrace(); }
		 */
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				if (query == null)
					return true;
				else {
					searchView.clearFocus();
					queryString = query;
					doSearchQuery();
				}
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		// 获取搜索服务管理器
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		// searchable activity的component name，由此系统可通过intent进行唤起
		ComponentName cn = getComponentName();

		// 通过搜索管理器，从searchable
		// activity中获取相关搜索信息，就是searchable的xml设置。如果返回null，表示该activity不存在，或者不是searchable
		SearchableInfo info = searchManager.getSearchableInfo(cn);
		if (info == null) {
			Log.e("SearchableInfo", "Fail to get search info.");
		}
		// 将searchable activity的搜索信息与search view关联
		searchView.setSearchableInfo(info);
		searchView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		searchView.setGravity(Gravity.CENTER);
		searchView.clearFocus();
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.w(TAG, "onDestory()");
		/*if (fragment_result.isResumed()) {

			this.getSupportFragmentManager().beginTransaction()
					.remove(fragment_result).commitAllowingStateLoss();
		}*/
		super.onDestroy();
	}

	private void gosugfrg() {

		fragment_suggest = new Fragment_searchsug();
		FragmentTransaction transaction = this.getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.search_content, fragment_suggest);
		transaction.commitAllowingStateLoss();

	}

	private void goresultfrg() {

		fragment_result = new Fragment_searchres();

		FragmentTransaction transaction = this.getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.search_content, fragment_result);
		transaction.commitAllowingStateLoss();

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
		ownField = argClass.getDeclaredField("mSearchSrcTextView");
		ownField.setAccessible(true);
		AutoCompleteTextView searchtext = (AutoCompleteTextView) ownField
				.get(searchView);
		searchtext.setText(queryString);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		// 如果是通过ACTION_SEARCH来调用，即如果通过搜索调用
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// 获取搜索内容
			queryString = intent.getStringExtra(SearchManager.QUERY);

		}

		doSearchQuery();

	}

	/**
	 * @param intent
	 */
	private void doSearchQuery() {
		// TODO Auto-generated method stub

		if (queryString == null)
			return;
		String regex = "[?;,:.~!@#$%^&*<>]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(queryString);
		if (matcher.find()) {
			Toast.makeText(getApplicationContext(), "含有非法字符!",
					Toast.LENGTH_SHORT).show();
			return;

		}
		progress_search.show();
		goresultfrg();
		// 保存搜索记录
		SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
				SearchSuggestionsProvider.AUTHORITY,
				SearchSuggestionsProvider.MODE);
		suggestions.saveRecentQuery(queryString, null);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchName", queryString);
		Task task = new Task(Task.GOODS_SEARCH, params);
		MainService.newTask(this, task);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mark.market.ui.MarketAcitivity#refresh(int, java.lang.Object[])
	 */
	@Override
	public void refresh(int taskID, Object... objects) {
		// TODO Auto-generated method stub
		progress_search.cancel();

		if (objects[0] == null) {
			Toast.makeText(this, "未得到返回数据", Toast.LENGTH_SHORT).show();
			return;
		}
		List<Good> goodsjson = new ArrayList<Good>();

		try {
			String res = (String) objects[0];
			JSONObject json = JSON.parseObject(res);
			Log.w(TAG, json.toJSONString());
			JSONArray jsonarray = json.getJSONArray("list");
			Log.w(TAG, jsonarray.toJSONString());
			goodsjson = JSON.parseArray(jsonarray.toJSONString(), Good.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "JSON 解析错误");
			Log.e(TAG, "msg:" + e.getMessage());
			Log.e(TAG, "caused:" + e.getCause());
		}

		fragment_result.searchcomplete(goodsjson);
	}
}
