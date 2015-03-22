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
import android.widget.ImageView;

/**
 * @author mazhao
 * @version 1.0
 * @describe �첽����ͼƬ��(����ͼƬ���ڴ滺��ʵ��)
 */
public class AsyncImageLoader extends AsyncTask<String, Void, Bitmap> {

	private ImageView image;  
    private LruCache<String, Bitmap> lruCache;  
    private int width;
    private int height;
    
    /*
     * ���췽������Ҫ��ImageView�ؼ���LruCache ���󴫽��� 
     * @param image ����ͼƬ���� {@code}ImageView 
     * @param lruCache ����ͼƬ�Ķ��� 
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
    
    //����LruCache��put ������ͼƬ�����ڴ滺���У�Ҫ�����ͼƬһ��key �����´δӻ�����ȡ����  
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {  
        if (getBitmapFromMemoryCache(key) == null) {  
            lruCache.put(key, bitmap);  
        }  
    }  
    
    //����Lrucache��get �������ڴ滺����ȥͼƬ  
    public Bitmap getBitmapFromMemoryCache(String key) {  
        return lruCache.get(key);  
    }  
    
  //��������biturl ��ַ��ͼƬ��Դ������Bitmap
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
  	
  	//�任ԭBitmapΪ newWidth��newHeight��С��Bitmap���� 
  	public static Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) 
  	{
  	    // ���ͼƬ�Ŀ��
  	    int width = bm.getWidth();
  	    int height = bm.getHeight();
  	    // ������Ҫ�Ĵ�С
  	    int newWidth1 = newWidth;
  	    int newHeight1 = newHeight;
  	    // �������ű���
  	    float scaleWidth = ((float) newWidth1) / width;
  	    float scaleHeight = ((float) newHeight1) / height;
  	    // ȡ����Ҫ���ŵ�matrix����
  	    Matrix matrix = new Matrix();
  	    matrix.postScale(scaleWidth, scaleHeight);
  	    // �õ��µ�ͼƬ
  	    Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
  	    return newbm;
  	 }
}
