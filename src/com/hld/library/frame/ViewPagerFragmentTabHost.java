package com.hld.library.frame;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *  ViewPagerFragmentTabHost tabhost=new ViewPagerFragmentTabHost(this);<br/>
 * 	tabhost.setTabSelectBg(R.drawable.bg_tab_title_eff);<br/>
 * 	tabhost.setTabItemBg(R.drawable.bg_tab_title);<br/>
 * 	tabhost.setTabBgColor(getResources().getColor(R.color.bg_app));<br/>
 * 	tabhost.setTabTextColor(getResources().getColor(R.color.gray));<br/>
 * 	tabhost.setTabTextSize(18);<br/>
 * 	tabhost.setTabTextSelectColor(getResources().getColor(R.color.color_theme));<br/>
 * 	tabhost.addTab(tabhost.newTab().setFragment(new Tab1Fragment()).setText("tab1"));<br/>
 * 	tabhost.addTab(tabhost.newTab().setFragment(new Tab2Fragment()).setText("tab2"));<br/>
 * 	tabhost.setTabSelectBg(R.drawable.title_tab_select_bg);<br/>
 *	setContentView(bar.getView());<br/>
 *  @author  hld
 */
@SuppressLint({ "Recycle", "InlinedApi" })
public class ViewPagerFragmentTabHost implements OnClickListener,OnPageChangeListener{
	private FragmentActivity context;
	private LinearLayout baseLayout,titleTabLayout;
	private FragmentManager fragmentManager;
	private int tabTextColor=Color.BLACK;
	private int tabTextSelectColor=Color.GRAY;
	
	private int bg=android.R.drawable.title_bar;
	private int selectBg=android.R.drawable.title_bar_tall;
	private int textSize=0;
	
	
	private LinearLayout.LayoutParams weightParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
	
	private List<Tab> listTabs;
	
	private OnItemSelectListener onItemSelectListener;
	
	private ViewPager viewPager;
	
	private MyPagerAdapter adapter;
	
	public ViewPagerFragmentTabHost(FragmentActivity context) {
		this.context=context;
		fragmentManager=context.getSupportFragmentManager();
		adapter=new MyPagerAdapter(fragmentManager);
		listTabs=new ArrayList<ViewPagerFragmentTabHost.Tab>();
		baseLayout=new LinearLayout(context);
		titleTabLayout =new LinearLayout(context);
		viewPager=new ViewPager(context);
		
		baseLayout.setOrientation(LinearLayout.VERTICAL);
		baseLayout.addView(titleTabLayout);
		viewPager.setId(hashCode());
		baseLayout.addView(viewPager);
		
		titleTabLayout.setBackgroundColor(Color.WHITE);
		
		viewPager.setOnPageChangeListener(this);
		viewPager.setOffscreenPageLimit(0);
		viewPager.setAdapter(adapter);
	}
	/**
	 * @param limit
	 */
	public void setOffscreenPageLimit(int limit) {
		viewPager.setOffscreenPageLimit(limit);
	}
	public void setCurrentItem(int item){
		viewPager.setCurrentItem(item);
	}
	public void addTab(Tab v) {
		listTabs.add(v);
		v.setLayoutParams(weightParam);
		titleTabLayout.addView(v);
		
		adapter.notifyDataSetChanged();
		
		if(listTabs.size()==1){
			listTabs.get(0).setIsSelect(true);
		}
	}
	
	public Tab newTab(){
		Tab tab=new Tab(context);
		tab.setOnClickListener(this);
		return tab;
	}
	
	@Override
	public void onClick(View v) {
		if(v instanceof Tab){
			Tab tab=(Tab)v;
			viewPager.setCurrentItem(listTabs.indexOf(v),true);
			if(onItemSelectListener!=null){
				onItemSelectListener.onItemSelect(listTabs.indexOf(tab), tab);
			}
		}
	}
	
	/**
	 * @author hld
	 */
	public interface OnItemSelectListener{
		void onItemSelect(int p,Tab tab);
	}
	
	/**
	 * @author hld
	 *
	 */
	public class Tab extends LinearLayout{
		private Fragment fragment;
		private TextView textView;
		/**
		 * @param context
		 * @param attrs
		 */
		public Tab(Context context, AttributeSet attrs) {
			super(context, attrs);
			prepare(context);
		}
		/**
		 * @param context
		 */
		public Tab(Context context) {
			super(context);
			prepare(context);
			setBackgroundResource(bg);
		}
		private void prepare(Context context) {
			setGravity(Gravity.CENTER);
			textView=new TextView(context);
			textView.setPadding(0, 20, 0, 20);
			textView.setTextColor(tabTextColor);
			if(textSize>0){
				textView.setTextSize(textSize);
			}
			addView(textView);
		}
		/**
		 * @param fragment
		 * @return
		 */
		public Tab setFragment(Fragment fragment){
			this.fragment=fragment;
			return this;
		}
		/**
		 * @return
		 */
		public Fragment getFragment() {
			return fragment;
		}
		/**
		 * @param text
		 * @return
		 */
		public Tab setText(String text){
			textView.setText(""+text);
			return this;	
		}
		/**
		 */
		public void setIsSelect(boolean flag){
			if(flag) {
				setBackgroundResource(selectBg);
				textView.setTextColor(tabTextSelectColor);
			}else{ 
				setBackgroundResource(bg);
				textView.setTextColor(tabTextColor);
			}
		}
		
		
	}
	
	public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
		this.onItemSelectListener = onItemSelectListener;
	}
	
	
	/**
	 * @return
	 */
	public View getView() {
		return baseLayout;
	}

	
	
	
	private class MyPagerAdapter extends FragmentPagerAdapter{
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int p) {
			Tab t= listTabs.get(p);
			return t.getFragment();
		}

		@Override
		public int getCount() {
			return listTabs.size();
		}
	}
	
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
//		Log.d("dddd", "onPageScrolled:"+arg0+" "+arg1+" "+arg2);
	}
	@Override
	public void onPageSelected(int arg0) {
		if(onItemSelectListener!=null)
			onItemSelectListener.onItemSelect(arg0, listTabs.get(arg0));
		for (int i = 0; i < listTabs.size(); i++) {
			listTabs.get(i).setIsSelect(false);
		}
		listTabs.get(arg0).setIsSelect(true);
	}
	
	
	public void setTabTextSize(int size) {
		this.textSize= size;
	}
	/**
	 * @param tabTextColor
	 */
	public void setTabTextColor(int tabTextColor) {
		this.tabTextColor = tabTextColor;
	}
	/**
	 * @param tabTextSelectColor
	 */
	public void setTabTextSelectColor(int tabTextSelectColor) {
		this.tabTextSelectColor = tabTextSelectColor;
	}
	public void setTabSelectBg(int resId) {
		this.selectBg=resId;
	}
	public void setTabBgColor(int color){
		titleTabLayout.setBackgroundColor(color);
	}
	public void setTabBg(int resid){
		titleTabLayout.setBackgroundResource(resid);
	}
	public void setTabItemBg(int resId) {
		this.bg=resId;
	}
}
