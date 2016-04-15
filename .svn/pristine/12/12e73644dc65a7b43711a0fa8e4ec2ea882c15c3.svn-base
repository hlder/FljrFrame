package com.hld.library.frame;

import android.content.Context;

import com.hld.library.frame.image.ImageUtils;

/**
 * 图片加载器(采用finalbitmap)<br/>
 * 初始化的时候调用,imageutils使用的是单列模式<br/>
 * iu = ImageManager.getInstance(this);//初始化FinalBitmap模块<br/>
 * iu.configLoadingImage(R.drawable.downloading);<br/>
 * //这里可以进行其他十几项的配置，也可以不用配置，配置之后必须调用init()函数,才生效<br/>
 * //iu.configBitmapLoadThreadSize(int size)<br/>
 * //iu.configBitmapMaxHeight(bitmapHeight)<br/>
 * 
 * iu.display(imageView,url);//加载图片只需要这一句话<br/>
 * @author HLD
 */
public class ImageManager{
	public static ImageUtils getInstance(Context ctx){
		return ImageUtils.create(ctx);
	}
}