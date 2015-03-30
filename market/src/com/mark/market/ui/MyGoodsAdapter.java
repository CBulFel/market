/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.ui;

import java.util.List;

import com.mark.market.R;
import com.mark.market.bean.Good;
import com.mark.market.ui.GoodsAdapter.ViewHolder;
import com.mark.market.util.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author mazhao
 * @describ 
 */
public class MyGoodsAdapter extends BaseAdapter {

	private Context context;
	private List<Good> goods;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	
	static class ViewHolder{
		public ImageView item_img;
		public TextView item_likenum;
		public TextView item_comnum;
	}
	
	
	
	/**
	 * @param context
	 * @param goods
	 */
	public MyGoodsAdapter(Context context, List<Good> goods) {
		super();
		this.context = context;
		this.goods = goods;
		this.mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return goods.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Good good=goods.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.gridview_item, null);
			holder = new ViewHolder();
			// 获得各个组件，注意这里getviewbyid是通过传入的本条目的convertView
			holder.item_img=(ImageView)convertView.findViewById(R.id.mygoods_item_img);
			holder.item_likenum=(TextView)convertView.findViewById(R.id.mygoods_item_likenum);
			holder.item_comnum=(TextView)convertView.findViewById(R.id.mygoods_item_commentnum);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//填充内容
		/*Tools.loadBitmap(good.getGimg1(), holder.item_img, 0, 0);
		holder.item_likenum.setText(good.getGcollectNum());
		holder.item_comnum.setText(good.getGcommentNum());*/
		holder.item_img.setImageResource(R.drawable.featured_big_b1);
		holder.item_likenum.setText("12");
		holder.item_comnum.setText("23");

		return convertView;
	}

}
