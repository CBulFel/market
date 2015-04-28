package com.mark.market.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.mark.market.R;

public class MyAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Bitmap> bitset;
	private Button itemButon;
	private ImageView itemImg;

	// private MyView myview;
	public MyAdapter(Context c, ArrayList<Bitmap> bitset) {
		this.bitset = new ArrayList<Bitmap>();
		context = c;
		this.bitset = bitset;
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return bitset.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		LayoutInflater li = LayoutInflater.from(context);
		View vi = li.inflate(R.layout.comview, null);
		itemButon = (Button) vi.findViewById(R.id.mybutton);
		itemImg = (ImageView) vi.findViewById(R.id.myimg);
		itemImg.setImageBitmap(bitset.get(position % bitset.size()));
		// vi.setLayoutParams(new Gallery.LayoutParams(200, 200));
		/*
		 * float h,w; h = itemImg.getDrawable().getIntrinsicHeight(); w =
		 * itemImg.getDrawable().getIntrinsicWidth();
		 * 
		 * vi.setLayoutParams(new Gallery.LayoutParams((int) (80.0/(h/w)),
		 * 190));
		 */
		// itemImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

		return vi;
	}

}
