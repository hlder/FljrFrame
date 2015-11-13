package com.fljr.frame;

import android.content.Context;

import com.fljr.frame.db.DbConfig;
import com.fljr.frame.db.DbUtils;

/**
 * 数据库处理类采用finalDb<br/>
 * @author User
 */
public class DBmanager{
	/**
	 * 生成dbtuils
	 * @param context
	 * @param dbName
	 * @return
	 */
	public static DbUtils create(Context context, String dbName){
		return DbConfig.create(context, dbName);
	}
}