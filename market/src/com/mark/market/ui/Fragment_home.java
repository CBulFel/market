package com.mark.market.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mark.android_ui.MyImgScroll;
import com.mark.android_ui.MyListView;
import com.mark.android_ui.MyListView.MyListViewListener;
import com.mark.market.R;
import com.mark.market.bean.Good;

public class Fragment_home extends Fragment implements MyListViewListener {

	// listview�ؼ�
	public static MyListView marketListView;
	private GoodsAdapter mAdapter;
	private LinkedList<Good> goods = new LinkedList<Good>();




	// ImgScroll���ֿؼ�
	MyImgScroll myPager; // ͼƬ����
	LinearLayout ovalLayout; // Բ������
	private List<View> listViews; // ͼƬ��

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_home, null);
		marketListView = (MyListView) view.findViewById(R.id.marketlist);
		
		View mview=inflater.inflate(R.layout.scrollimg, null);
		myPager = (MyImgScroll) mview.findViewById(R.id.martket_home_imgscroll);
		ovalLayout = (LinearLayout) mview.findViewById(R.id.vb);
		// ��ʼ��
		init();
		// ����imgScroll��ʼ����
		myPager.start(getActivity(), listViews, 4000, ovalLayout,
				R.layout.ad_bottom_item, R.id.ad_item_v,
				R.drawable.dot_focused, R.drawable.dot_normal);
		marketListView.addHeaderView(mview);
		return view;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	private void init() {

		/*
		 * imgScroll�ؼ��ĳ�ʼ��
		 */

		listViews = new ArrayList<View>();
		int[] imageResId = new int[] { R.drawable.scrollimg_img1, R.drawable.scrollimg_img2,
				R.drawable.scrollimg_img3, R.drawable.scrollimg_img4, R.drawable.scrollimg_img5 };
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {// ����ͼƬ����¼�
					Toast.makeText(getActivity(),
							"�����:" + myPager.getCurIndex(), Toast.LENGTH_SHORT)
							.show();
				}
			});
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			listViews.add(imageView);
		}

		/*
		 * Listview�ؼ��ĳ�ʼ��
		 */
		for(int i=0;i<=12;i++)
			goods.add(new Good());
		mAdapter=new GoodsAdapter(getActivity(), goods);
		marketListView.setPullLoadEnable(true);
		marketListView.setMyListViewListener(this);
		marketListView.setAdapter(mAdapter);
	}

	@SuppressWarnings("unused")
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		private Context context;
		private int index;

		public GetDataTask(Context context, int index) {
			this.context = context;
			this.index = index;
		}

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				;
			}
			String[] mStrings = null;
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			if (index == 0) {
				// ���ַ�����Added after refresh����ӵ�����
				//mListItems.addFirst("Added after refresh...");

				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy��MM��dd��  HH:mm");
				String date = format.format(new Date());
				// Call onRefreshComplete when the list has been refreshed.
				marketListView.setRefreshTime(date);
			} else if (index == 1) {
				//mListItems.addLast("Added after loadmore...");
				//marketListView.onLoadMoreComplete();
				marketListView.stopRefresh();
				marketListView.stopLoadMore();
			}

			super.onPostExecute(result);
		}
	}
	
	

	@Override
	public void onRefresh() {
		//new GetDataTask(getActivity(), 0).execute();
		/*mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				items.clear();
				geneItems();
				// mAdapter.notifyDataSetChanged();
				mAdapter = new ArrayAdapter<String>(getActivity(),
						R.layout.list_item, items);
				marketListView.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);*/
	}

	@Override
	public void onLoadMore() {
		//new GetDataTask(getActivity(), 1).execute();
		/*mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);*/
	}

}
