/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author mazhao
 * @describe 网络连接相关类
 */
public class HttpconnectUtil {
	private static String url = "http://fytest.duapp.com/person";
	
	

	/**
	 * 
	 */
	public HttpconnectUtil() {
		super();
	}



	@SuppressWarnings("deprecation")
	public static JSONObject getobject() throws ClientProtocolException, IOException, JSONException {
		JSONObject json=null;

		HttpGet get = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		System.out.println("http error");
		HttpResponse response = client.execute(get);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String result = EntityUtils.toString(response.getEntity());
			System.out.println("HttpconnectUtil get "+result);
			json = new JSONObject(result);
		}
		else {
			System.out.println("http error");
		}
		return json;
	}

}