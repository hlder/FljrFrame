package com.fljr.frame.download;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fljr.frame.utils.FileUtils;


/**
 * 文件下载管理<br/>
 * 支持多线程同步下载<br/>
 * 支持断点续传<br/>
 * 使用：<br/>
 * DownLoadUtils utils=new DownLoadUtils();<br/>
 * utils.setOnFileDownListener(listener);//设置回调函数<br/>
 * utils.download(url,"sdcard/XXX.apk",3);//开始下载文件<br/>
 * utils.download(url2,"sdcard/XXX.apk",4);//开始下载文件2<br/>
 *  //utils.pause(3);//暂停<br/>
 *  //utils.resume(3);//继续下载<br/>
 * @author HLD
 */
@SuppressLint("UseSparseArrays") 
public class DownLoadUtils {
	private OnFileDownListener listener;
	private Map<Integer, DownLoadThread> map;//需要暂停的线程的tag集合，key：tag，value：线程对象
	
	private ExecutorService pools=null; 
	private Context ctx;
	
	public DownLoadUtils(Context ctx,int poolSize) {
		this.ctx=ctx;
		init(poolSize);
	}
	public DownLoadUtils(Context ctx) {
		this.ctx=ctx;
		init(5);//默认线程池容量是5个线程
	}
	private void init(int poolSize) {
		pools=Executors.newFixedThreadPool(poolSize);
		map=new HashMap<Integer, DownLoadThread>();
	}
	
	
	public boolean isHaveTask(){
		return !pools.isTerminated();
	}
	
	/**
	 * 添加下载任务
	 * @param url 下载地址
	 * @param filePath 保存文件路劲
	 * @param idReLoad 是否重新下载，true表示重新下载，false表示断点续传,默认重新下载
	 * @param tag 标记
	 */
	public void downLoad(String url,String filePath,File file,long max,boolean isReLoad,int tag){
		downWait(tag);
		if(map.get(tag)==null){
			DownLoadThread thread=null;
			if(file==null){
				thread=new DownLoadThread(url, filePath,max,isReLoad, tag);
			}else{
				thread=new DownLoadThread(url, file,max,isReLoad, tag);
			}
			map.put(tag, thread);
			pools.execute(thread);
		}else{
			if(isReLoad){
				reLoad(tag);
			}else{
				resume(tag);
			}
//			try {
//				throw new DownLoadException(DownLoadConfig.ERROR_MESSAGE_EXIST);
//			} catch (DownLoadException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	
	public void downLoad(String url,File file,long fileSize,int tag){
		downLoad(url, null, file,fileSize,true, tag);
	}
	public void downLoad(String url,String filePath,long fileSize,int tag){
		downLoad(url, filePath, null,fileSize,true, tag);
	}
	public void downLoad(String url,File file,long fileSize,boolean isReLoad,int tag){
		downLoad(url, null, file,fileSize,isReLoad, tag);
	}
	public void downLoad(String url,String filePath,long fileSize,boolean isReLoad,int tag){
		downLoad(url, filePath, null,fileSize,isReLoad, tag);
	}

	
	/**
	 * 下载线程
	 * 执行下载
	 * @author HLD
	 */
	class DownLoadThread extends Thread{
		private int tag;
		private String downLoadUrl;
		private String fileName;
		private boolean flag=true;
		private boolean isUsed=false;
		private File file;
		private boolean isReLoad;
		private long max;
		private int isSend=0;
		
		private double lastSpeed=0;
		private long lastSendTime=0;
		private long lastTime=0;
		private long lastCount=0;
		public boolean isUsed() {
			return isUsed;
		}
		public void setReLoad(boolean isReLoad) {
			this.isReLoad = isReLoad;
		}
		public DownLoadThread(String url,String fileName,long max,boolean isReLoad,int tag) {
			this.max=max;
			this.isReLoad=isReLoad;
			this.downLoadUrl=url;
			this.fileName=fileName;
			this.tag=tag;
		}
		public DownLoadThread(String url,File file,long max,boolean isReLoad,int tag) {
			this.max=max;
			this.isReLoad=isReLoad;
			this.downLoadUrl=url;
			this.file=file;
			this.tag=tag;
		}
		public void onstop() {
			flag=false;
		}
		public void onresume(){
			flag=true;
			isUsed=false;
		}
		public void run() {
			if(flag){
				InputStream is=null;
				FileOutputStream os=null;
				long count=0;
				long skip=0;
				try{
					URL url = new URL(downLoadUrl);
					// 打开连接   
					URLConnection con = url.openConnection();
					 if(file==null||!file.exists()){
						 file=FileUtils.getFileInstanceInSdcard(fileName);
					 }
					byte[] bs = new byte[1024]; 
					int len;
					if(max<=0){
						max=con.getContentLength();
					}
					skip=getProcess(file);
					if(isReLoad){//如果重新下载，便把skip设为0
						skip=0;
					}
					if(skip>=max){//如果此文件已经下载完成，便重新下载
						skip=0;
						isReLoad=true;
					}
					try{
						os = new FileOutputStream(file,!isReLoad);
					}catch(FileNotFoundException e){
						file=FileUtils.getFileInstanceInSdcard(fileName);
						os = new FileOutputStream(file,!isReLoad);
					}
					startDown(tag);
					boolean isStart=false;
					// 设置断点续传的开始位置 
					con.setRequestProperty("RANGE","bytes="+skip+"-"+max); 
					// 输入流   
					 is = con.getInputStream();
					while ((len = is.read(bs)) != -1) {
						if(!flag){
							isUsed=true;
							break;
						}
						saveProcess(tag, count);
						count+=len;
			            os.write(bs, 0, len);
			            if(isSend>50){
			            	long nowTime=System.currentTimeMillis();
			            	double tempSpeed=0;
			            	if(lastTime>0&&lastCount>0){
			            		tempSpeed=(double)(count+skip-lastCount)*(1000f/(nowTime-lastTime));
			            	}
			            	if(tempSpeed<0){
			            		tempSpeed=-tempSpeed;
			            	}
			            	if((nowTime-lastSendTime)>1000){
			            		lastSpeed=tempSpeed;
			            		lastSendTime=nowTime;
			            	}
			            	lastCount=count+skip;
			            	lastTime=nowTime;
			            	if(!isStart){
			            		isStart=true;
								onStart(max, count+skip,lastSpeed, tag);
			            	}
			            	onloading(max, count+skip,lastSpeed, tag);
			            	isSend=0;			            	
			            }
			            isSend++;
			         }
				}catch(Exception e){
					flag=false;
					e.printStackTrace();
					downFailure(e,DownLoadException.ERROR_MESSAGE_NET(ctx), tag);
				}finally{
					if(os!=null){
						try {
							os.close();
						} catch (IOException e) {
							flag=false;
							e.printStackTrace();
							downFailure(e, DownLoadException.ERROR_MESSAGE_NET(ctx), tag);
						}  
					}
					if(is!=null){
						try {
							is.close();
						} catch (IOException e) {
							flag=false;
							e.printStackTrace();
							downFailure(e, DownLoadException.ERROR_MESSAGE_NET(ctx), tag);
						}
					}
				}
				
				if(flag&&listener!=null){
					long temCount=count+skip;
					if((max>0&&temCount==max)||(max<=0&&temCount>0)){//下载成功
						Request request=new Request();
						request.setUrl(downLoadUrl);
						request.setFileName(fileName);
						if(max<=0){
							request.setSize(temCount);
						}else{
							request.setSize(max);
						}
						downSuccess(request, tag);
					}else{//文件大小不对,下载失败
						downFailure(new DownLoadException("下载失败:"+temCount+"/"+max),DownLoadException.ERROR_MESSAGE_NET(ctx), tag);
					}
				}
				isUsed=true;
			}
		}
	}
	
	private void startDown(int tag){//开始下载
		if(listener!=null){
			listener.onStatusChanage(DownLoadConfig.STATUS_DOWN_DOING, tag);
		}
	}
	
	private void downFailure(Exception e,String message,int tag){//下载失败
		if(e!=null){
			e.printStackTrace();
		}
		if(listener!=null){
			listener.onStatusChanage(DownLoadConfig.STATUS_DOWN_FAILURE, tag);
			listener.onFailure(e, message, tag);
		}
	}
	private void downSuccess(Request request, int tag){//下载成功
		if(listener!=null){
			listener.onStatusChanage(DownLoadConfig.STATUS_DOWN_SUCCESS, tag);
			listener.onSuccess(request, tag);
		}
	}
	private void downWait(int tag){//等待下载
		if(listener!=null){
			listener.onStatusChanage(DownLoadConfig.STATUS_DOWN_WAIT, tag);
		}
	}
	private void onStart(long max,long count,double speed,int tag) {
		if(listener!=null){
			listener.onStart(max, count,speed, tag);
		}
	}
	private void onloading(long max,long count,double speed,int tag) {
		if(listener!=null)
			listener.onLoading(max, count,speed, tag);
	}
	
	
	/**
	 * 保存下载进度
	 * @param tag
	 * @param size
	 */
	private void saveProcess(int tag,long size) {
		
	}
	/**
	 * 获取上次下载进度，断点续传
	 * @param tag
	 * @return
	 */
	private long getProcess(File file){
		if(file!=null){
			return file.length();
		}
		return 0;
	}
	
	
	/**
	 * 暂停
	 */
	public void pause(int tag){
		DownLoadThread thread = map.get(tag);
		if(thread!=null){
			thread.onstop();
			if(listener!=null){
				listener.onStatusChanage(DownLoadConfig.STATUS_DOWN_PAUSE, tag);
			}
		}
	}
	/**
	 * 重新启动
	 */
	public void resume(int tag){
		DownLoadThread thread= map.get(tag);
		if(thread!=null){
			thread.onresume();
			if(!thread.isAlive()){
				thread.setReLoad(false);
				pools.execute(thread);
			}
		}
	}
	public void reLoad(int tag) {
		DownLoadThread thread= map.get(tag);
		if(thread!=null){
			thread.onresume();
			if(!thread.isAlive()){
				thread.setReLoad(true);
				pools.execute(thread);
			}
		}
	}
	
	public void setOnFileDownListener(OnFileDownListener listener) {
		this.listener=listener;
	}
}