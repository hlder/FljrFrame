package com.fljr.frame.view;

import com.fljr.frame.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 下拉刷新  和上拉刷新控件
 * @author HLD
 */
@SuppressLint("ClickableViewAccessibility")
public class PullToRefreshView extends LinearLayout{
	private final int STATUS_REFRESH_NONE=0;//正常滑动
	private final int STATUS_REFRESH_TOP=1;//下拉刷新
	private final int STATUS_REFRESH_FOOTER=2;//上拉刷新
	private final String SHARE_NAME_REFRESHNAME="SHARE_NAME_REFRESHNAME";
	private final String shareName="librarys_jiuyi";
	
	public OnHeaderRefreshListener refreshListener;
	private Context con;
	
	private LinearLayout topRefreshView,footerRefreshView;
	
	private int moveStatus;
	private boolean animIsOver=false;
	
	private boolean refreshing=false;
	
	private boolean isCanBottomRefresh=false;

	private float downY=0;
	boolean flag;
	
	private ProgressBar progressBarTop,progressBarBottom;//等待框
	private ImageView imgArrawTop,imgArrawBottom;//拉的图标
	private TextView textRefreshTop,textRefreshTimeTop,textRefreshBottom;//下拉刷新的文字,上次更新时间的文字
	
	private boolean isAnimDoing=false;
	
	public PullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public PullToRefreshView(Context context) {
		super(context);
		init(context);
	}
	
	
	
	private void init(Context con){
		this.con=con;
		setOrientation(LinearLayout.VERTICAL);setWillNotDraw(false);
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		addTopRefreshView();
		addFooterRefreshView();
	}
	
	private boolean listViewStatus() {
		boolean flag=false;
		AdapterView<?> listView=null;
		for (int i = 0; i < getChildCount(); i++) {
			View v=getChildAt(i);
			if(v instanceof AdapterView<?>){
				listView=(AdapterView<?>) v;
			}
		}
		if(listView==null){//
			return false;
		}
		int frist=listView.getFirstVisiblePosition();
		int last=listView.getLastVisiblePosition();
		
		View top=listView.getChildAt(0);
		View bottom=listView.getChildAt(listView.getChildCount()-1);
		
		if(top!=null&&frist==0&&top.getTop()==0){//最顶端
			moveStatus=STATUS_REFRESH_TOP;
			flag=true;
		}else if(bottom!=null&&last>=(listView.getCount()-1)&&bottom.getBottom()==listView.getHeight()){//最底端
			moveStatus=STATUS_REFRESH_FOOTER;
			flag=true;
		}else{
			moveStatus=STATUS_REFRESH_NONE;
			flag=false;
		}
		return flag;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		flag=listViewStatus();
		if(flag){
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if(!isAnimDoing){
					initAnimNow();
				}
				downY=ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				float tem=ev.getY()-downY;
				switch (moveStatus) {
				case STATUS_REFRESH_TOP:
					if(tem>0){
						return true;
					}
					break;
				case STATUS_REFRESH_FOOTER:
					if(tem<0){
						return true;
					}
					break;
				case STATUS_REFRESH_NONE:
					break;
				}
				break;
			}
		}
		return super.onInterceptTouchEvent(ev);
	}
	private float lasty=0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(!refreshing&&flag){
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downY=event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				if(lasty==0){
					lasty=event.getY();
				}
				float tem=event.getY()-lasty;
				LinearLayout.LayoutParams lp = (LayoutParams) topRefreshView.getLayoutParams();
				float mar=lp.topMargin+tem/3;
				setTopMartgin(mar);
				lasty=event.getY();
				break;
			case MotionEvent.ACTION_CANCEL:
				animIsOver=true;
				isToRefreshing();
				startAnim();
				break;
			case MotionEvent.ACTION_UP:
				animIsOver=true;
				isToRefreshing();
				startAnim();
				break;
			}
		}
		return true;
	}
	public void onRefreshComplete() {
		refreshing=false;
		isAnimDoing=false;
		initBaseViews();
		startAnim();
		con.getSharedPreferences(shareName, 1).edit().putString(SHARE_NAME_REFRESHNAME, DateFormat.format("yyyy/MM/dd kk:mm:ss", System.currentTimeMillis()).toString()).commit();
	}

	
	
	private void isToRefreshing() {
		int tem=-topRefreshView.getHeight();
		int tem2=tem-footerRefreshView.getHeight();
		LayoutParams lp = (LayoutParams) topRefreshView.getLayoutParams();
		if(lp.topMargin>0){//进行下拉刷新
			refreshing=true;
			chanageHeaderViews();
			if(refreshListener!=null){
				refreshListener.onHeaderRefresh();
			}
		}else if(lp.topMargin<tem2){//进行上拉刷新
			refreshing=true;
			chanageBottomViews();
			if(refreshListener!=null&& refreshListener instanceof OnRefreshListener){
				((OnRefreshListener) refreshListener).onBottomRefresh();
			}
		}
	}
	
	
	public void setOnRefreshListener(OnHeaderRefreshListener refreshListener){
		if(refreshListener instanceof OnRefreshListener){
			isCanBottomRefresh=true;
		}
		this.refreshListener=refreshListener;
	}
	private boolean isFrist=true;
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(isFrist&&topRefreshView.getHeight()>0){
			LayoutParams lp = (LayoutParams) topRefreshView.getLayoutParams();
			lp.topMargin=-topRefreshView.getHeight();
			topRefreshView.setLayoutParams(lp);
			isFrist=false;
		}
		
		if(animIsOver){
			startAnim();
		}
	}
	private void startAnim() {
		int tem=-topRefreshView.getHeight();
		int tem2=footerRefreshView.getHeight();
		LayoutParams lp = (LayoutParams) topRefreshView.getLayoutParams();
		if(lp.topMargin>tem){
			lp.topMargin-=20;
			if(refreshing){
				if(lp.topMargin<0){
					lp.topMargin=0;
				}
			}else{
				if(lp.topMargin<=tem){
					lp.topMargin=tem;
				}
			}
		}else if(lp.topMargin<tem){
			lp.topMargin+=20;
			if(refreshing){
				if(lp.topMargin>=(tem-tem2)){
					lp.topMargin=tem-tem2;
				}
			}else{
				if(lp.topMargin>=tem){
					lp.topMargin=tem;
				}
			}
		}
		topRefreshView.setLayoutParams(lp);
		if(lp.topMargin!=tem){
			invalidate();
		}else{
			animIsOver=false;
		}
		
	}
	int count=0;
	private void setTopMartgin(float mar) {
		ViewGroup.LayoutParams temp = topRefreshView.getLayoutParams();
		if(temp!=null){
			int temHeight=topRefreshView.getHeight()+footerRefreshView.getHeight();
			LinearLayout.LayoutParams lp=(LayoutParams) temp;
			lp.topMargin=(int) mar;
			if(lp.topMargin>temHeight){
				lp.topMargin=temHeight;
			}else if(lp.topMargin<-2*temHeight){
				lp.topMargin=-2*temHeight;
			}
			if(!isCanBottomRefresh&& lp.topMargin<-topRefreshView.getHeight()){
				lp.topMargin=-topRefreshView.getHeight();
			}
			switch (moveStatus) {
			case STATUS_REFRESH_TOP://下拉刷新 不能变成上拉刷新  需要控制
				if(lp.topMargin<-temHeight/2){
					lp.topMargin=-temHeight/2;
				}
				break;
			case STATUS_REFRESH_FOOTER://上拉刷新不能变成下拉刷新  需要控制
				if(lp.topMargin>-temHeight/2){
					lp.topMargin=-temHeight/2;
				}
				break;
			}
			if(lp.topMargin>0){//进行下拉刷新
				if(!isAnimDoing){
					pauseRefresh();
				}
			}else if(lp.topMargin<-temHeight){//进行上拉刷新
				if(!isAnimDoing){
					pauseRefresh();
				}
			}else{
				if(isAnimDoing){
					initBaseViews();
				}
				isAnimDoing=false;
			}
			topRefreshView.setLayoutParams(lp);
		}
	}
	
	
	
	private void addTopRefreshView() {
		topRefreshView=new LinearLayout(con);
		topRefreshView.setGravity(Gravity.CENTER);
		topRefreshView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		View v=inflate(con, R.layout.view_pull_to_refresh, null);
		progressBarTop=(ProgressBar) v.findViewById(R.id.progressBar);
		imgArrawTop=(ImageView) v.findViewById(R.id.imgArraw);
		textRefreshTop=(TextView) v.findViewById(R.id.textRefresh);
		textRefreshTimeTop=(TextView) v.findViewById(R.id.textRefreshTime);
		textRefreshTimeTop.setVisibility(View.VISIBLE);
		topRefreshView.addView(v);
		addView(topRefreshView, 0);
		measureView(topRefreshView);
		LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT, topRefreshView.getMeasuredHeight());
		lp.topMargin=-topRefreshView.getMeasuredHeight();
		topRefreshView.setLayoutParams(lp);
	}
	private void addFooterRefreshView() {
		footerRefreshView=new LinearLayout(con);
		footerRefreshView.setGravity(Gravity.CENTER);
		footerRefreshView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		View v=inflate(con, R.layout.view_pull_to_refresh, null);
		progressBarBottom=(ProgressBar) v.findViewById(R.id.progressBar);
		imgArrawBottom=(ImageView) v.findViewById(R.id.imgArraw);
		textRefreshBottom=(TextView) v.findViewById(R.id.textRefresh);
		footerRefreshView.addView(v);
		addView(footerRefreshView, getChildCount());
		measureView(footerRefreshView);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, footerRefreshView.getMeasuredHeight());
		footerRefreshView.setLayoutParams(lp);
	}
	
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
	
	
	/**
	 * 初始化原先的所有view
	 */
	public void initBaseViews() {
		progressBarTop.setVisibility(View.INVISIBLE);
		imgArrawTop.setVisibility(View.VISIBLE);
		progressBarBottom.setVisibility(View.INVISIBLE);
		imgArrawBottom.setVisibility(View.VISIBLE);
		textRefreshTop.setText(con.getString(R.string.library_refresh_top));
		textRefreshBottom.setText(con.getString(R.string.library_refresh_bottom));
		doAnim(imgArrawTop);
		doAnim2(imgArrawBottom);
	}
	private void pauseRefresh() {
		isAnimDoing=true;
		textRefreshTop.setText(con.getString(R.string.library_refresh_start));
		textRefreshBottom.setText(con.getString(R.string.library_loadmore_start));
		doAnim2(imgArrawTop);
		doAnim(imgArrawBottom);
	}
	private void initAnimNow() {
		doRotate(imgArrawTop,  0,180, imgArrawTop.getWidth()/2, imgArrawTop.getHeight()/2, 0);
		doRotate(imgArrawBottom, 180, 0, imgArrawBottom.getWidth()/2, imgArrawBottom.getHeight()/2, 0);
		
		textRefreshTimeTop.setText(con.getSharedPreferences(shareName, 1).getString(SHARE_NAME_REFRESHNAME,DateFormat.format("yyyy/MM/dd kk:mm:ss", System.currentTimeMillis()).toString()));
	}
	private void doRotate(View v,int from ,int to,int x,int y,long duration) {
		RotateAnimation rotate=new RotateAnimation( from,to, x,y); 
		rotate.setDuration(duration);
		rotate.setFillAfter(true);
		v.startAnimation(rotate);
	}
	private void doAnim2(View v) {
		doRotate(v, 180, 0, v.getWidth()/2, v.getHeight()/2, 200);
	}
	private void doAnim(View v) {
		doRotate(v,  0,180, v.getWidth()/2, v.getHeight()/2, 200);
	}
	
	private void onrefresh() {
		textRefreshTop.setText(con.getString(R.string.library_refreshing));
		textRefreshBottom.setText(con.getString(R.string.library_loadmore_doing));
		
	}
	/**
	 * 修改头部下拉刷新的view
	 */
	public void chanageHeaderViews() {
		progressBarTop.setVisibility(View.VISIBLE);
		imgArrawTop.clearAnimation();
		imgArrawTop.setVisibility(View.INVISIBLE);
		onrefresh();
	}
	/**
	 * 修改底部上拉刷新的view
	 */
	public void chanageBottomViews() {
		progressBarBottom.setVisibility(View.VISIBLE);
		imgArrawBottom.clearAnimation();
		imgArrawBottom.setVisibility(View.INVISIBLE);
		onrefresh();
	}

	/**
	 * 接口 下拉刷新
	 * @author hld
	 *
	 */
	public interface OnHeaderRefreshListener{
		public void onHeaderRefresh();
	}
	/**
	 * 接口  上拉和下拉刷新
	 * @author hld
	 *
	 */
	public interface OnRefreshListener extends OnHeaderRefreshListener{
		public void onBottomRefresh();
	}
	/**
	 * 不用下拉 滚动到此进行加载
	 * @author HLD
	 *//*
	public interface OnScrollRefreshListener extends OnHeaderRefreshListener{
		public void onBottomRefresh();
	}*/
	
}