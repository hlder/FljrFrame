package com.hld.library.frame.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5校验，文件加密
 * @author HLD
 */
public class Md5Utils {
	public static String stringToMd5(String str){
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			return  byteArrayToHex(messageDigest.digest(str.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String fileToMd5(File file){
		if(file==null){
			return null;
		}
		DigestInputStream digestInputStream=null;
		try {
			// 拿到一个MD5转换器（同样，这里可以换成SHA1）
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 使用DigestInputStream
			FileInputStream input = new FileInputStream(file);
			digestInputStream= new DigestInputStream(input,messageDigest);
			int bufferSize = 256 * 1024;

			// read的过程中进行MD5处理，直到读完文件
			byte[] buffer = new byte[bufferSize];
			while (digestInputStream.read(buffer) > 0);

			// 获取最终的MessageDigest
			messageDigest = digestInputStream.getMessageDigest();
			// 拿到结果，也是字节数组，包含16个元素
			byte[] resultByteArray = messageDigest.digest();

			// 同样，把字节数组转换成字符串
			return  byteArrayToHex(resultByteArray);

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(digestInputStream!=null){
				try {
					digestInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static String fileToMd5(String filePath) {
		return fileToMd5(new File(filePath));
	}

	private static String byteArrayToHex(byte[] byteArray) {
		// 首先初始化一个字符数组，用来存放每个16进制字符
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F' };
		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
		char[] resultCharArray = new char[byteArray.length * 2];
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		// 字符数组组合成字符串返回
		return new String(resultCharArray);
	}

}
