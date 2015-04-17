package com.mark.android_util;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author mazhao
 * @version 1.0
 * @describe 异步加载图片类(包含图片的内存缓存实现)
 */
public class AsyncImageLoader extends AsyncTask<String, Void, Bitmap> {

	private ImageView image;  
    private LruCache<String, Bitmap> lruCache;  
    private int width;
    private int height;
    
    /*
     * 构造方法，需要把ImageView控件和LruCache 对象传进来 
     * @param image 加载图片到此 {@code}ImageView 
     * @param lruCache 缓存图片的对象 
     */ 
    public AsyncImageLoader(ImageView image, LruCache<String, Bitmap> lruCache,int width,int height) {  
        super();  
        this.image = image;  
        this.lruCache = lruCache;  
        this.width=width;
        this.height=width;
    }  
  
    protected Bitmap doInBackground(String... params) {  
        Bitmap bitmap = null;  
       
        bitmap = getBitmap(params[0]); 
        if(width!=0&height!=0)
        	bitmap= scaleImg(bitmap, width, height);
        addBitmapToMemoryCache(params[0], bitmap);  
        return bitmap;  
    }  
  
    @Override  
    protected void onPostExecute(Bitmap bitmap) {  
        image.setImageBitmap(bitmap);  
    }  
    
    //调用LruCache的put 方法将图片加入内存缓存中，要给这个图片一个key 方便下次从缓存中取出来  
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {  
    	
        if (getBitmapFromMemoryCache(key) == null) {  
        	 Log.w("market lrucache", key);
            lruCache.put(key, bitmap);  
        }  
    }  
    
    //调用Lrucache的get 方法从内存缓存中去图片  
    public Bitmap getBitmapFromMemoryCache(String key) {  
        return lruCache.get(key);  
    }  
    
  //网络下载biturl 地址的图片资源，返回Bitmap
  	public static Bitmap getBitmap(String biturl)
  	{
  		Bitmap bitmap=null;
  		try {
  			URL url=new URL(biturl);
  			URLConnection conn=url.openConnection();
  			InputStream in =conn.getInputStream();
  			bitmap=BitmapFactory.decodeStream(new BufferedInputStream(in));

  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		return bitmap;
  	}
  	
  	//变换原Bitmap为 newWidth和newHeight大小的Bitmap返回 
  	public static Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) 
  	{
  	    // 获得图片的宽高
  	    int width = bm.getWidth();
  	    int height = bm.getHeight();
  	    // 设置想要的大小
  	    int newWidth1 = newWidth;
  	    int newHeight1 = newHeight;
  	    // 计算缩放比例
  	    float scaleWidth = ((float) newWidth1) / width;
  	    float scaleHeight = ((float) newHeight1) / height;
  	    // 取得想要缩放的matrix参数
  	    Matrix matrix = new Matrix();
  	    matrix.postScale(scaleWidth, scaleHeight);
  	    // 得到新的图片
  	    Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
  	    return newbm;
  	 }
}
