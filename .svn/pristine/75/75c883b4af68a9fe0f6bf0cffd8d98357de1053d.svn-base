package com.hld.library.frame.fragment;

import java.util.HashMap;

import com.hld.library.frame.event.EventConfig;
import com.hld.library.frame.event.EventUtils;


import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
/**
 * fragment框架<br/>
 * 可以将程序全部使用fragment<br/>
 * @author HLD
 *
 */
public class FragmentFrame extends android.support.v4.app.Fragment implements EventConfig,TitleMothd{
	private Bundle b;
	private View contentView;
	private TitleBean titleBean;
	
	public Bundle getBundle() {
		return b;
	}
	public void setBundle(Bundle b) {
		this.b=b;
	}
	public View inflateView(int resource) {
		return inflateView(resource,getActivity().getLayoutInflater());
	}
	public View inflateView(int resource,LayoutInflater inflater) {
		contentView= inflater.inflate(resource, null);
		contentView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return contentView;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(!hidden){
			onReShow();
			chanageTitle();
		}
	}
	/**
	 * 在展示此fragment的时候会调用这个生命周期方法
	 */
	public void onReShow() {
		
	}
	
	public void setParamId(int resId) {
		if(getActivity() instanceof FragmentActivityFrame){
			((FragmentActivityFrame)getActivity()).setParamId(resId);
		}
	}
	
	public TitleBean getTitleBean() {
		if(titleBean==null){
			titleBean=new TitleBean();
		}
		return titleBean;
	}
	public void disShowTitleBackButton(){
		getTitleBean().setBack(false);
		chanageTitle();
	}
	public void showTitleBackButton() {
		getTitleBean().setBack(true);
		chanageTitle();
	}
	public void setTitle(CharSequence title){
		getTitleBean().setTitle(title);
		chanageTitle();
	}
	public void setTitleColor(int color) {
		getTitleBean().setTextColor(color);
		chanageTitle();
	}
	public void setTitleLeftView(View leftView){
		getTitleBean().setLeftView(leftView);
		chanageTitle();
	}
	public void setTitleRightView(View rightView) {
		getTitleBean().setRightView(rightView);
		chanageTitle();
	}
	
	/**
	 * 修改title
	 */
	public void chanageTitle() {
		Message msg=new Message();
		msg.arg1=EventConfig.EVENT_CHANAGE_TITLE;
		msg.arg2=this.getActivity().getClass().hashCode();
		msg.obj=titleBean;
		EventUtils.sendOne(msg);
	}
	
	
	
	/**
	 * 关闭这个fragment
	 */
	public void finish() {
		Message msg=new Message();
		msg.arg1=EventConfig.EVENT_FINISH_FRAGMENT;
		msg.arg2=this.getActivity().getClass().hashCode();
		msg.obj=this;
		EventUtils.sendOne(msg);
	}
	/**
	 * 发�?�消息给主activity,在主activity中重写onEventReceive方法便可以间听到发�?�的消息
	 * @param msg
	 */
	public void sendEventToActivity(Message msg) {
		EventUtils.sendOne(msg);
	}
	
	/**
	 * fragment启动模式，singleTask和standard两种启动模式
	 * @return
	 */
	public int registStartMode() {
		return standard;
	}
	/**
	 * 启动fragment
	 * @param cls
	 */
	public void startFragment(Class<?> cls){
		startFragment(null, cls);
	}
	public void startFragment(Bundle b,Class<?> cls) {
		HashMap<String, Object> hm=new HashMap<String, Object>();
		hm.put(EventConfig.KEY_CLASS_NAME, cls);
		hm.put(EventConfig.KEY_VALUE_BUNDLE, b);
		Message msg=new Message();
		msg.arg1=EventConfig.EVENT_START_FRAGMENT;
		msg.arg2=this.getActivity().getClass().hashCode();
		msg.obj=hm;
		EventUtils.sendOne(msg);
	}
	public void onFragmentDestroy() {
	}
}