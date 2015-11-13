package com.fljr.frame.eventbus;

public interface EventBusListener {
	void onEvent(String action,Object obj);
}
