package com.hld.library.frame.eventbus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventSender {
	private ExecutorService singleThreadPool=Executors.newSingleThreadExecutor();
	private ExecutorService cachedThreadPool=Executors.newCachedThreadPool();
	
	private EventContainer ec;
	public EventSender(EventContainer ec) {
		this.ec=ec;
	}
	
	/**
	 * 循序发送消息
	 * @param action
	 * @param obj
	 * @param list
	 */
	public void doSend(String action,Object obj,List<EventBusListener> list) {
		singleThreadPool.execute(new SenderThread(action,obj,mergeList(list)));
	}
	
	
	/**
	 * 同步发送event消息<br/>
	 * 多条线程同时启动发送event消息,不建议使用
	 */
	public void doSendSynch(String action,Object obj,List<EventBusListener> list){
		List<EventBusListener>	listAll=mergeList(list);
		for(EventBusListener listener:listAll){
			cachedThreadPool.execute(new SenderThread(action, obj, listener));
		}
	}
	
	/**
	 * 合并
	 * @param list
	 * @return
	 */
	public List<EventBusListener> mergeList(List<EventBusListener> list) {
		List<EventBusListener> listAll=new ArrayList<EventBusListener>();
		if(list!=null){
			listAll.addAll(list);
		}
		List<EventBusListener> allAction=ec.getListeners(EventBusConfig.AllAction);//如果有的action是属于allAction，则也会发送
		if(allAction!=null){
			listAll.addAll(allAction);
		}
		return listAll;
	}
	
	
}
