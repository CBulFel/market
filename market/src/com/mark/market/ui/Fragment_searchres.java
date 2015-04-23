/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.ui;

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
 * @describ 
 */
public class Fragment_searchres extends Fragment {
	private static final String TAG="market->fragment_search_result";
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
		View view=inflater.inflate(R.layout.fragment_searchres, null);
		list_result=(ListView)view.findViewById(R.id.search_result);
		msg=(TextView)view.findViewById(R.id.search_res_text);
		return view;
	}
	
	public void searchcomplete(List<Good> goods){
		Log.w(TAG, "searchcomplete");
		if(getActivity()!=null){
		madapter=new GoodsAdapter(getActivity(),goods);
		list_result.setAdapter(madapter);
		}else {
			Log.w(TAG, "get context error,null");
			msg.setText("搜索页面出现错误");
			msg.setVisibility(View.VISIBLE);
			return;
		}
		if(goods.isEmpty()){
			msg.setVisibility(View.VISIBLE);
		}
	}
	
}
