package com.fljr.frame.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class APPInstallUtils {
	
	public static void installBySys(Context ctx,String filePath){
		Intent intent = new Intent(Intent.ACTION_VIEW);  
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://"+filePath),"application/vnd.android.package-archive");  
		ctx.startActivity(intent);  
	}
	public static void installByCmd(Context ctx,String filePath){
		boolean flag=doProcess("pm install -r "+filePath+"\n");
		if(flag){
			installBySys(ctx, filePath);
		}
	}
	private static boolean doProcess(String order) {
		Process process=null;
		try {
			process =Runtime.getRuntime().exec("su");
    		StringBuffer successStr=new StringBuffer();
    		OutputStream out = process.getOutputStream();
    		BufferedReader brSuccess=new BufferedReader(new InputStreamReader(process.getInputStream()));
    		PrintWriter pw=new PrintWriter(out);
    		pw.write(order);
    		pw.close();
    		process.waitFor();
    		String line="";
    		while((line=brSuccess.readLine())!=null){
    			successStr.append(line);
    		}
    		String successmsg=successStr.toString();
    		if(successmsg!=null&&!"".equals(successmsg)&&("success".equals(successmsg)||"Success".equals(successmsg))){
    			return true;
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(process!=null){
				process.destroy();
			}
		}
    	return false;
	}
	
}