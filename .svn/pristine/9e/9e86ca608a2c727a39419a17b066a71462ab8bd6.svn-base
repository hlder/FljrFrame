package com.hld.library.frame.eventbus;

import java.util.List;

/**
 * @author hld
 */
public class EventBusUtils {
	private EventContainer ec;
	private EventSender es;
	
	
	public EventBusUtils() {
		ec=new EventContainer();
		es=new EventSender(ec);
	}
	
	/**
	 * 注册监听器
	 * 将listener注册进来
	 * @param action 参数值,一个listener可以注册好几个action
	 * @param listener 监听器
	 */
	public void register(EventBusListener listener,String...action) {
		for (int i = 0; i < action.length; i++) {
			ec.addListener(action[i], listener);
		}
	}
	/**
	 * 注册监听器，当不传入action时，则为接收所有的消息
	 * @param listener
	 */
	public void register(EventBusListener listener) {
		ec.addListener(listener);
	}
	
	/**
	 * 当activity finish的时候需要调用次方法  将listener从容器中移除
	 * @param listener 需要移除的listener
	 */
	public void remove(EventBusListener listener){
		ec.removeListener(listener);
	}

	/**
	 * 清空所有的监听器
	 */
	public void clear(){
		ec.clear();
	}
	
	/**
	 * 发送消息
	 * @param action 发送的action
	 * @param obj 发送的消息内容
	 */
	public void post(String action,Object obj) {
		List<EventBusListener> list=ec.getListeners(action);
		if(list!=null){
			es.doSend(action,obj,list);
		}
	}
	/**
	 * 同步发送消息
	 * @param action 发送的action
	 * @param obj 发送的消息内容
	 */
	public void postSynch(String action,Object obj) {
		List<EventBusListener> list=ec.getListeners(action);
		if(list!=null){
			es.doSendSynch(action,obj,list);
		}
	}
}