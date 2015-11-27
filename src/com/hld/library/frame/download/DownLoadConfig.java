package com.hld.library.frame.download;

public interface DownLoadConfig {
//	public static final String ERROR_MESSAGE_NET="网络错误,下载失败";
//	public static final String ERROR_MESSAGE_LOSE_DATA="数据丢失,下载失败";
//	public static final String ERROR_MESSAGE_EXIST="TAG已经存在";
	
	
	public static final int STATUS_DOWN_WAIT=1;//等待下载
	public static final int STATUS_DOWN_DOING=2;//正在下载
	public static final int STATUS_DOWN_PAUSE=3;//下载暂停
	public static final int STATUS_DOWN_FAILURE=4;//下载失败
	public static final int STATUS_DOWN_SUCCESS=5;//下载成功
	
	
	public static final String APP_UPDATE_FILEPATH="/download/app/update/";
	
}
