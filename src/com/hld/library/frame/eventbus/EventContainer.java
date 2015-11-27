package com.hld.library.frame.eventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * event存储器
 * @author hld
 */
public class EventContainer {

	private Map<String, List<EventBusListener>> map;
	
	public EventContainer() {
		map=new HashMap<String, List<EventBusListener>>();
	}
	
	/**
	 * 想存储器添加listener对象
	 * @param action 
	 * @param listener
	 */
	public void addListener(String action , EventBusListener listener) {
		List<EventBusListener> list= map.get(action);
		if(list==null){
			list=new ArrayList<EventBusListener>();
			map.put(action, list);
		}
		int index=list.indexOf(listener);
		if(index==-1){//表示不存在
			list.add(listener);
			removeOnAllAction(listener);
		}else{//表示已经注册过了
		}
	}
	
	/**
	 * 当addlistener的时候需要查看allAction中是否有次listener如果有则删除,否则会发送两次消息<br/>
	 * 一般情况不会注册两次(以防万一)
	 */
	private void removeOnAllAction(EventBusListener listener) {
		List<EventBusListener> list = map.get(EventBusConfig.AllAction);
		if(list==null){
			return;
		}
		int index=list.indexOf(listener);
		if(index!=-1){
			list.remove(index);
		}
	}
	/**
	 * 添加
	 * @param listener
	 */
	public void addListener(EventBusListener listener) {
		List<EventBusListener> list= map.get(EventBusConfig.AllAction);
		if(list==null){
			list=new ArrayList<EventBusListener>();
			map.put(EventBusConfig.AllAction, list);
		}
		int index=list.indexOf(listener);
		if(index==-1){//表示不存在
			list.add(listener);
		}else{//表示已经注册过了
		}
	}
	
	
	
	/**
	 * 移除存储器中的listener对象
	 * @param listener 需要移除的listener对象
	 */
	public void removeListener(EventBusListener listener){
		Set<String> set=map.keySet();
		/*遍历获取list对象进行删除操作*/
		for(String str:set){
			List<EventBusListener> list=map.get(str);
			removeAllInList(list, listener);//删除列表中的listener
		}
	}
	
	/**
	 * 使用递归法，清楚list里面所有次listener，防止意外多添加了同一个listener
	 * @param list 对list进行删除操作
	 * @param ls 需要删除的对象
	 */
	private void removeAllInList(List<EventBusListener> list,EventBusListener ls) {
		int index=list.indexOf(ls);
		if(index!=-1){
			list.remove(index);
			removeAllInList(list, ls);
		}
	}
	
	/**
	 * 清空所有监听器
	 */
	public void clear() {
		map.clear();
	}
	
	/**
	 * 获取关于此action所有的listener的集合
	 * @param action
	 * @return
	 */
	public List<EventBusListener> getListeners(String action){
		return map.get(action);
	}
}