package com.mark.market.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.android_ui.MyImgScroll;
import com.mark.market.R;
import com.mark.market.bean.GComment;

public class Gooddetail extends Activity {

	private ListView list_comments;
	
	private MyImgScroll detail_img;
	private LinearLayout ovalLayout; // 下方的小圆点
	private List<View> listimgs; // 要滚动的 图片组
	private LinkedList<GComment> comments=new LinkedList<GComment>();
	private ViewHolder holder=new ViewHolder();

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gooddetail);
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

					Toast.makeText(Gooddetail.this,
							"点击了:" + detail_img.getCurIndex(),
							Toast.LENGTH_SHORT).show();
				}
			});
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_INSIDE);
			listimgs.add(imageView);
		}
		GComment comment=new GComment();
		for(int i=0;i<10;i++){
			comments.add(comment);
		}
		CommentsAdapter adapter=new CommentsAdapter(this, comments);
		list_comments.setAdapter(adapter);
		
		
		
		
	}

}
