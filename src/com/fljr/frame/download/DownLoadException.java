package com.fljr.frame.download;

import com.fljr.frame.R;

import android.content.Context;
import android.util.Log;

public class DownLoadException extends Exception implements DownLoadConfig{
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	public DownLoadException(String message) {
		super(message);
		Log.e("error", ""+message);
	}
	
	public static String ERROR_MESSAGE_NET(Context ctx){
		return ctx.getString(R.string.library_download_error_net);
	}
	public static String ERROR_MESSAGE_LOSE_DATA(Context ctx){
		return ctx.getString(R.string.library_download_error_lose_data);
	}
	public static String ERROR_MESSAGE_EXIST(Context ctx){
		return ctx.getString(R.string.library_download_error_exist);
	}
	
	
	
}
