package com.mark.market.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mark.market.R;
import com.mark.market.adapter.GoodsAdapter;
import com.mark.market.bean.Good;
import com.mark.market.bean.Task;
import com.mark.market.logic.MainService;
import com.mark.market.util.SearchSuggestionsProvider;

public class SearchActivity extends Activity implements MarketAcitivity {

	private static String TAG = "market searchactivity";
	private ListView result;
	private SearchView searchView;
	private String queryString;
	private GoodsAdapter madapter;
	private List<Good> goods = new ArrayList<Good>();

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
		result = (ListView) findViewById(R.id.search_result);
		Intent intent = getIntent();
		// 如果是通过ACTION_SEARCH来调用，即如果通过搜索调用
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// 获取搜索内容
			queryString = intent.getStringExtra(SearchManager.QUERY);

		}
		doSearchQuery();
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
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				queryString = query;
				doSearchQuery();
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
		View foot_view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.seatchview_footer,null);
		searchView.addView(foot_view, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
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
		// 保存搜索记录

		SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
				SearchSuggestionsProvider.AUTHORITY,
				SearchSuggestionsProvider.MODE);
		suggestions.saveRecentQuery(queryString, null);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchtext", queryString);
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
		if (objects[0] == null) {
			Toast.makeText(this, "未得到返回数据", Toast.LENGTH_SHORT).show();
			return;
		}
		String res = (String) objects[0];
		JSONObject json = JSON.parseObject(res);
		Log.w(TAG, json.toJSONString());
		JSONArray jsonarray = json.getJSONArray("goods");
		Log.w(TAG, jsonarray.toJSONString());
		List<Good> goodsjson = JSON.parseArray(jsonarray.toJSONString(),
				Good.class);
		goods.addAll(goodsjson);
		madapter = new GoodsAdapter(this, goods);
		result.setAdapter(madapter);

	}
}
