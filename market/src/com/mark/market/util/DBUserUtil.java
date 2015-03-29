/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mark.market.bean.User;

/**
 * @author mazhao
 * @describ 本地数据库用户表的操作
 */
public class DBUserUtil {

	private DBHelper dbhelper;

	/**
	 * @param 构造函数
	 */
	public DBUserUtil(Context context) {
		dbhelper = new DBHelper(context);
	}

	public void insertUserinfo(User user) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();

		ContentValues values = new ContentValues(3);
		values.put(User.USERID, user.getUid());
		values.put(User.USERNAME, user.getUname());
		values.put(User.USERPWD, user.getUpwd());
		// 插入数据
		db.insert(User.TB_USER, null, values);
		db.close();
	}

	/*
	 * 从本地数据库获取存储的用户信息 判断uid用户是否已存到本地数据库
	 */
	public User getUserInfobyUid(String uid) {

		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor = db.query(User.TB_USER, new String[] { User.USERID,
				User.USERNAME, User.USERPWD }, User.USERID + "=?",
				new String[] { uid }, null, null, null);
		User user = null;
		if (cursor != null) {
			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				user = new User();
				String uId = cursor.getString(cursor
						.getColumnIndex(User.USERID));
				String userName = cursor.getString(cursor
						.getColumnIndex(User.USERNAME));
				String userPWD = cursor.getString(cursor
						.getColumnIndex(User.USERPWD));
				user.setUid(uId);
				user.setUname(userName);
				user.setUpwd(userPWD);

			}
		}
		db.close();
		return user;
	}

	// 获取本地数据库所有用户信息
	public List<User> getAllUserInfo() {
		List<User> users = new ArrayList<User>();

		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor = db.query(User.TB_USER, new String[] { User.USERID,
				User.USERNAME, User.USERPWD }, null, null, null, null, null);
		User userinfo = null;

		while (cursor.moveToNext()) {
			userinfo = new User();
			String uId = cursor.getString(cursor.getColumnIndex(User.USERID));
			String userName = cursor.getString(cursor
					.getColumnIndex(User.USERNAME));
			String userPWD = cursor.getString(cursor
					.getColumnIndex(User.USERPWD));
			userinfo.setUid(uId);
			userinfo.setUname(userName);
			userinfo.setUpwd(userPWD);
			users.add(userinfo);
		}
		db.close();
		return users;
	}
}
