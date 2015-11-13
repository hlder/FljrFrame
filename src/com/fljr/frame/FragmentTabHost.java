package com.fljr.frame;

import android.content.Context;
import android.util.AttributeSet;

import com.fljr.frame.fragment.FragmentTabHostFrame;

/**
 * Tabhost<br/>
 * 使用方法:<br/>
 * host=(FragmentTabHost) findViewById(R.id.tabHost);<br/>
 * host.setTextSelectedColor(Color.RED);<br/>
 * host.addTab(host.newTabSpec().setIndicator(getString(R.string.tab_category)), new TabRecommendFragment());<br/>
 * host.addTab(host.newTabSpec().setIndicator(getString(R.string.tab_software)), new TabSoftwareFragment());<br/>
 * host.addTab(host.newTabSpec().setIndicator(getString(R.string.tab_game)), new TabGameFragment());<br/>
 * host.addTab(host.newTabSpec().setIndicator(getString(R.string.tab_manage)), new TabManageFragment());<br/>
 * @author hld
 */
public class FragmentTabHost extends FragmentTabHostFrame{
	public FragmentTabHost(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public FragmentTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public FragmentTabHost(Context context) {
		super(context);
	}
}