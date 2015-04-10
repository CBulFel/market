/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.adapter;

import java.util.ArrayList;
import java.util.List;

import com.mark.android_ui.CircularImage;
import com.mark.market.R;
import com.mark.market.bean.GComment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author mazhao
 * @describ 
 */
public class CommentsAdapter extends BaseAdapter {

	private Context context;
	private List<GComment> comments;
	private LayoutInflater inflater;
	private ViewHolder holder;
	
	private class ViewHolder{
		public CircularImage comment_headimg;
		public TextView comment_uname;
		public TextView comment_content;
		
	}
	
	
	/**
	 * @param context	当前上下文
	 * @param comments 传入的评论实例
	 */
	public CommentsAdapter(Context context, List<GComment> comments) {
		super();
		this.context = context;
		this.comments = comments;
		this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
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
		GComment comment=comments.get(position);
		if(convertView==null){
			convertView=inflater.inflate(R.layout.comments_item, null);
			holder=new ViewHolder();
			
			holder.comment_headimg=(CircularImage)convertView.findViewById(R.id.comment_headimg);
			holder.comment_uname=(TextView)convertView.findViewById(R.id.comment_uname);
			holder.comment_content=(TextView)convertView.findViewById(R.id.comment_content);
			convertView.setTag(holder);
		}
		else {
			holder=(ViewHolder)convertView.getTag();
		}
		
		holder.comment_headimg.setImageResource(R.drawable.header_img_default);
		holder.comment_uname.setText("小明");
		holder.comment_content.setText("评论评论评论评论");
		
		
		
		return convertView;
	}

}
