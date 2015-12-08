package com.hld.library.frame.utils;

import java.io.File;
import java.io.IOException;

import android.os.Environment;
/**
 * 文件操作类
 * 支持文件的创建，文件夹的创建
 * 文件的删除，文件夹及其文件的删除
 * @author HLD
 */
public class FileUtils {
	/**
	 * 删除文件和文件夹
	 * @param filePath
	 */
	public static void delete(String filePath){
		if(filePath==null) return;
		File file=new File(filePath);
		delete(file);
	}
	public static void deleteInSdcard(String filePath){
		if(filePath==null) return;
		delete(getSDCardRoot()+filePath);
	}
	public static void delete(File file){
		if(file == null)return;
		if(file.isDirectory()){
			File[] f = file.listFiles();
			for (int i = 0; i < f.length; i++) {
				delete(f[i]);
			}
		}
		file.delete();
	}
	/**
	 * 获取文件对象，传全文件路劲
	 * @param filePath
	 * @return
	 */
	public static File getFileInstance(String filePath){
		if(filePath==null) return null;
		File file=new File(filePath);
		if(!file.exists()){
			try {
				String tem=filePath.substring(0, filePath.lastIndexOf("/"));
				createFileDirectory(tem);
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	public static File getFileInstanceinSdcard(String filePath){
		return getFileInstance(getSDCardRoot()+filePath);
	}
	/**
	 * 获取文件对象，不用传sd卡的路径<br/>
	 * 如在sd卡上创建jiuyi/abc.apk
	 * @param filePath
	 * @return
	 */
	public static File getFileInstanceInSdcard(String filePath){
		if(filePath==null) return null;
		return getFileInstance(getSDCardRoot()+filePath);
	}
	/**
	 * 创建文件夹
	 */
	public static boolean createFileDirectory(String filePath){
		if(filePath==null) return false;
		File file=new File(filePath);
		if(!file.exists()){
			return file.mkdirs();
		}
		return false;
	}
	/**
	 * 在sdcard上创建文件夹
	 * @param filePath
	 */
	public static boolean createFileDirectoryInSdcard(String filePath){
		if(filePath==null) return false;
		return createFileDirectory(getSDCardRoot()+filePath);
	}
	
	public static String getSDCardRoot() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
	
	
	public static boolean isFileExist(String path){
		return new File(getSDCardRoot()+path).exists();
	}
	
}