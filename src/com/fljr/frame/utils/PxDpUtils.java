package com.fljr.frame.utils;

import android.content.Context;

/**
 * dip��pxת��
 * @author User
 */
public class PxDpUtils {
	 /** 
	  * dip转px
     */  
    public static int dipToPx(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
    /** 
     * px转dip
     */  
    public static int pxToDip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
}
