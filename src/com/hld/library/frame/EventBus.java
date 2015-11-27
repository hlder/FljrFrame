package com.hld.library.frame;

import com.hld.library.frame.eventbus.EventBusListener;
import com.hld.library.frame.eventbus.EventBusUtils;

/**
 * eventbus使用类，单列模式！如果不想使用单列，可以自行创建eventBusUtils<br/>
 * 通过此方法可以进行组件间的通信<br/>
 * 注此方法无法夸进程通信，如果项目中有多个进程，请使用aidl<br/>
 * 使用方法：<br/>
 * class MainActivity extent Activity implements EventBusListener{<br/>
 * 		public void oncreate(){<br/>
 * 			setContentView(R.layout.main);<br/>
 * 			//注册event监听器<br/>
 * 			Eventbus.register(this,"login");<br/>
 * 			Login bean=new Login();<br/>
 * 			bean.setUsername("zhangsan");<br/>
 * 			bean.setPassword("MK3K94K35I3M2K1K232K");<br/>
 * 			EventBus.post("login",bean);<br/>
 * 		}
 * 		public void onEvent(String action,Object obj){<br/>
 * 			//这里便会接收到post发送的消息<br/>
 * 			if("login".equals(action)&&obj instanceof Login){<br/>
 * 				....<br/>
 * 			}<br/>
 * 		}<br/>
 * 		<br/>
 * 		//使用完成后要及时销毁，以免占用内存<br/>
 * 		public void onDestory(){<br/>
 * 			Eventbus.remove(this);<br/>
 * 		}<br/>
 * }<br/>
 * @author hld
 *
 */
public class EventBus {
	private static EventBusUtils event;
	
	private static synchronized EventBusUtils getInstance(){
		if(event==null){
			event=new EventBusUtils();
		}
		return event;
	}
	/**
	 * 注册监听器
	 * @param listener 监听器
	 * @param action 注册的action，数组
	 */
	public static void register(EventBusListener listener,String...action){
		getInstance().register(listener, action);
	}
	/**
	 * 注册监听器
	 * 当没有传入action的时候,默认是接收所有post的消息
	 * @param listener
	 */
	public static void register(EventBusListener listener){
		getInstance().register(listener);
	}
	
	/**
	 * 发送消息
	 * @param action
	 * @param message
	 */
	public static void post(String action,Object message){
		getInstance().post(action, message);
	}
	
	/**
	 * 在activity结束的时候，调用此方法，释放内存,否则内存会越来越大直到溢出
	 * @param listener 需要移除的listnerner
	 */
	public static void remove(EventBusListener listener){
		getInstance().remove(listener);
	}
	/**
	 * 清空所有的listener
	 */
	public static void clear(){
		getInstance().clear();
	}
}