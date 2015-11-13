package com.fljr.frame;

import java.io.File;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.tsz.afinal.http.HttpHandler;

import com.fljr.frame.http.HttpListener;
import com.fljr.frame.http.HttpUtils;

/**
 * http简单操作类(目前根据afinal进行封装操作,采用apache httpclient)<br/>
 * 参数说明:dopost或者doGet都需要传入id，通过id来判断是哪一个请求，在listener返回的时候会将id返回<br/>
 * 使用方法:<br/>
 * 1、执行http请求
 * HttpManager http=new HttpManager();<br/>
 * http.setHttpListener(listener);<br/>
 * http.doPost(xxx,xxx);<br/>
 * 2、上传文件<br/>
 * AjaxParams params = new AjaxParams();<br/>
 * params.put("username", "michael yang");<br/>
 * params.put("password", "123456");<br/>
 * params.put("email", "test@tsz.net");<br/>
 * params.put("profile_picture", new File("/mnt/sdcard/pic.jpg")); // 上传文件<br/>
 * params.put("profile_picture2", inputStream); // 上传数据流<br/>
 * params.put("profile_picture3", new ByteArrayInputStream(bytes)); // 提交字节流<br/>
 * HttpManager http = new HttpManager();<br/>
 * http.doPost(xxx,xxx);<br/>
 * 3、文件下载(支持断点续传)
 * httpUtils http = new httpUtils();  
 * //调用download方法开始下载
 * HttpHandler handler = http.download(id,"http://www.xxx.com/下载路径/xxx.apk", //这里是下载的路径
 * "/mnt/sdcard/testapk.apk", //这是保存到本地的路径
 * true//true:断点续传 false:不断点续传（全新下载）
 * )
 * //调用stop()方法停止下载
 * handler.stop();
 *
 * @author User
 */
@SuppressWarnings("unchecked")
public class HttpManager {
	@SuppressWarnings("rawtypes")
	private HttpListener listener;
	private HttpUtils http;
	public HttpManager() {
		http=new HttpUtils();
	}
	
	public void configTimeOut(int timeout) {
		http.configTimeout(timeout);
	}
	public void addHeader(String header, String value){
		http.addHeader(header, value);
	}
	
	
	public void configCharset(String charSet) {
		http.configCharset(charSet);
	}
	
	/**
	 * 文件下载
	 * @param url 下载地址
	 * @param filePath 存放路径xx/xx/xxxx.apk
	 * @param isResume 是否断点续传
	 */
	public HttpHandler<File> download(final int id,String url, String filePath, boolean isResume) {
		return http.download(url, filePath, isResume, new AjaxCallBack<File>() {
			public void onLoading(long count, long current) {
				HttpManager.this.onLoading(id,count, current);
			}
			public void onSuccess(File f) {
				HttpManager.this.onSuccess(id, f);
			}
		});
	}
	
	
	
	/**
	 * 执行post请求
	 * @param id 请求的id
	 * @param url 请求地址
	 */
	public void doPost(int id,String url){
		http.post(url, newAjaxCallBack(id));
	}
	/**
	 * 执行post请求
	 * @param id 请求的id
	 * @param params ajax请求参数
	 * @param url 请求地址
	 */
	public void doPost(int id,AjaxParams params,String url) {
		http.post(url, params, newAjaxCallBack(id));
	}
	/**
	 * 执行get请求
	 * @param id 请求的id
	 * @param url 请求地址
	 */
	public void doGet(int id,String url){
		http.get(url, newAjaxCallBack(id));
	}
	/**
	 * 执行get
	 * 请求
	 * @param id 请求的id
	 * @param params ajax请求参数
	 * @param url 请求地址
	 */
	public void doGet(int id,AjaxParams params,String url) {
		http.get(url, params, newAjaxCallBack(id));
	}
	
	
	
	
	
	/**
	 * 设置listener
	 * @param listener
	 */
	public void setHttpListener(HttpListener<?> listener) {
		this.listener=listener;
	}
	private AjaxCallBack<String> newAjaxCallBack(final int id){
		return new AjaxCallBack<String>(){
		    public void onLoading(long count, long current) { //每1秒钟自动被回调一次
		    	HttpManager.this.onLoading(id, count, current);
		    }
		    public void onSuccess(String t) {
		    	HttpManager.this.onSuccess(id,t);
		    }
		    public void onStart() {
		    	HttpManager.this.onStart(id);
		    }
		    @Override
		    public void onFailure(Throwable t, int errorNo, String strMsg) {
		    	super.onFailure(t, errorNo, strMsg);
				HttpManager.this.onFailure(id, t, strMsg);
		    }
		};
	}
	protected void onLoading(int id,long count, long current) {
		if(listener!=null){
			listener.onLoading(id, count, current);
		}
	}
	protected void onSuccess(int id,String t) {
		if(listener!=null){
			listener.onSuccess(id, t);
		}
	}
	protected void onSuccess(int id,File f) {
		if(listener!=null){
			listener.onSuccess(id, f);
		}
	}
	protected void onStart(int id) {
		if(listener!=null){
			listener.onStart(id);
		}
	}
	protected void onFailure(int id,Throwable t, String strMsg){
		if(listener!=null){
			listener.onFailure(id, t, strMsg);
		}
	}
}