package com.fljr.frame.fragment;

import android.view.View;

public class TitleBean {
	private boolean isBack;
	private CharSequence title;
	private int textColor;
	private View rightView;
	private View leftView;
	
	public boolean isBack() {
		return isBack;
	}
	public void setBack(boolean isBack) {
		this.isBack = isBack;
	}
	public CharSequence getTitle() {
		return title;
	}
	public void setTitle(CharSequence title) {
		this.title = title;
	}
	public int getTextColor() {
		return textColor;
	}
	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}
	public View getRightView() {
		return rightView;
	}
	public void setRightView(View rightView) {
		this.rightView = rightView;
	}
	public View getLeftView() {
		return leftView;
	}
	public void setLeftView(View leftView) {
		this.leftView = leftView;
	}
}
