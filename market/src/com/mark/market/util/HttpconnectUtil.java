/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

/**
 * @author mazhao
 * @describe 网络链接util类
 */
@SuppressWarnings("deprecation")
public class HttpconnectUtil {
	private static final String TAG = "market->httpconnecttuil";
	private static final int TIMEOUT = 3 * 1000;
	private static final String CHARSET = "utf-8";
	private static HttpClient client = new DefaultHttpClient();
	private static HttpPost post = null;
	private static HttpResponse response = null;

	/**
	 * 
	 */
	public HttpconnectUtil() {
		super();
	}

	public static String getResult(String url, Map<String, Object> param)
			throws IOException {
		// Log.w(TAG, url);
		String result = null;
		post = new HttpPost(url);

		List<NameValuePair> pairList = new ArrayList<NameValuePair>();

		if (param != null) {

			Set<String> set = param.keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				Object value = param.get(key);
				pairList.add(new BasicNameValuePair(key.toString(), value
						.toString()));
				// Log.w(TAG,"key:" + key.toString() + " value:" +
				// value.toString());
			}

			post.setEntity(new UrlEncodedFormEntity(pairList, "UTF-8"));

			/*
			 * for (String key : param.keySet()) { Log.w(TAG, key + ":" +
			 * param.get(key)); pairList.add(new BasicNameValuePair(key,
			 * param.get(key) .toString())); } post.setEntity(new
			 * UrlEncodedFormEntity(pairList, "UTF-8"));
			 */
		} else {
			Log.w(TAG, "param is null");
		}

		response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			Log.w(TAG, "getresult httpstatus:"
					+ response.getStatusLine().getStatusCode());
			result = EntityUtils.toString(response.getEntity());
			// Log.w(TAG, "httpconnectutil getresult:" + result);

		} else {
			Log.w(TAG, "getresult httpstatus:"
					+ response.getStatusLine().getStatusCode());
			Log.w(TAG, "http error!");
		}

		return result;
	}

	/*
	 * @author mazhao
	 * 
	 * @describe 利用httppost上传文件方法
	 */
	public static String postfile(String url, Map<String, Object> params)
			throws ClientProtocolException, IOException {
		post = new HttpPost(url);
		// Log.w(TAG, "postfile()");
		ArrayList<Bitmap> imgs = (ArrayList<Bitmap>) params.get("imgs");
		Map<String, String> param = (Map<String, String>) params.get("info");
		ContentBody body = null;
		String result = null;
		File file = null;
		MultipartEntity reqentity = new MultipartEntity();
		if (param != null) {
			for (String key : param.keySet()) {
				// Log.w(TAG, key + ":" + param.get(key));
				System.out.println(key + ":" + param.get(key));
				System.out.println(key
						+ ":"
						+ new String(Base64.decodeBase64(param.get(key)
								.toString().getBytes())));
				reqentity.addPart(key,
						new StringBody(param.get(key).toString()));
			}
		} else {
			Log.w(TAG, "param is null");
		}
		for (Bitmap bitmap : imgs) {
			// 把图片存储到本地
			file = new File(Tools.CACHE_DIR, System.nanoTime() + ".jpg");
			OutputStream os = null;
			os = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(CompressFormat.JPEG, 100, os);
			if (file != null) {
				body = new FileBody(file);
				reqentity.addPart("imgs", body);
			}
		}

		post.setEntity(reqentity);

		response = client.execute(post);
		Log.w(TAG, response.getStatusLine().toString());
		if (response.getStatusLine().equals(HttpStatus.SC_OK)) {
			Log.w(TAG, "postfile httpstatus:"
					+ response.getStatusLine().getStatusCode());
			result = EntityUtils.toString(response.getEntity());
			// Log.w(TAG, "httpconnectutil postfile result:" + result);
		} else {
			Log.w(TAG, "postfile httpstatus:"
					+ response.getStatusLine().getStatusCode());
			Log.w(TAG, "http error!");
		}

		return result;
	}
}