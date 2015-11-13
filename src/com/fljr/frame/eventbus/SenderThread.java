package com.fljr.frame.eventbus;

import java.util.List;

/**
 * 消息发送线程
 * @author hld
 */
public class SenderThread implements Runnable{
	private Object message;
	private List<EventBusListener> list;
	private EventBusListener listener;
	private String action;
	
	public SenderThread(String action,Object obj,List<EventBusListener> list) {
		this.action=action;
		this.message=obj;
		this.list=list;
	}
	public SenderThread(String action,Object obj,EventBusListener listener){
		this.action=action;
		this.message=obj;
		this.listener=listener;
	}
	
	@Override
	public void run() {
		if(list!=null){
			for(EventBusListener listener:list){
				onEvent(listener);
			}
		}
		onEvent(listener);
	}
	private void onEvent(EventBusListener listener) {
		if(listener!=null){
			listener.onEvent(action, message);
		}
	}
	
}
