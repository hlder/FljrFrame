package com.hld.library.frame.fragment;

import java.util.HashMap;
import java.util.Stack;

import com.hld.library.frame.R;
import com.hld.library.frame.event.EventConfig;
import com.hld.library.frame.event.EventListener;
import com.hld.library.frame.event.EventUtils;
import com.hld.library.frame.utils.PxDpUtils;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 使用说明:在oncreate�?要setParamInt，设置存放fragment的layout
 * @author hld
 */
@SuppressLint("InflateParams") 
public class FragmentActivityFrame extends android.support.v4.app.FragmentActivity implements EventListener,TitleMothd{
	private FragmentManager manager;
	private int paramInt;
	private Stack<FragmentFrame> stack;
	private Stack<Class<?>> singles;
	
	private RelativeLayout baseLayout;
	private RelativeLayout titleLayout;
	private View titleView;

	private boolean isShowTitle=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager=getSupportFragmentManager();
		EventUtils.registOne(this);
		stack=new Stack<FragmentFrame>();
		singles=new Stack<Class<?>>();

		initTitleViews();
		
	}
	/**
	 * 设置装fragment的layout
	 * @param view
	 */
	public void setParamId(int paramInt) {
		this.paramInt = paramInt;
	}
	/**
	 * 启动fragment
	 */
	public void startFragment(Class<?> cls){
		startFragment(null, cls);
	}
	public boolean isTitle() {
		return isShowTitle;
	}
	/**
	 * 启动fragment
	 * @param b �?要传给fragment的内�?
	 */
	public void startFragment(Bundle b,Class<?> cls) {
		if(cls==null)return;
		try {
			if(stack.size()>0){
				FragmentFrame lf = stack.peek();
				manager.beginTransaction().hide(lf).commitAllowingStateLoss();
			}
			
			if(singles.search(cls)!=-1){
				for (int i = 0; i < stack.size(); i++) {
					if(cls.equals(stack.get(i).getClass())){
						FragmentFrame fl=stack.get(i);
						stack.remove(fl);
						stack.push(fl);
						fl.setBundle(b);
						manager.beginTransaction().show(fl).commitAllowingStateLoss();
						return;
					}
				}
			}
			FragmentFrame f=(FragmentFrame) cls.newInstance();
			if(f.registStartMode()==FragmentFrame.singleTask){
				singles.add(cls);
			}
			f.setBundle(b);
			manager.beginTransaction().add(paramInt, f).commitAllowingStateLoss();
			stack.push(f);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 执行返回操作,会移除掉栈顶的fragment
	 */
	public void doBack(){
		if(stack!=null&&stack.size()>0){
			removeFragement(stack.pop());
		}else{
			this.finish();
		}
	}
	/**
	 * 移除掉fragment
	 */
	public void removeFragement(FragmentFrame f) {
		FragmentTransaction t = manager.beginTransaction();
		t.remove(f);
		t.commitAllowingStateLoss();
		f.onFragmentDestroy();
		if(stack.size()>0){
			FragmentFrame lf = stack.peek();
			singles.remove(lf.getClass());
			manager.beginTransaction().show(lf).commitAllowingStateLoss();
		}else{
			this.finish();
		}
	}
	/**
	 * 接收到发送的消息
	 */
	@Override
	public void onEventReceive(Message msg) {
		if(msg==null||msg.arg2!=this.getClass().hashCode()){
			return;
		}
		switch (msg.arg1) {
		case EventConfig.EVENT_FINISH_FRAGMENT:
			doBack();
			break;
		case EventConfig.EVENT_START_FRAGMENT:
			@SuppressWarnings("unchecked")
			HashMap<String, Object> hm=(HashMap<String, Object>) msg.obj;
			Object b = hm.get(EventConfig.KEY_VALUE_BUNDLE);
			Object c = hm.get(EventConfig.KEY_CLASS_NAME);
			if(b==null){
				startFragment((Class<?>)c);
			}else{
				startFragment((Bundle)b,(Class<?>)c);
			}
			break;
		case EventConfig.EVENT_CHANAGE_TITLE:
			if(msg.obj!=null){
				TitleBean bean=(TitleBean) msg.obj;
				setTitle(bean.getTitle());
				if(bean.isBack())
					showTitleBackButton();
				else
					disShowTitleBackButton();
				setTitleLeftView(bean.getLeftView());
				setTitleRightView(bean.getRightView());
			}
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		/**
		 * �?毁event单列对象，和资源
		 */
//		EventUtils.destroySingtonEvent();
		EventUtils.unRegistOne(this);
		stack.clear();
		singles.clear();
	}
	private ImageView imgBack;
	/**
	 * 弃用系统自带的actionbar，加载title
	 */
	private void initTitleViews() {
		baseLayout=new RelativeLayout(this);
		ViewGroup.LayoutParams baseParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		baseLayout.setLayoutParams(baseParams);
		titleLayout=new RelativeLayout(this);
		RelativeLayout.LayoutParams titleParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, PxDpUtils.dipToPx(this, Constants.ACTIONBAR_TITLE_HEIGHT));
		titleLayout.setLayoutParams(titleParams);
		titleLayout.setId(Constants.ACTIONBAR_TITLE_ID);
		titleView=getLayoutInflater().inflate(R.layout.view_title, null);
		titleView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
		titleLayout.addView(titleView);
		baseLayout.addView(titleLayout);
		imgBack=(ImageView) titleLayout.findViewById(R.id.titleLeftImageView);
			imgBack.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					doBack();
				}
			});
	}
	
	public void setTitleBackImage(int resId){
		imgBack.setImageResource(resId);
	}
	public void setTitleBackImage(Bitmap bm){
		imgBack.setImageBitmap(bm);
	}
	
	
	@Override
	public void setTitle(CharSequence title) {
		TextView titleTextView=(TextView) titleLayout.findViewById(R.id.titleTextView);
		titleTextView.setText(title);
	}
	@Override
	public void setTitle(int titleId) {
		setTitle(""+getString(titleId));
	}
	@Override
	public void setTitleColor(int textColor) {
		TextView titleTextView=(TextView) titleLayout.findViewById(R.id.titleTextView);
		titleTextView.setTextColor(textColor);
	}
	public void setTitleTextSize(float size) {
		((TextView) titleLayout.findViewById(R.id.titleTextView)).setTextSize(size);
	}
	
	public void setTitleView(View view) {
		titleLayout.removeAllViews();
		titleLayout.addView(view);
	}
	
	public void setTitleBackground(int resId){
		titleLayout.setBackgroundResource(resId);
	}
	public void setTitleBackgroundColor(int color){
		titleLayout.setBackgroundColor(color);
	}
	@SuppressWarnings("deprecation")
	public void setTitleBackground(Drawable background){
		titleLayout.setBackgroundDrawable(background);
	}
	
	public void setTitleLeftView(View view) {
			setTitleViewBase(view, R.id.titleLeftLayout);
	}
	public void setTitleRightView(View view){
			setTitleViewBase(view, R.id.titleRightLayout);
	}
	private void setTitleViewBase(View view,int id){
		RelativeLayout titleView=(RelativeLayout) titleLayout.findViewById(id);
		titleView.removeAllViews();
		if(view!=null){
			titleView.addView(view);
		}
	}
	
	public void showTitleBackButton(){
		titleLayout.findViewById(R.id.titleLeftImageView).setVisibility(View.VISIBLE);
	}
	public void disShowTitleBackButton(){
		titleLayout.findViewById(R.id.titleLeftImageView).setVisibility(View.INVISIBLE);
	}
	
	public void setNoTitle() {
		isShowTitle=false;
	}
	
	@Override
	public void setContentView(int layoutResID) {
		setContentView(getLayoutInflater().inflate(layoutResID, null));
	}
	@Override
	public void setContentView(View view) {
		if(isShowTitle){
			try{
				requestWindowFeature(Window.FEATURE_NO_TITLE);
			}catch(Exception e){
				e.printStackTrace();
			}
			RelativeLayout.LayoutParams contentParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			contentParams.addRule(RelativeLayout.BELOW, Constants.ACTIONBAR_TITLE_ID);
			setContentView(view, contentParams);
		}else{
			super.setContentView(view);
		}
	}
	@Override
	public void setContentView(View view, LayoutParams params) {
		view.setLayoutParams(params);
		baseLayout.addView(view);
		super.setContentView(baseLayout, params);
	}
	
	
}