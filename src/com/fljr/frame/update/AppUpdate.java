package com.fljr.frame.update;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.fljr.frame.Download;
import com.fljr.frame.download.DownLoadConfig;
import com.fljr.frame.download.OnFileDownListener;
import com.fljr.frame.download.Request;
import com.fljr.frame.utils.APPInstallUtils;
import com.fljr.frame.utils.FileUtils;

@SuppressLint("HandlerLeak")
public class AppUpdate {
	private Download download;
	private String filePath=DownLoadConfig.APP_UPDATE_FILEPATH;
	private String downloadUrl;
	private long fileSize;
	private ProgressDialog dialog;
	private float bl;
	private Context ctx;
	
	public AppUpdate(Context ctx,String downloadUrl,long fileSize) {
		this.ctx=ctx;
		this.downloadUrl=downloadUrl;
		this.fileSize=fileSize;
		download=new Download(ctx);
		download.setOnFileDownListener(onFileDownListener);

		dialog=new ProgressDialog(ctx);
		dialog.setCancelable(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		
		bl=100f/fileSize;
		dialog.setMax(100);
		FileUtils.createFileDirectoryInSdcard(filePath);
		
		filePath+=ctx.getPackageName()+"file.apk";
	}
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0://正在下载
				Log.d("dddd", "正在下载:"+msg.arg1);
				dialog.setProgress(msg.arg1);
				break;
			case 1://下载成功
				dialog.dismiss();
				dialog.cancel();
				APPInstallUtils.installBySys(ctx, FileUtils.getSDCardRoot()+filePath);
				break;
			case 2://下载失败
				dialog.dismiss();
				dialog.cancel();
				Toast.makeText(ctx, "下载失败", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
	
	public void startDownload() {
		dialog.show();
		download.downLoad(downloadUrl, filePath, fileSize, 1);
	}
	private OnFileDownListener onFileDownListener=new OnFileDownListener() {
		@Override
		public void onSuccess(Request request, int tag) {
			Message msg = handler.obtainMessage();
			msg.what=1;
			handler.sendMessage(msg);
		}
		@Override
		public void onStatusChanage(int status, int tag) {
			
		}
		@Override
		public void onStart(long total, long current, double speed, int tag) {
			
		}
		@Override
		public void onLoading(long total, long current, double speed, int tag) {
			Message msg = handler.obtainMessage();
			msg.what=0;
			msg.arg1=(int) (current*bl);
			handler.sendMessage(msg);
		}
		@Override
		public void onFailure(Exception error, String msg, int tag) {
			Message message = handler.obtainMessage();
			message.what=2;
			handler.sendMessage(message);
		}
	};
}