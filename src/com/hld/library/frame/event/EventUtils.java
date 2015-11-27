package com.hld.library.frame.event;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * 类似eventbus<br/>
 * @author HLD
 */
@SuppressLint("HandlerLeak")
public class EventUtils {
	private static EventUtils event;
	private HashMap<Class<?>,EventListener> listListener;
	
	private Handler handler;
	
	public EventUtils() {
		listListener=new HashMap<Class<?>,EventListener>();
		handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				((EventListener)listListener.get(listListener.keySet().toArray()[msg.arg2])).onEventReceive((Message) msg.obj);
//				((EventListener)listListener.keySet().toArray()[msg.arg2]).onEventReceive((Message) msg.obj);
			}
		};
	}
	
	
	
	
	/**
	 * @return
	 */
	public static EventUtils getOneEventUtils() {
		if(event==null){
			event=new EventUtils();
		}
		return event;
	}
	
	/**
	 */
	public static void sendOne(Message msg){
		getOneEventUtils().send(msg);
	}
	/**
	 * @param msg
	 */
	public void send(final Message msg) {
		new Thread(){
			public void run() {
				synchronized (EventUtils.this) {  
			        for (int i = 0; i < listListener.size(); i++) {
			        	Message msg2=new Message();
			        	msg2.arg2=i;
			        	msg2.obj=msg;
			        	handler.sendMessage(msg2);
					}
			    }  
			};
		}.start();
	}
	
	
	/**
	 */
	public static void registOne(EventListener listener){
		getOneEventUtils().regist(listener);
	}
	public static void unRegistOne(EventListener listener) {
		getOneEventUtils().unRegist(listener);
	}
	/**
	 * @param listener
	 */
	public void regist(EventListener listener) {
		listListener.put(listener.getClass(),listener);
	}
	public void unRegist(EventListener listener) {
		listListener.remove(listener);
	}
	/**
	 */
	public static void destroySingtonEvent() {
		if(event==null)return;
		event.listListener.clear();
		event=null;
	}
}