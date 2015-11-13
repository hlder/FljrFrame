package com.fljr.frame.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 类似于新闻头部的切换页<br/>
 * 分类界面展示<br/>
 * 使用方式<br/>
 * List<TabBean> list=new ArrayList<MyView.TabBean>();<br/>
 * TabBean bean=mv.newTabBean() ;<br/>
 * list.add(bean);<br/>
 * ScrollTabHost host=new ScrollTabHost(this);<br/>
 * host.setData(list);//设置数据<br/>
 * host.setFragment(XXX.class);//设置fragment的class,此fragment必须有构造函数public XXXFragment(TabBean){}<br/>
 * //host.setTextColor(XXX);//设置字体的颜色<br/>
 * //host.setSelectTextColor(XXX);<br/>
 * //......<br/>
 * @author HLD
 */
@SuppressLint("UseSparseArrays")
public class ScrollTabHost extends LinearLayout{
	private HorizontalScrollView scrollView; 
	
	private TitleLinearLayout titleLayout;
	private ViewPager viewPager;
	private List<TabBean> list;
	
	private MyPagerAdapter adapter;
	private int textSelectColor=Color.BLUE;
	private int textColor=Color.BLACK;

	private Bitmap tabSelectBack=null;
	private Paint tabLayoutPaint;

	private Class<?> fragment;

	private int padLeft=-1;
	private int padRight=-1;
	private int padTop=-1;
	private int padBottom=-1;

	private int index=0;
	private NinePatch np;
	
	public void setFragmentClass(Class<?> fragment){
		this.fragment=fragment;
	}
	
	/**
	 * 设置数据
	 * @param list
	 */
	public void setData(List<TabBean> list){
		this.list=list;
		titleLayout.removeAllViews();
		for (int i = 0; i < list.size(); i++) {
			TextView view=createTab(list.get(i).getLable(),i);
			if(i==0){
				view.setTextColor(textSelectColor);
			}
			titleLayout.addView(view);
		}
		if(adapter!=null){
			adapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * 设置选中的字体的颜色
	 * @param color
	 */
	public void setSelectTextColor(int color){
		this.textSelectColor=color;
		View tem = titleLayout.getChildAt(viewPager.getCurrentItem());
		if(tem!=null && tem instanceof TextView){
			((TextView)tem).setTextColor(textSelectColor);
		}
	}
	/**
	 * 设置字体的默认颜色
	 * @param color
	 */
	public void setTextColor(int color){
		this.textColor=color;
		for (int i = 0; i < titleLayout.getChildCount(); i++) {
			View tem = titleLayout.getChildAt(i);
			if(tem!=null && tem instanceof TextView){
				((TextView)tem).setTextColor(textColor);
			}
		}
	}
	/**
	 * 设置选中时的item背景颜色
	 * @param color
	 */
	public void setTabSelectBackgroundColor(int color){
		tabLayoutPaint.setColor(color);
		titleLayout.invalidate();
	}
	
	public void setTabSelectBackground(int resid){
		tabSelectBack=BitmapFactory.decodeResource(getResources(), resid);
		titleLayout.invalidate();
		np = new NinePatch(tabSelectBack, tabSelectBack.getNinePatchChunk(), null);  
	}
	public void setCurrentItem(int index){
		this.index=index;
		viewPager.setCurrentItem(index);
	}
	
	/**
	 * 设置tab的背景颜色
	 * @param color
	 */
	public void setTabBackgroundColor(int color){
		titleLayout.setBackgroundColor(color);
		scrollView.setBackgroundColor(color);
	}
	/**
	 * 设置tab的背景
	 */
	public void setTabBackground(int resid){
		titleLayout.setBackgroundResource(resid);
		scrollView.setBackgroundResource(resid);
	}
	/**
	 * 设置tab的背景
	 */
	@SuppressWarnings("deprecation")
	public void setTabBackground(Drawable drawable){
		titleLayout.setBackgroundDrawable(drawable);
		scrollView.setBackgroundDrawable(drawable);
	}
	
	
	public void setTabItemPadding(int padLeft,int padRight,int padTop,int padBottom){
		this.padLeft=padLeft;
		this.padRight=padRight;
		this.padTop=padTop;
		this.padBottom=padBottom;
		if(titleLayout.getChildCount()==0){
			return;
		}
		for (int i = 0; i < titleLayout.getChildCount(); i++) {
			TextView tv= (TextView) titleLayout.getChildAt(i);
			tv.setPadding(padLeft, padTop, padRight, padBottom);
		}
	}
	
	private void init() {
		tabLayoutPaint=new Paint();
		tabLayoutPaint.setColor(Color.GRAY);
		list=new ArrayList<ScrollTabHost.TabBean>();
		Context ctx=getContext();
		setOrientation(LinearLayout.VERTICAL);
		scrollView=new HorizontalScrollView(ctx);
		titleLayout=new TitleLinearLayout(ctx);
		scrollView.addView(titleLayout);
		scrollView.setHorizontalScrollBarEnabled(false);
		viewPager=new ViewPager(getContext());
		viewPager.setId(hashCode());
		addView(scrollView);
		addView(viewPager);
//		viewPager.setOffscreenPageLimit(index);
		viewPager.setCurrentItem(index);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				for (int i = 0; i < titleLayout.getChildCount(); i++) {
					View tem = titleLayout.getChildAt(i);
					if(tem!=null && tem instanceof TextView){
						if(i==arg0){
							((TextView)tem).setTextColor(textSelectColor);
						}else{
							((TextView)tem).setTextColor(textColor);
						}
					}
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				titleLayout.translate(arg0, arg1);
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				if(arg0==2){//表示滑动完成了
					int tem = scrollView.getScrollX();
					int w=0;
					int w2=0;
					int count=viewPager.getCurrentItem();
					for (int i = 0; i <= count; i++) {
						View v=titleLayout.getChildAt(i);
						if(v!=null){
							w+=v.getWidth();
							if(i<count){
								w2=w;
							}
						}
					}
					if((tem+scrollView.getWidth())<w){
						scrollView.scrollTo(w-scrollView.getWidth(),0);
					}else if(w2<tem){
						scrollView.scrollTo(w2,0);
					}
					
					Log.d("dddd", "onPageScrollStateChanged:"+(tem+scrollView.getWidth())+"*"+w+"   "+viewPager.getCurrentItem());
					
				}
			}
		});
		if(ctx instanceof FragmentActivity){
			adapter=new MyPagerAdapter(((FragmentActivity) ctx).getSupportFragmentManager());
			viewPager.setAdapter(adapter);
		}
	}
	
	private OnClickListener onTextViewClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Object tag=v.getTag();
			if(tag!=null&&tag instanceof Integer){
				int index=Integer.parseInt(tag.toString());
				viewPager.setCurrentItem(index);
			}
		}
	};
	
	
	private TextView createTab(String text,int index) {
		TextView tv=new TextView(getContext());
		tv.setTag(index);
		tv.setOnClickListener(onTextViewClick);
		if(padLeft!=-1&&padTop!=-1&&padRight!=-1&&padBottom!=-1)
			tv.setPadding(padLeft, padTop, padRight, padBottom);
		tv.setText(text);
		return tv;
	}
	
	private class MyPagerAdapter extends FragmentPagerAdapter{
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		@Override
		public int getCount() {
			if(list==null)
				return 0;
			return list.size();
		}
		@Override
		public Fragment getItem(int arg0) {
			if(fragment!=null){
				TabBean bean = list.get(arg0);
				try {
					Constructor<?> con=fragment.getConstructor(TabBean.class);
					return (Fragment) con.newInstance(bean);
				} catch (NoSuchMethodException e) {
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				} catch (IllegalArgumentException e) {
				} catch (InvocationTargetException e) {
				}
			}
			return null;
		}
	}
	
	private class TitleLinearLayout extends LinearLayout{
		float x1,x2;
		float base1,base2;
		Rect src=null;
		Rect rect=new Rect();
		public void translate(int index,float bl){
			View v = getChildAt(index);
			View v2 =getChildAt(index+1);
			if(v!=null)
				x1=v.getWidth()*bl;
			if(v2!=null){
				x2=v2.getWidth()*bl;
			}else{
				x2=0;
			}
			base1=0;
			base2=0;
			for (int i = 0; i < index+1; i++) {
				View tem=getChildAt(i);
				if(tem!=null){
					base2+=tem.getWidth();
				}
			}
			if(v!=null)
				base1=base2-v.getWidth();
			invalidate();
		}
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			if(tabSelectBack!=null){
				if(src==null){
					src=new Rect();
					src.left=0;
					src.top=0;
					src.right=tabSelectBack.getWidth();
					src.bottom=tabSelectBack.getHeight();
				}
				rect.left=(int) (base1+x1);
				rect.top=0;
				rect.right=(int) (base2+x2);
				rect.bottom=getHeight();
//				canvas.drawBitmap(tabSelectBack, src, rect, tabLayoutPaint);
//				np.draw(canvas, src);
				np.draw(canvas, rect);
			}else{
				canvas.drawRect(base1+x1,0,base2+x2,getHeight(),tabLayoutPaint);
			}
		}
		
		public TitleLinearLayout(Context context) {
			super(context);
			setWillNotDraw(false);
		}
	}
	
	
	
	
	
	public TabBean newTabBean(){
		return new TabBean();
	}
	
	public class TabBean{
		private String lable;
		private Object bean;
		public String getLable() {
			return lable;
		}
		public void setLable(String lable) {
			this.lable = lable;
		}
		public Object getBean() {
			return bean;
		}
		public void setBean(Object bean) {
			this.bean = bean;
		}
	}
	
	public ScrollTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public ScrollTabHost(Context context) {
		super(context);
		init();
	}
}