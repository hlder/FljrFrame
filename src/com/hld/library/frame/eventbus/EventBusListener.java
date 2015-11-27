package com.hld.library.frame.eventbus;

public interface EventBusListener {
	void onEvent(String action,Object obj);
}
