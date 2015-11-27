package com.hld.library.frame.http;

/**
 * 执行http的返回监听
 * @param <T>
 */
public interface HttpListener <T>{
	void onLoading(int id,long count, long current);
	void onSuccess(int id,T t);
	void onStart(int id);
	void onFailure(int id,Throwable t, String strMsg);
}