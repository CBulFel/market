/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author mazhao
 * @describe 网络连接相关类
 */
public class HttpconnectUtil {
	private static final String url = "http://192.168.1.158:8080/android_demo/test";
	private static final int TIMEOUT=3*1000;
	private static final String CHARSET = "utf-8";
	/**
	 * 
	 */
	public HttpconnectUtil() {
		super();
	}

	@SuppressWarnings("deprecation")
	public static String getResult(String url, Map<String, Object> param)
			throws IOException {
		String result = null;
		HttpPost post = new HttpPost(url);
		HttpClient client = new DefaultHttpClient();
		List<NameValuePair> pairList = new ArrayList<NameValuePair>();
		
		if (param != null) {
			Set set = param.keySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				Object value = param.get(key);
				pairList.add(new BasicNameValuePair(key.toString(), value
						.toString()));
			}

			post.setEntity(new UrlEncodedFormEntity(pairList, "UTF-8"));
		}
		HttpResponse response = client.execute(post);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity());
			result = Base64.decodeBase64(result.getBytes()).toString();
			System.out.println("HttpconnectUtil get " + result);

		} else {
			System.out.println("http error");
		}
		return result;
	}
	
	public static boolean upload(String url1,File file) throws IOException{
		URL url=new URL(url1);
		HttpURLConnection connect=(HttpURLConnection)url.openConnection();
		connect.setDoInput(true);
		connect.setDoOutput(true);
		connect.setUseCaches(false);
		connect.setConnectTimeout(TIMEOUT);
		connect.setRequestMethod("POST");
		connect.setRequestProperty("Charset", CHARSET);
		if(file!=null){
			DataOutputStream dos=new DataOutputStream(connect.getOutputStream());
			StringBuffer strbuf=new StringBuffer();
			
		}
		return false;
	}
}