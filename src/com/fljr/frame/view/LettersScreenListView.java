package com.fljr.frame.view;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

/**
 * 字母筛选的listView
 * @author hld
 */
@SuppressLint({ "NewApi", "DefaultLocale", "ClickableViewAccessibility" })
public class LettersScreenListView extends RelativeLayout{
	private int padding=10;
	private ExpandableListView listView;
	private String letters[]={"↑","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","#"};
	/**右侧字母筛选的view*/
	private LinearLayout rightLayout=null;
	/** 记录字母和groupId对应的值 */
	private Map<String, Integer> map=new HashMap<String, Integer>();
	private TextView showLetterView;
	private int rightLetterColor=Color.WHITE;
	
	/**设置提示字母的背景颜色*/
	public void setShowLetterViewBackgroundColor(int color) {
		showLetterView.setBackgroundColor(color);
	}
	public void setShowLetterViewBackgroundResource(int resid) {
		showLetterView.setBackgroundResource(resid);
	}
	/**设置提示字母的颜色*/
	public void setShowLetterViewTextColor(int color) {
		showLetterView.setTextColor(color);
	}
	public void setChildDivider(Drawable childDivider) {
		listView.setChildDivider(childDivider);
	}
	public void setChildIndicator(Drawable childIndicator) {
		listView.setChildIndicator(childIndicator);
	}
	public void setChildIndicatorBounds(int left,int right) {
		listView.setChildIndicatorBounds(left, right);
	}
	public void setDivider(Drawable divider) {
		listView.setDivider(divider);
	}
	public void setDividerHeight(int height) {
		listView.setDividerHeight(height);
	}
	public void setGroupIndicator(Drawable groupIndicator) {
		listView.setGroupIndicator(groupIndicator);
		
	}
	public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
		listView.setOnChildClickListener(onChildClickListener);
	}
	
	public void setShowLetterViewTextSize(float size){
		showLetterView.setTextSize(size);
	}
	public void setShowLetterViewPadding(int l,int t,int r,int b){
		showLetterView.setPadding(l, t, r, b);
	}
	/**
	 * 动态设置提示字母view的长宽
	 * @param w
	 * @param h
	 */
	public void setShowLetterViewSize(int w,int h){
		ViewGroup.LayoutParams lp=showLetterView.getLayoutParams();
		lp.width=w;
		lp.height=h;
		showLetterView.setLayoutParams(lp);
//		showLetterView.setWidth(w);
//		showLetterView.setHeight(h);
	}
	/**设置右边字母筛选栏的背景颜色*/
	public void setRightLetterBackgroundColor(int color){
		this.rightLetterColor=color;
	}
	
	/**初始化*/
	private void init() {
		showLetterView=new TextView(getContext());
		RelativeLayout showLetterLayout=new RelativeLayout(getContext());
		listView=new ExpandableListView(getContext());//listView
		listView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
		showLetterLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(50,50);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		showLetterView.setLayoutParams(lp);
		showLetterView.setGravity(Gravity.CENTER);
		
		showLetterLayout.setGravity(Gravity.CENTER);
		showLetterLayout.addView(listView);
		showLetterLayout.addView(showLetterView);
		showLetterView.setBackgroundColor(Color.RED);
		addView(showLetterLayout);
		rightSelectView();
		
		showLetterView.setVisibility(View.GONE);
	}
	/**初始化右边字母筛选view*/
	private void rightSelectView() {
		rightLayout=new LinearLayout(getContext());
		rightLayout.setPadding(padding, padding, padding, padding);
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rightLayout.setLayoutParams(lp);
		rightLayout.setOrientation(LinearLayout.VERTICAL);
		rightLayout.setGravity(Gravity.CENTER);
		for (int i = 0; i <letters.length; i++) {
			TextView letterView=new TextView(getContext());
			letterView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
			letterView.setGravity(Gravity.CENTER);
			letterView.setText(letters[i]);
			rightLayout.addView(letterView);
		}
		addView(rightLayout);
		
		rightLayout.setOnTouchListener(touchListener);
	}
	
	/**设置listView的adapter*/
	public void setAdapter(LettersScreenAdapter adapter) {
		listView.setAdapter(adapter);
		int count=adapter.getGroupCount();
		Log.d("dddd", "adapterCount:"+count);
		for (int i = 0; i < count; i++) {
			Log.d("dddd", ""+adapter.getGroupLetter(i));
			if(adapter.getGroupLetter(i)!=null){
				map.put(adapter.getGroupLetter(i).toUpperCase()+"", i);
			}
		}
		showAllGroup(adapter);
	}
	
	/**
	 * 展开所有的group
	 */
	private void showAllGroup(LettersScreenAdapter adapter) {
		for(int i = 0; i < adapter.getGroupCount(); i++){
			listView.expandGroup(i);
		}
	}
	/**右侧字母栏的touch事件*/
	private OnTouchListener touchListener =new OnTouchListener() {
		@SuppressWarnings("deprecation")
		@Override
		public boolean onTouch(View v, MotionEvent m) {
			switch (m.getAction()) {
			case MotionEvent.ACTION_DOWN:
				rightLayout.setBackgroundColor(rightLetterColor);
				showLetterView.setVisibility(View.VISIBLE);
				moveLetter(m.getY());
				break;
			case MotionEvent.ACTION_MOVE://滑动中
				moveLetter(m.getY());
				break;
			case MotionEvent.ACTION_UP:
				rightLayout.setBackgroundDrawable(null);
				showLetterView.setVisibility(View.GONE);
				break;
			case MotionEvent.ACTION_CANCEL:
				rightLayout.setBackgroundDrawable(null);
				showLetterView.setVisibility(View.GONE);
				break;
			}
			return true;
		}
	};
	private void moveLetter(float y) {
		float itemh=(float)rightLayout.getHeight()/letters.length;
		int item=(int) (y/itemh);
		if(item>=0&&item<letters.length){
			String letter=letters[item].toUpperCase();
			showLetterView.setText(letter);
			Log.d("dddd", "touch:"+y+"   "+letter+"    "+map.get(letter));
			if(map.get(letter)!=null){
				int p=map.get(letter);
				listView.setSelectedGroup(p);
			}
		}
	}
	/**
	 * 自定义ExpandableListAdapter接口
	 * @author hld
	 */
	public static abstract class LettersScreenAdapter extends BaseExpandableListAdapter{
		/**
		 * 记录每一个group返回的字母
		 * @param posion
		 * @return
		 */
		public abstract String getGroupLetter(int posion);
		@Override
		public abstract View getChildView(int p1, int p2, boolean flag, View v,ViewGroup vg) ;
		@Override
		public abstract int getChildrenCount(int posion) ;
		@Override
		public abstract int getGroupCount();
		@Override
		public abstract View getGroupView(int p, boolean flag, View v,ViewGroup vg);
		
		@Override
		public  Object getChild(int arg0, int arg1){
			return null;
		}
		@Override
		public Object getGroup(int arg0) {
			return null;
		}
		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}
		@Override
		public long getChildId(int arg0, int arg1) {
			return 0;
		}
		@Override
		public long getCombinedChildId(long arg0, long arg1) {
			return 0;
		}
		@Override
		public long getCombinedGroupId(long arg0) {
			return 0;
		}
		@Override
		public long getGroupId(int arg0) {
			return 0;
		}
		@Override
		public boolean hasStableIds() {
			return false;
		}
		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			return true;
		}
		@Override
		public boolean isEmpty() {
			return false;
		}
		@Override
		public void onGroupCollapsed(int arg0) {
		}
		@Override
		public void onGroupExpanded(int arg0) {
		}
		@Override
		public void registerDataSetObserver(DataSetObserver arg0) {
		}
		@Override
		public void unregisterDataSetObserver(DataSetObserver arg0) {
		}
	}
	
	public TextView getShowLetterView() {
		return showLetterView;
	}
	
	public LettersScreenListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	public LettersScreenListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public LettersScreenListView(Context context) {
		super(context);
		init();
	}
}