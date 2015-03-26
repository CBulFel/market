/**
 * 
 */
package com.mark.market.ui;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.android_util.AsyncImageLoader;
import com.mark.market.R;
import com.mark.market.bean.Good;

/**
 * @author mazhao
 * 
 * @describe 商品List的Adapter
 */
public class GoodsAdapter extends BaseAdapter {

	private Context context;
	private List<Good> goods;
	private LayoutInflater mInflater;
	private ViewHolder holder;

	// 用于实现商品List中的图片缓存
	// 获取当前应用程序所分配的最大内存
	private final int maxMemory = (int) Runtime.getRuntime().maxMemory();
	// 只分5分之一用来做图片缓存
	private final int cacheSize = maxMemory / 5;

	private LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(
			cacheSize) {
		protected int sizeOf(String key, Bitmap bitmap) {
			// 复写sizeof()方法
			return bitmap.getRowBytes() * bitmap.getHeight() / 1024; // 这里是按多少KB来算
		}
	};

	static class ViewHolder {
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
		Good good = goods.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.goods_item, null);
			holder = new ViewHolder();
			// 获得各个组件，注意这里getviewbyid是通过传入的本条目的convertView
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
		holder.layout_like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/* 收藏，待完成 */
				Toast.makeText(context, "收藏---->待完成", Toast.LENGTH_SHORT).show();
			}
		});
		holder.layout_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/* 评论，待完成 */
				Toast.makeText(context, "评论---->待完成", Toast.LENGTH_SHORT).show();
			}
		});
		holder.layout_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/* 一键分享，待完成 */
				Toast.makeText(context, "分享---->待完成", Toast.LENGTH_SHORT).show();
			}
		});

		return convertView;
	}

	public void addgoods(Good good) {
		goods.add(good);

	}

	/*
	 * @param urlStr 所需要加载的图片的url，以String形式传进来，可以把这个url作为缓存图片的key
	 * 
	 * @param image ImageView 控件
	 */
	private void loadBitmap(String urlStr, ImageView image, int width,
			int height) {
		// 异步图片加载类
		AsyncImageLoader asyncLoader = new AsyncImageLoader(image, mLruCache,
				width, height);
		// 首先从内存缓存中获取图片
		Bitmap bitmap = asyncLoader.getBitmapFromMemoryCache(urlStr);
		// 如果缓存中存在这张图片则直接设置给ImageView
		if (bitmap != null) {
			image.setImageBitmap(bitmap);
		} else {
			image.setImageResource(R.drawable.user_head);// 否则先设置成默认的图片
			asyncLoader.execute(urlStr);// 然后执行异步任务AsycnTask去网上加载图片
		}
	}

	/*
	 * 一个工具方法：作用是把系统时间 转换成当前时间的 前*分钟，前*小时
	 */
	@SuppressWarnings("deprecation")
	public String dealTime(String time) {
		Date now = new Date();
		long lnow = now.getTime() / 1000;

		long ldate = Date.parse(time) / 1000;
		Date date = new Date(ldate);

		if ((lnow - ldate) < 60)
			return (lnow - ldate) + "秒前";
		else if ((lnow - ldate) < 60 * 60)
			return ((lnow - ldate) / 60) + "分钟前";
		else
			return date.getHours() + ":" + date.getMinutes();
	}

}
