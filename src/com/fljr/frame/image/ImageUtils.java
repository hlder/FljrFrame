package com.fljr.frame.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import net.tsz.afinal.bitmap.download.Downloader;

/**
 * 图片加载器(采用finalbitmap)<br/>
 * 初始化的时候调用,imageutils使用的是单列模式<br/>
 * iu = ImageUtils.create(this);//初始化FinalBitmap模块<br/>
 * iu.configLoadingImage(R.drawable.downloading);<br/>
 * //这里可以进行其他十几项的配置，也可以不用配置，配置之后必须调用init()函数,才生效<br/>
 * //iu.configBitmapLoadThreadSize(int size)<br/>
 * //iu.configBitmapMaxHeight(bitmapHeight)<br/>
 * 
 * iu.display(imageView,url);//加载图片只需要这一句话<br/>
 * @author HLD
 */
public class ImageUtils{
	private FinalBitmap image;
	private static ImageUtils iu=null;
	
	public static ImageUtils create(Context ctx){
		if(iu==null){
			iu=new ImageUtils();
		}
		if(iu.image==null){
			iu.image=FinalBitmap.create(ctx);
		}
		return iu;
	}
	
	public FinalBitmap configLoadingImage(Bitmap bitmap) {
		return image.configLoadingImage(bitmap);
	}
	public FinalBitmap configLoadingImage(int resId) {
		return image.configLoadingImage(resId);
	}
	public FinalBitmap configLoadfailImage(Bitmap bitmap) {
		return image.configLoadfailImage(bitmap);
	}
	public FinalBitmap configLoadfailImage(int resId) {
		return image.configLoadfailImage(resId);
	}
	public FinalBitmap configBitmapMaxHeight(int bitmapHeight) {
		return image.configBitmapMaxHeight(bitmapHeight);
	}
	public FinalBitmap configBitmapMaxWidth(int bitmapWidth) {
		return image.configBitmapMaxWidth(bitmapWidth);
	}
	public FinalBitmap configDownlader(Downloader downlader) {
		return image.configDownlader(downlader);
	}
	public FinalBitmap configDisplayer(Displayer displayer) {
		return image.configDisplayer(displayer);
	}
	public FinalBitmap configDiskCachePath(String strPath) {
		return image.configDiskCachePath(strPath);
	}
	public FinalBitmap configMemoryCacheSize(int size) {
		return image.configMemoryCacheSize(size);
	}
	public FinalBitmap configMemoryCachePercent(float percent) {
		return image.configMemoryCachePercent(percent);
	}
	public FinalBitmap configDiskCacheSize(int size) {
		return image.configDiskCacheSize(size);
	}
	public FinalBitmap configBitmapLoadThreadSize(int size) {
		return image.configBitmapLoadThreadSize(size);
	}
	public FinalBitmap configRecycleImmediately(boolean recycleImmediately) {
		return image.configRecycleImmediately(recycleImmediately);
	}
	public void display(View imageView, String uri) {
		image.display(imageView, uri);
	}
	public void display(View imageView, String uri, int imageWidth,
			int imageHeight) {
		image.display(imageView, uri, imageWidth, imageHeight);
	}
	public void display(View imageView, String uri, Bitmap loadingBitmap) {
		image.display(imageView, uri, loadingBitmap);
	}
	public void display(View imageView, String uri, Bitmap loadingBitmap,
			Bitmap laodfailBitmap) {
		image.display(imageView, uri, loadingBitmap, laodfailBitmap);
	}
	public void display(View imageView, String uri, int imageWidth,
			int imageHeight, Bitmap loadingBitmap, Bitmap laodfailBitmap) {
		image.display(imageView, uri, imageWidth, imageHeight, loadingBitmap,
				laodfailBitmap);
	}
	public void display(View imageView, String uri, BitmapDisplayConfig config) {
		image.display(imageView, uri, config);
	}
	public Bitmap getBitmapFromCache(String key) {
		return image.getBitmapFromCache(key);
	}
	public Bitmap getBitmapFromMemoryCache(String key) {
		return image.getBitmapFromMemoryCache(key);
	}
	public Bitmap getBitmapFromDiskCache(String key) {
		return image.getBitmapFromDiskCache(key);
	}
	public Bitmap getBitmapFromDiskCache(String key, BitmapDisplayConfig config) {
		return image.getBitmapFromDiskCache(key, config);
	}
	public void setExitTasksEarly(boolean exitTasksEarly) {
		image.setExitTasksEarly(exitTasksEarly);
	}
	public void onResume() {
		image.onResume();
	}
	public void onPause() {
		image.onPause();
	}
	public void onDestroy() {
		image.onDestroy();
	}
	public void clearCache() {
		image.clearCache();
	}
	public void clearCache(String key) {
		image.clearCache(key);
	}
	public void clearMemoryCache() {
		image.clearMemoryCache();
	}
	public void clearMemoryCache(String key) {
		image.clearMemoryCache(key);
	}
	public void clearDiskCache() {
		image.clearDiskCache();
	}
	public void clearDiskCache(String key) {
		image.clearDiskCache(key);
	}
	public void closeCache() {
		image.closeCache();
	}
	public void exitTasksEarly(boolean exitTasksEarly) {
		image.exitTasksEarly(exitTasksEarly);
	}
	public void pauseWork(boolean pauseWork) {
		image.pauseWork(pauseWork);
	}
}