package com.hld.library.frame.utils;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class OnDialogItemClickListener implements OnClickListener{
	OnItemSelectedListener onItemSelectedListener;
	private int i=0;
	public OnDialogItemClickListener(OnItemSelectedListener onItemSelectedListener,int i) {
		this.onItemSelectedListener=onItemSelectedListener;
		this.i=i;
	}
	@Override
	public void onClick(View v) {
		if(onItemSelectedListener!=null){
			onItemSelectedListener.onItemSelected(null, v, i, 0);
		}
	}

}
