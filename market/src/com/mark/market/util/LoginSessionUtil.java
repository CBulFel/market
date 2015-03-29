package com.mark.market.util;


import com.mark.market.bean.User;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author mazhao
 * @version 1.0
 * @describe 类似于Web中的登录session
 */
public class LoginSessionUtil {
	/*
	 * 保存当前登录用户信息到SharePrefrence
	 * @param context
	 * @param user
	 */
	public static void SaveLoginUser(Context context,User user)
	{
		SharedPreferences sp=context.getSharedPreferences("LOGIN_USER",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		editor.putString(User.USERID, user.getUid());
		editor.putString(User.USERNAME, user.getUname());
		editor.putString(User.USERPWD, user.getUpwd());
		editor.commit();
	}
	
	/*
	 * 从SharePrefrence获取当前登录用户信息
	 * @param context
	 * @return LoginUser
	 */
	public static User getLoginUser(Context context)
	{
		SharedPreferences sp=context.getSharedPreferences("LOGIN_USER",Context.MODE_PRIVATE);
		String userName=sp.getString(User.USERNAME, "");
		String userId=sp.getString(User.USERID, "");
		String pwd=sp.getString(User.USERPWD, "");
		if("".equals(userName))
			return null;
		else
			return new User(userId,userName,pwd);
	}
	
	/*
	 * 退出登录，从SharePrefrence删除当前登录用户信息
	 * @param context
	 */
	public static void removeLoginUser(Context context)
	{
		SharedPreferences sp=context.getSharedPreferences("LOGIN_USER",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		editor.remove(User.USERID);
		editor.remove(User.USERPWD);
		editor.remove(User.USERNAME);	
		editor.commit();
	}
	
}
