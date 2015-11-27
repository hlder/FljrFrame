package com.hld.library.frame.event;

import android.os.Message;

public interface EventListener {
	void onEventReceive(Message msg);
}