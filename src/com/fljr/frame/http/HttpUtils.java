package com.fljr.frame.http;

import java.io.File;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.protocol.HttpContext;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.tsz.afinal.http.HttpHandler;

public class HttpUtils{
	FinalHttp http;
	public HttpUtils() {
		http=new FinalHttp();
	}
	
	public void addHeader(String header, String value) {
		http.addHeader(header, value);
	}
	public void configCharset(String charSet) {
		http.configCharset(charSet);
	}
	
	
	public void configCookieStore(CookieStore cookieStore) {
		
		http.configCookieStore(cookieStore);
	}
	
	public void configRequestExecutionRetryCount(int count) {
		
		http.configRequestExecutionRetryCount(count);
	}
	
	public void configSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
		
		http.configSSLSocketFactory(sslSocketFactory);
	}
	
	public void configTimeout(int timeout) {
		
		http.configTimeout(timeout);
	}
	
	public void configUserAgent(String userAgent) {
		
		http.configUserAgent(userAgent);
	}
	
	public void delete(String url, AjaxCallBack<? extends Object> callBack) {
		
		http.delete(url, callBack);
	}
	
	public void delete(String url, Header[] headers,
			AjaxCallBack<? extends Object> callBack) {
		
		http.delete(url, headers, callBack);
	}
	
	public Object deleteSync(String url, Header[] headers) {
		
		return http.deleteSync(url, headers);
	}
	
	public Object deleteSync(String url) {
		
		return http.deleteSync(url);
	}
	
	public HttpHandler<File> download(String url, AjaxParams params,
			String target, AjaxCallBack<File> callback) {
		
		return http.download(url, params, target, callback);
	}
	
	public HttpHandler<File> download(String url, AjaxParams params,
			String target, boolean isResume, AjaxCallBack<File> callback) {
		
		return http.download(url, params, target, isResume, callback);
	}
	
	public HttpHandler<File> download(String url, String target,
			AjaxCallBack<File> callback) {
		
		return http.download(url, target, callback);
	}
	
	public HttpHandler<File> download(String url, String target,
			boolean isResume, AjaxCallBack<File> callback) {
		
		return http.download(url, target, isResume, callback);
	}
	
	public void put(String url, AjaxCallBack<? extends Object> callBack) {
		
		http.put(url, callBack);
	}
	
	public void put(String url, AjaxParams params,
			AjaxCallBack<? extends Object> callBack) {
		
		http.put(url, params, callBack);
	}
	
	public void put(String url, Header[] headers, HttpEntity entity,
			String contentType, AjaxCallBack<? extends Object> callBack) {
		
		http.put(url, headers, entity, contentType, callBack);
	}
	
	public void put(String url, HttpEntity entity, String contentType,
			AjaxCallBack<? extends Object> callBack) {
		
		http.put(url, entity, contentType, callBack);
	}
	
	public Object putSync(String url, AjaxParams params) {
		
		return http.putSync(url, params);
	}
	
	public Object putSync(String url, Header[] headers, HttpEntity entity,
			String contentType) {
		
		return http.putSync(url, headers, entity, contentType);
	}
	
	public Object putSync(String url, HttpEntity entity, String contentType) {
		
		return http.putSync(url, entity, contentType);
	}
	
	public Object putSync(String url) {
		
		return http.putSync(url);
	}
	
	public void post(String url, AjaxCallBack<? extends Object> callBack) {
		http.post(url, callBack);
	}
	
	public void post(String url, AjaxParams params,
			AjaxCallBack<? extends Object> callBack) {
		http.post(url, params, callBack);
	}
	
	public <T> void post(String url, Header[] headers, AjaxParams params,
			String contentType, AjaxCallBack<T> callBack) {
		http.post(url, headers, params, contentType, callBack);
	}
	
	public void post(String url, Header[] headers, HttpEntity entity,
			String contentType, AjaxCallBack<? extends Object> callBack) {
		http.post(url, headers, entity, contentType, callBack);
	}
	
	public void post(String url, HttpEntity entity, String contentType,
			AjaxCallBack<? extends Object> callBack) {
		http.post(url, entity, contentType, callBack);
	}
	
	public Object postSync(String url) {
		return http.postSync(url);
	}
	
	public Object postSync(String url, AjaxParams params) {
		return http.postSync(url, params);
	}
	
	public Object postSync(String url, Header[] headers, AjaxParams params,
			String contentType) {
		return http.postSync(url, headers, params, contentType);
	}
	
	public Object postSync(String url, Header[] headers, HttpEntity entity,
			String contentType) {
		return http.postSync(url, headers, entity, contentType);
	}
	
	public Object postSync(String url, HttpEntity entity, String contentType) {
		return http.postSync(url, entity, contentType);
	}
	
	public void get(String url, AjaxCallBack<? extends Object> callBack) {
		http.get(url, callBack);
	}
	
	public void get(String url, AjaxParams params,
			AjaxCallBack<? extends Object> callBack) {
		http.get(url, params, callBack);
	}
	
	public void get(String url, Header[] headers, AjaxParams params,
			AjaxCallBack<? extends Object> callBack) {
		http.get(url, headers, params, callBack);
	}
	
	public HttpClient getHttpClient() {
		return http.getHttpClient();
	}
	
	public HttpContext getHttpContext() {
		return http.getHttpContext();
	}
	
	public Object getSync(String url) {
		return http.getSync(url);
	}
	
	public Object getSync(String url, AjaxParams params) {
		return http.getSync(url, params);
	}
	
	public Object getSync(String url, Header[] headers, AjaxParams params) {
		return http.getSync(url, headers, params);
	}
}