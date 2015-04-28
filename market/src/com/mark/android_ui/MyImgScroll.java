package com.mark.android_ui;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 
 * @author mazhao
 * 
 */
public class MyImgScroll extends ViewPager {
	Activity mActivity; //activity实例 
	List<View> mListViews; //要滚动的图片控件list
	int mScrollTime = 0;
	Timer timer;
	int oldIndex = 0;
	int curIndex = 0;
	private static String TAG = "market->MyImgScroll";

	public MyImgScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void start(Activity mainActivity, List<View> imgList,
			int scrollTime, LinearLayout ovalLayout, int ovalLayoutId,
			int ovalLayoutItemId, int focusedId, int normalId) {
		mActivity = mainActivity;
		mListViews = imgList;
		mScrollTime = scrollTime;
		setOvalLayout(ovalLayout, ovalLayoutId, ovalLayoutItemId, focusedId,
				normalId);
		this.setAdapter(new MyPagerAdapter());//设置适配器
		if (scrollTime != 0 && imgList.size() > 1) {

			startTimer();
			// 点击停止滚动
			this.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_UP) {
						startTimer();
					} else {
						stopTimer();
					}
					return false;
				}
			});
		}
		if (mListViews.size() > 1) {
			this.setCurrentItem((Integer.MAX_VALUE / 2)
					- (Integer.MAX_VALUE / 2) % mListViews.size());
		}
	}

	// 设置滚动小圆圈图标
	private void setOvalLayout(final LinearLayout ovalLayout, int ovalLayoutId,
			final int ovalLayoutItemId, final int focusedId, final int normalId) {
		if (ovalLayout != null) {
			LayoutInflater inflater = LayoutInflater.from(mActivity);
			for (int i = 0; i < mListViews.size(); i++) {
				ovalLayout.addView(inflater.inflate(ovalLayoutId, null));

			}
			if (ovalLayout.getChildAt(0) == null)
				return;
			else
				ovalLayout.getChildAt(0).findViewById(ovalLayoutItemId)
						.setBackgroundResource(focusedId);
			this.setOnPageChangeListener(new OnPageChangeListener() {
				public void onPageSelected(int i) {
					curIndex = i % mListViews.size();
					ovalLayout.getChildAt(oldIndex)
							.findViewById(ovalLayoutItemId)
							.setBackgroundResource(normalId);
					//更换当前滚动位置的图片
					ovalLayout.getChildAt(curIndex)
							.findViewById(ovalLayoutItemId)
							.setBackgroundResource(focusedId);
					oldIndex = curIndex;
				}

				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}

				public void onPageScrollStateChanged(int arg0) {
				}
			});
		}
	}

	/**
	 * @return
	 */
	public int getCurIndex() {
		return curIndex;
	}

	/**
	 * ֹͣ停止计时器
	 */
	public void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/**
	 * 开始计时器
	 */
	public void startTimer() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				mActivity.runOnUiThread(new Runnable() {
					public void run() {
						MyImgScroll.this.setCurrentItem(MyImgScroll.this
								.getCurrentItem() + 1);
					}
				});
			}
		}, mScrollTime, mScrollTime);
	}

	// 适配器
	private class MyPagerAdapter extends PagerAdapter {
		public void finishUpdate(View arg0) {
		}

		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}

		public int getCount() {
			if (mListViews.size() == 1) {//如果只有一张图片
				return mListViews.size();
			}
			return Integer.MAX_VALUE;
		}

		public Object instantiateItem(View v, int i) {
			/*
			 * if (((ViewPager) v).getChildCount() == mListViews.size()) {
			 * ((ViewPager) v) .removeView(mListViews.get(i %
			 * mListViews.size())); }
			 */
			((ViewPager) v).removeView(mListViews.get(i % mListViews.size()));
			((ViewPager) v).addView(mListViews.get(i % mListViews.size()), 0);
			
			return mListViews.get(i % mListViews.size());
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
		}
	}
}
