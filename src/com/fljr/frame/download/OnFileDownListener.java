package com.fljr.frame.download;

public interface OnFileDownListener {
	/**
	 * 开始下载
	 * @param total
	 * @param current
	 * @param speed
	 * @param tag
	 */
	void onStart(long total, long current,double speed,int tag);
	
	/**
	 * 下载中
	 * @param total 文件大小
	 * @param current 下载进度
	 * @param speed 下载速度
	 * @param tag 标记
	 */
	void onLoading(long total, long current,double speed,int tag);
	/**
	 * 下载成功
	 */
	void onSuccess(Request request,int tag);
	/**
	 * 下载失败
	 */
	void onFailure(Exception error,String msg,int tag);
	/**
	 * 状态发生改变
	 */
	void onStatusChanage(int status,int tag);
}