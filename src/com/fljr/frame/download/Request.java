package com.fljr.frame.download;

public class Request {
	private String url;
	private String fileName;
	private long size;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "FileRequest [url=" + url + ", fileName=" + fileName + ", size="
				+ size + "]";
	}
}