/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.ui;

import java.lang.reflect.Field;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mark.market.R;
import com.mark.market.adapter.GoodsAdapter;
import com.mark.market.bean.Good;

/**
 * @author mazhao
 * @describ 搜索结果fragment
 */
public class Fragment_searchres extends Fragment {
	private static final String TAG = "market->fragment_search_result";
	private ListView list_result;
	private TextView msg;
	private GoodsAdapter madapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.w(TAG, "oncreat");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.w(TAG, "oncreatview");
		View view = inflater.inflate(R.layout.fragment_searchres, null);
		list_result = (ListView) view.findViewById(R.id.search_result);
		msg = (TextView) view.findViewById(R.id.search_res_text);
		madapter = new GoodsAdapter(getActivity());
		list_result.setAdapter(madapter);
		return view;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public void searchcomplete(List<Good> goods) {
		Log.w(TAG, "searchcomplete");

		if (goods == null || goods.isEmpty()) {
			list_result.setVisibility(View.GONE);
			msg.setVisibility(View.VISIBLE);
			return;
		}
		madapter.setgoods(goods);
		madapter.notifyDataSetChanged();

	}

}
