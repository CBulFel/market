/**
 * @auther mazhao
 * TODO
 *
 */
package com.mark.market.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mark.market.bean.User;

/**
 * @author mazhao
 * @describ sqlite数据库操作类
 */
public class DBHelper extends SQLiteOpenHelper {
	private static String DB_name = "market";

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DBHelper(Context context) {
		super(context, DB_name, null, 1);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE IF NOT EXISTS  "
				+ User.TB_USER
				+ "( _id INTEGER PRIMARY KEY,userId TEXT, userName TEXT, token TEXT,isDefault TEXT,userIcon BLOB)";
		db.execSQL(sql); // 创建表
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
