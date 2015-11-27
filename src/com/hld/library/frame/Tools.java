package com.hld.library.frame;

import java.io.File;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.hld.library.frame.utils.APPInstallUtils;
import com.hld.library.frame.utils.FileUtils;
import com.hld.library.frame.utils.Md5Utils;
import com.hld.library.frame.utils.PxDpUtils;

/**
 * 工具类
 * 1、md5加密(文件和字符串加密)
 * 2、文件操作(文件或文件夹的创建、删除,获取sd卡的路径)注意:这里所有的文件操作的根目录都是sd卡
 * 3、px和dip的转换
 * 4、获取手机的mac地址
 * 5、调用apk安装
 * @author User
 */
public class Tools {
	/**把dip转成px*/
    public static int dipToPx(Context context, float dpValue) {
    	return PxDpUtils.dipToPx(context, dpValue);
    }
    /**把px转成dip*/
    public static int pxToDip(Context context, float pxValue) {
    	return PxDpUtils.pxToDip(context, pxValue);
    } 
	
	/**删除文件和文件夹*/
	public static void delete(String filePath){
		FileUtils.delete(filePath);
	}
	/**删除文件和文件夹*/
	public static void delete(File file){
		FileUtils.delete(file);
	}
	/**创建文件夹*/
	public static boolean createFileDirectory(String filePath){
		return FileUtils.createFileDirectory(filePath);
	}
	/**获取文件的对象*/
	public static File createOrGainFile(String filePath) {
		return FileUtils.getFileInstance(filePath);
	}
	
	/**
	 * 字符串转md5
	 * @param str
	 * @return 返回md5值
	 */
	public static String stringToMd5(String str){
		return Md5Utils.stringToMd5(str);
	}
	/**
	 * 读取文件的md5值返回
	 * @param filePath 传入文件路径
	 * @return 返回md5值
	 */ 
	public static String fileToMd5(String filePath) {
		return Md5Utils.fileToMd5(filePath);
	}
	/**
	 * 读取文件的MD5值并返回
	 * @param file 传入文件
	 * @return 返回md5
	 */
	public static String fileToMd5(File file) {
		return Md5Utils.fileToMd5(file);
	}
	
	/**
	 * 获取手机的mac地址
	 * @param ctx
	 * @return
	 */
	public static String getMacAddress(Context ctx){
		WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}
	/**
	 * 调用系统的安装器
	 * @param ctx
	 * @param filePath
	 */
	public static void installBySys(Context ctx,String filePath){
		APPInstallUtils.installBySys(ctx, filePath);
	}
	/**
	 * 通过命令安装
	 * @param ctx
	 * @param filePath
	 */
	public static void installByCmd(Context ctx,String filePath){
		APPInstallUtils.installByCmd(ctx, filePath);
	}
}