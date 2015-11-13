package com.fljr.frame.event;

import android.os.Message;

public interface EventListener {
	void onEventReceive(Message msg);
}