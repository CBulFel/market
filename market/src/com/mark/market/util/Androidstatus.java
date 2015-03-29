/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

/**
 * @author mazhao
 * @describ 获得手机的当前状态，是否有内存卡，是否联网。。。
 */
public class Androidstatus {

	public static boolean hasSD() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNetworkAvaliable(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager==null){
			return false;
		}
		else{
		//获取networkinfo对象
		NetworkInfo[] networkinfo=connectivityManager.getAllNetworkInfo();
		 if (networkinfo != null && networkinfo.length > 0)
         {
             for (int i = 0; i < networkinfo.length; i++)
             {
                 System.out.println(i + "===状态===" + networkinfo[i].getState());
                 System.out.println(i + "===类型===" + networkinfo[i].getTypeName());
                 // 判断当前网络状态是否为连接状态
                 if (networkinfo[i].getState() == NetworkInfo.State.CONNECTED)
                 {
                     return true;
                 }
             }
         }
		
		}
		
		return false;

	}

}
