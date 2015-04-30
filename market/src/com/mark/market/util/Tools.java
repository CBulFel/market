/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.mark.android_util.AsyncImageLoader;

/**
 * @author mazhao
 * @describ
 */
public class Tools {
	public static final String CACHE_DIR = Environment
			.getExternalStorageDirectory() + "/market2/cache";
	// 用于实现商品中的图片缓存
	// 获取当前应用程序所分配的最大内存
	private final static int maxMemory = (int) Runtime.getRuntime().maxMemory();
	// 只分5分之一用来做图片缓存
	private final static int cacheSize = maxMemory / 8;

	private static LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(
			cacheSize) {
		protected int sizeOf(String key, Bitmap bitmap) {
			// 复写sizeof()方法
			return bitmap.getRowBytes() * bitmap.getHeight() / 1024; // 这里是按多少KB来算
		}

		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {
			// TODO Auto-generated method stub
			super.entryRemoved(evicted, key, oldValue, newValue);
		}
		
	};

	/*
	 * @param urlStr 所需要加载的图片的url，以String形式传进来，可以把这个url作为缓存图片的key
	 * 
	 * @param image ImageView 控件
	 */
	public static void loadBitmap(String urlStr, ImageView image, int width,
			int height) {
		// 异步图片加载类
		AsyncImageLoader asyncLoader = new AsyncImageLoader(image, mLruCache,
				width, height);

		// 首先从内存缓存中获取图片
		Bitmap bitmap = null;
		try {
			bitmap = asyncLoader.getBitmapFromMemoryCache(urlStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("tools", e.getMessage());
		}

		// 如果缓存中存在这张图片则直接设置给ImageView
		if (bitmap != null) {
			image.setImageBitmap(bitmap);
		} else {
			// 否则先设置成默认的图片
			// image.setImageResource(R.drawable.header_img_default);
			// 然后执行异步任务AsycnTask去网上加载图片
			asyncLoader.execute(urlStr);
		}
	}

	// 初始化文件目录
	public static void initdir() {
		File file = new File(CACHE_DIR);
		if (!file.exists()) {
			file.mkdirs();
		}

	}

	/*
	 * 一个工具方法：作用是把系统时间 转换成当前时间的 前*分钟，前*小时
	 */
	public static String dealTime(Date date) {
		Date now = new Date();

		long lnow = now.getTime() / 1000;

		long ldate = date.getTime() / 1000;

		if ((lnow - ldate) < 60)
			return (lnow - ldate) + "秒前";
		else if ((lnow - ldate) < 60 * 60)
			return ((lnow - ldate) / 60) + "分钟前";
		else
			return date.getHours() + ":" + date.getMinutes();
	}

	/*
	 * 把指定时间显示为年：月：日 时：分格式
	 */
	public static String formattime(Date date) {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
		return sdformat.format(date);

	}
	
	
}
