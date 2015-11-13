package com.fljr.frame;

import android.content.Context;

import com.fljr.frame.download.DownLoadUtils;

/**
 * 文件下载管理<br/>
 * 支持多线程同步下载<br/>
 * 支持断点续传(传入文件路径的时候,会自动判断文件大小进行断点续传)<br/>
 * 使用：<br/>
 * DownLoadUtils utils=new DownLoadUtils();<br/>
 * utils.setOnFileDownListener(listener);//设置回调函数<br/>
 * utils.download(url,"sdcard/XXX.apk",3);//开始下载文件<br/>
 * utils.download(url2,"sdcard/XXX.apk",4);//开始下载文件2<br/>
 *  //utils.pause(3);//暂停<br/>
 *  //utils.resume(3);//继续下载<br/>
 * @author HLD
 */
public class Download extends DownLoadUtils{
	/**
	 * @param ctx
	 * @param poolSize 设置线程池的数量
	 */
	public Download(Context ctx, int poolSize) {
		super(ctx, poolSize);
	}
	public Download(Context ctx) {
		super(ctx);
	}
}