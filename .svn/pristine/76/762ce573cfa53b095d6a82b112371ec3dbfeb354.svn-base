package com.hld.library.frame.utils;

import com.hld.library.frame.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class DialogFactory {
	
	
	public static Dialog alertDialog(Context context,String msg,String sureButton,String calButton,final OnClickListener sureLisntener){
		final Dialog dialog=new Dialog(context,R.style.FrameDialogTheme);
		dialog.setCanceledOnTouchOutside(false);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_alert, null);
		dialog.addContentView(view,  new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		TextView message=(TextView) view.findViewById(R.id.message);
		TextView btnSure=(TextView) view.findViewById(R.id.btnSure);
		TextView btnCancle=(TextView) view.findViewById(R.id.btnCancle);
		
		message.setText(msg);
		btnSure.setText(sureButton);
		btnCancle.setText(calButton);
		
		btnSure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if(sureLisntener!=null){
					sureLisntener.onClick(v);
				}
			}
		});
		
		btnCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		return dialog;
	}
	public static Dialog alertDialog(Context context,String msg,OnClickListener sureLisntener){
		return alertDialog(context, msg, context.getString(R.string.frame_btn_sure), context.getString(R.string.frame_btn_cancle),sureLisntener);
	}
	
	
	
	
	/**
	 * 单选框
	 * @param context
	 * @param str
	 * @param onItemSelectedListener
	 * @return
	 */
	public static Dialog selectDialog(Context context,String [] str,OnItemSelectedListener onItemSelectedListener){
		Dialog dialog=new Dialog(context,R.style.FrameDialogTheme);
		dialog.setCanceledOnTouchOutside(false);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_select, null);
		dialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		LinearLayout layout=(LinearLayout) view.findViewById(R.id.layout);
		int dim=context.getResources().getDimensionPixelSize(R.dimen.dialog_padding_item);
		LinearLayout.LayoutParams lineLp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
		for (int i = 0; i < str.length; i++) {
			View line=new View(context);
			line.setBackgroundColor(Color.GRAY);
			line.setLayoutParams(lineLp);
			TextView tv=new TextView(context);
			tv.setPadding(dim, dim, dim, dim);
			tv.setBackgroundResource(R.drawable.item_click);
			tv.setText(str[i]);
			tv.setOnClickListener(new OnDialogItemClickListener(onItemSelectedListener, i));
			layout.addView(tv);
			if(i<str.length){
				layout.addView(line);
			}
		}
		return dialog;
	}
}