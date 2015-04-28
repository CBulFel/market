/**
 * 
 */
package com.mark.market.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mark.market.R;
import com.mark.market.bean.Good;
import com.mark.market.logic.MainService;
import com.mark.market.util.DoCollect;
import com.mark.market.util.GoodItemClick;
import com.mark.market.util.Tools;

/**
 * @author mazhao
 * 
 * @describe 商品List的Adapter
 */
public class GoodsAdapter extends BaseAdapter {

	private static String TAG = "market GoodsAdapter";
	private Context context;
	private List<Good> goods = new ArrayList<Good>();
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private Good good;

	static class ViewHolder {
		public LinearLayout layout_item;
		public ImageView item_userimg;
		public TextView item_username;
		public TextView item_time;
		public TextView item_palce;
		public TextView item_price;// 最低价
		public TextView item_preprice;// 最高价
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
	 */
	public GoodsAdapter(Context context) {
		super();
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		good = goods.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.goods_item, null);
			holder = new ViewHolder();
			// 获得各个组件，注意这里getviewbyid是通过传入的本条目的convertView
			holder.layout_item = (LinearLayout) convertView
					.findViewById(R.id.goods_item_layout_item);
			holder.item_userimg = (ImageView) convertView
					.findViewById(R.id.goods_item_userimg);
			holder.item_username = (TextView) convertView
					.findViewById(R.id.goods_item_username);
			holder.item_time = (TextView) convertView
					.findViewById(R.id.goods_item_time);
			holder.item_palce = (TextView) convertView
					.findViewById(R.id.goods_item_place);
			holder.item_price = (TextView) convertView
					.findViewById(R.id.goods_item_price);
			holder.item_preprice = (TextView) convertView
					.findViewById(R.id.goods_item_preprice);

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
		try {
			holder.item_userimg.setImageResource(R.drawable.header_img_default);
			holder.item_username.setText(good.getUser().getUname());
			if (good.getGimg1() != null) {
				Tools.loadBitmap(MainService.MARKETHOST + good.getGimg1(),
						holder.item_content_pic1, 0, 0);
			} else {
				holder.item_content_pic1.setVisibility(View.GONE);
			}
			if (good.getGimg2() != null) {
				Tools.loadBitmap(MainService.MARKETHOST + good.getGimg2(),
						holder.item_content_pic2, 0, 0);
			} else {
				holder.item_content_pic2.setVisibility(View.GONE);
			}
			if (good.getGimg3() != null) {
				Tools.loadBitmap(MainService.MARKETHOST + good.getGimg3(),
						holder.item_content_pic3, 0, 0);
			} else {
				holder.item_content_pic3.setVisibility(View.GONE);
			}

			holder.item_time.setText(Tools.formattime(good.getGtime()));
			holder.item_price.setText(good.getGprice().toString());
			holder.item_preprice.setText(good.getGprePrice().toString());
			holder.item_palce.setText(good.getGplace());
			holder.item_time.setText(Tools.formattime(good.getGtime()));
			// 判断商品是否收藏
			holder.item_like.setChecked(good.getGvalid());
			holder.item_like.setText(good.getGcollectNum().toString());
			holder.item_comment.setText(good.getGcommentNum().toString());
			holder.item_content.setText(good.getGdescription());
		} catch (Exception e) {
			Log.e(TAG, "返回数据异常");
			Log.e(TAG, "catched:" + e.getMessage());
			Log.e(TAG, "cause:" + e.getCause());
		}
		holder.layout_item.setOnClickListener(new GoodItemClick(context,
				position, good));
		holder.layout_comment.setOnClickListener(new GoodItemClick(context,
				position, good));
		holder.layout_share.setOnClickListener(new GoodItemClick(context,
				position, good));
		holder.layout_like.setOnClickListener(new GoodItemClick(context,
				position, good));
		holder.item_like
				.setOnCheckedChangeListener(new DoCollect(context, good));
		return convertView;
	}

	public void addgoods(List<Good> goods_more) {
		goods.addAll(goods_more);

	}

	public void setgoods(List<Good> goods_new) {
		goods = goods_new;
	}

}

