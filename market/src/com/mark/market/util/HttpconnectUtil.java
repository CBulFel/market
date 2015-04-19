/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.util;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeaderValueFormatter;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
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
			}

			post.setEntity(new UrlEncodedFormEntity(pairList, "UTF-8"));
		}
		response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity());
			// result = Base64.decodeBase64(result.getBytes()).toString();
			System.out.println("HttpconnectUtil get " + result);

		} else {
			System.out.println("http error");
		}
		return result;
	}

	// 利用流上传文件
	public static boolean upload(String url1, File file) throws IOException {
		URL url = new URL(url1);
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoInput(true);
		connect.setDoOutput(true);
		connect.setUseCaches(false);
		connect.setConnectTimeout(TIMEOUT);
		connect.setRequestMethod("POST");
		connect.setRequestProperty("Charset", CHARSET);
		if (file != null) {
			DataOutputStream dos = new DataOutputStream(
					connect.getOutputStream());
			StringBuffer strbuf = new StringBuffer();

		}
		return false;
	}

	/*
	 * @author mazhao
	 * 
	 * @describe 利用httppost上传文件方法
	 */
	public static String postfile(String url, Map<String, Object> params)
			throws ClientProtocolException, IOException {
		post = new HttpPost(url);
		Log.w(TAG, "postfile()");
		ArrayList<Bitmap> imgs = (ArrayList<Bitmap>) params.get("imgs");
		Map<String, Object> param = (Map<String, Object>) params.get("info");
		List<NameValuePair> pairList = new ArrayList<NameValuePair>();

		if (param != null) {
			Set<String> set = param.keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				Object value = param.get(key);
				pairList.add(new BasicNameValuePair(key.toString(), value
						.toString()));
			}

			post.setEntity(new UrlEncodedFormEntity(pairList, "UTF-8"));
		}
		ContentBody body = null;
		String result = null;

		for (Bitmap bitmap : imgs) {
			// 把图片存储到本地
			File file = new File(Environment.getExternalStorageDirectory(),
					"temp.jpg");
			OutputStream os = null;
			os = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(CompressFormat.JPEG, 100, os);
			if (file != null) {
				body = new FileBody(file);
			}
			String boundary = "-------------" + System.currentTimeMillis();
			//post.setHeader("Content-type", "multipart/form-data;boundary="
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.setBoundary(boundary);

			builder.addPart("imgs", body);

			response = client.execute(post);
			Log.w(TAG, response.getStatusLine().toString());
			if (response.getStatusLine().equals(HttpStatus.SC_OK)) {
				result = EntityUtils.toString(response.getEntity());
			}
			file.delete();
		}
		return result;
	}
}