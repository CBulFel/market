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
 * ͼƬ������
 * 
 * @author Administrator
 * 
 */
public class MyImgScroll extends ViewPager {
	Activity mActivity; // ������
	List<View> mListViews; // ͼƬ��
	int mScrollTime = 0;
	Timer timer;
	int oldIndex = 0;
	int curIndex = 0;

	public MyImgScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * ��ʼ������
	 * 
	 * @param mainActivity
	 *            ��ʾ����������
	 * @param imgList
	 *            ͼƬ�б�, ����Ϊnull ,����һ��
	 * @param scrollTime
	 *            ������� ,0Ϊ������
	 * @param ovalLayout
	 *            Բ������,��Ϊ��,LinearLayout����
	 * @param ovalLayoutId
	 *            ovalLayoutΪ��ʱ д0, Բ��layout XMl
	 * @param ovalLayoutItemId
	 *            ovalLayoutΪ��ʱ д0,Բ��layout XMl Բ��XMl��View ID
	 * @param focusedId
	 *            ovalLayoutΪ��ʱ д0, Բ��layout XMl ѡ��ʱ�Ķ���
	 * @param normalId
	 *            ovalLayoutΪ��ʱ д0, Բ��layout XMl ����ʱ����
	 */
	public void start(Activity mainActivity, List<View> imgList,
			int scrollTime, LinearLayout ovalLayout, int ovalLayoutId,
			int ovalLayoutItemId, int focusedId, int normalId) {
		mActivity = mainActivity;
		mListViews = imgList;
		mScrollTime = scrollTime;
		// ����Բ��
		setOvalLayout(ovalLayout, ovalLayoutId, ovalLayoutItemId, focusedId,
				normalId);
		this.setAdapter(new MyPagerAdapter());// ����������
		if (scrollTime != 0 && imgList.size() > 1) {
			// ���û�������ʱ��  ,�����Ĭ�϶���ʱ��ɲ��� ,���似��ʵ��
		//	 new FixedSpeedScroller(mActivity).setDuration(this, 700);
	
			startTimer();
			// ����ʱֹͣ����
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
					- (Integer.MAX_VALUE / 2) % mListViews.size());// ����ѡ��Ϊ�м�/ͼƬΪ�͵�0��һ��
		}
	}

	// ����Բ��
	private void setOvalLayout(final LinearLayout ovalLayout, int ovalLayoutId,
			final int ovalLayoutItemId, final int focusedId, final int normalId) {
		if (ovalLayout != null) {
			LayoutInflater inflater=LayoutInflater.from(mActivity);
			for (int i = 0; i < mListViews.size(); i++) {
				ovalLayout.addView(inflater.inflate(ovalLayoutId, null));
				
			}
			//ѡ�е�һ��
			ovalLayout.getChildAt(0).findViewById(ovalLayoutItemId)
			.setBackgroundResource(focusedId);
			this.setOnPageChangeListener(new OnPageChangeListener() {
				public void onPageSelected(int i) {
					curIndex = i % mListViews.size();
                    //ȡ��Բ��ѡ��
					ovalLayout.getChildAt(oldIndex).findViewById(ovalLayoutItemId)
							.setBackgroundResource(normalId);
					 //Բ��ѡ��
					ovalLayout.getChildAt(curIndex).findViewById(ovalLayoutItemId)
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
	 * ȡ�õ���ѡ���±�
	 * @return
	 */
    public int getCurIndex() {
		return curIndex;
	}
	/**
	 * ֹͣ����
	 */
	public void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/**
	 * ��ʼ����
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

	// ������ //ѭ������
	private class MyPagerAdapter extends PagerAdapter {
		public void finishUpdate(View arg0) {
		}

		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
		}

		public int getCount() {
			if (mListViews.size() == 1) {// һ��ͼƬʱ��������
				return mListViews.size();
			}
			return Integer.MAX_VALUE;
		}

		public Object instantiateItem(View v, int i) {
			if (((ViewPager) v).getChildCount() == mListViews.size()) {
				((ViewPager) v)
						.removeView(mListViews.get(i % mListViews.size()));
			}
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
