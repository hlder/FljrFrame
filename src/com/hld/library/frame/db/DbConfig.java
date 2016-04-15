package com.hld.library.frame.db;

import net.tsz.afinal.FinalDb.DbUpdateListener;
import android.content.Context;

public class DbConfig{
	public static DbUtils create(Context context, String dbName){
		return DbUtils.create(context, dbName);
	}
	public static DbUtils create(Context context,String targetDirectory, String dbName,boolean isDebug,int dbVersion,DbUpdateListener dbUpdateListener) {
		return DbUtils.create(context, targetDirectory, dbName, isDebug, dbVersion, dbUpdateListener);
	}
	public static DbUtils create(Context context, String dbName,boolean isDebug,int dbVersion,DbUpdateListener dbUpdateListener) {
		return DbUtils.create(context, dbName, isDebug, dbVersion, dbUpdateListener);
	}
	public static DbUtils create(Context context, String dbName,boolean isDebug) {
		return DbUtils.create(context, dbName, isDebug);
	}
	public static DbUtils create(Context context,String targetDirectory, String dbName) {
		return DbUtils.create(context, targetDirectory, dbName);
	}
	public static DbUtils create(Context context,String targetDirectory, String dbName,boolean isDebug) {
		return DbUtils.create(context, targetDirectory, dbName, isDebug);
	}
}