package com.mark.market.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class Fragment_my extends Fragment {

	private CircularImage headerimg;
	private TextView uname;
	private GridView mygoods;
	private List<Good> goods=new ArrayList<Good>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_my, null);
		headerimg=(CircularImage)view.findViewById(R.id.my_headimg);
		uname=(TextView)view.findViewById(R.id.my_uname);
		mygoods=(GridView)view.findViewById(R.id.my_goods);
		init();
		return view;
	}

	private void init(){
		for (int i = 0; i <= 12; i++)
			goods.add(new Good());
		headerimg.setImageResource(R.drawable.header_img_default);
		uname.setText("Mr.xiao");
		MyGoodsAdapter myadapter=new  MyGoodsAdapter(getActivity(), goods);
		mygoods.setAdapter(myadapter);
		headerimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "暂不支持设置头像，敬请期待V2.0~", Toast.LENGTH_SHORT).show();
			}
		});
		mygoods.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "商品-->"+arg2, Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}
	
	
	
}
