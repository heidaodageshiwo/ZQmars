package com.iekun.ef.model;

public class ExportTaskInfo {

	String exportId;
	
	int   progress;
	
	String url;

	public String getExportId() {
		return exportId;
	}

	public void setExportId(String exportId) {
		this.exportId = exportId;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
