/**
 * 
 */
package com.mark.market.adapter;

import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.sax.StartElementListener;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.android_util.AsyncImageLoader;
import com.mark.market.R;
import com.mark.market.bean.Good;
import com.mark.market.ui.Gooddetail;

/**
 * @author mazhao
 * 
 * @describe 商品List的Adapter
 */
public class GoodsAdapter extends BaseAdapter implements OnClickListener{

	private Context context;
	private List<Good> goods;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private int p;
	private Good good;
	

	static class ViewHolder {
		public LinearLayout layout_item;
		public ImageView item_userimg;
		public TextView item_username;
		public TextView item_time;
		public TextView item_content;
		public ImageView item_content_pic1;
		public ImageView item_content_pic2;
		public ImageView item_content_pic3;
		public LinearLayout layout_like;
		public LinearLayout layout_comment;
		public LinearLayout layout_share;
		public CheckBox item_like;
		public TextView item_comment;
		public TextView item_share;

	}

	/**
	 * @param context
	 * @param goods商品列表
	 */
	public GoodsAdapter(Context context, List<Good> goods) {
		super();
		this.context = context;
		this.goods = goods;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return goods.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unused")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		p=position;
		good = goods.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.goods_item, null);
			holder = new ViewHolder();
			// 获得各个组件，注意这里getviewbyid是通过传入的本条目的convertView
			holder.layout_item=(LinearLayout)convertView.findViewById(R.id.goods_item_layout_item);
			holder.item_userimg = (ImageView) convertView
					.findViewById(R.id.goods_item_userimg);
			holder.item_username = (TextView) convertView
					.findViewById(R.id.goods_item_username);
			holder.item_time = (TextView) convertView
					.findViewById(R.id.goods_item_time);
			holder.item_content = (TextView) convertView
					.findViewById(R.id.goods_item_content);
			holder.item_content_pic1 = (ImageView) convertView
					.findViewById(R.id.goods_item_content_pic1);
			holder.item_content_pic2 = (ImageView) convertView
					.findViewById(R.id.goods_item_content_pic2);
			holder.item_content_pic3 = (ImageView) convertView
					.findViewById(R.id.goods_item_content_pic3);
			holder.layout_like = (LinearLayout) convertView
					.findViewById(R.id.like);
			holder.layout_comment = (LinearLayout) convertView
					.findViewById(R.id.comment);
			holder.layout_share = (LinearLayout) convertView
					.findViewById(R.id.share);
			holder.item_like = (CheckBox) convertView
					.findViewById(R.id.goods_item_like);
			holder.item_comment = (TextView) convertView
					.findViewById(R.id.goods_item_comment);
			holder.item_share = (TextView) convertView
					.findViewById(R.id.goods_item_share);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		/*
		 * 组件填充内容
		 */
		holder.item_userimg.setImageResource(R.drawable.header_img_default);
		holder.item_username.setText("小明");
		holder.item_content_pic1.setImageResource(R.drawable.featured_big_b1);
		// loadBitmap(good.getGimg1(), holder.item_content_pic1, 0, 0);
		holder.item_like.setChecked(true);
		holder.item_like.setText("23");
		holder.item_comment.setText("45");
		holder.layout_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "item---->"+p, Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(context,Gooddetail.class);
				context.startActivity(intent);
			}
		});
		holder.layout_like.setOnClickListener(this);
		holder.layout_comment.setOnClickListener(this);
		holder.layout_share.setOnClickListener(this);
		holder.item_like.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked) Toast.makeText(context, "收藏---->待完成", Toast.LENGTH_SHORT).show();
				else Toast.makeText(context, "取消收藏---->待完成", Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

	public void addgoods(Good good) {
		goods.add(good);

	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.goods_item_layout_item:
			Toast.makeText(context, "item---->"+p, Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(context,Gooddetail.class);
			intent.putExtra("Gid", good.getGid());
			context.startActivity(intent);
			break;
		case R.id.like:
			Toast.makeText(context, "真心～请点击红心", Toast.LENGTH_SHORT).show();
			break;
		case  R.id.comment:
			Toast.makeText(context, "评论---->待完成", Toast.LENGTH_SHORT).show();
			break;
		case R.id.share:
			Toast.makeText(context, "分享---->待完成", Toast.LENGTH_SHORT).show();
			break;
			default:break;
		}
	}

}