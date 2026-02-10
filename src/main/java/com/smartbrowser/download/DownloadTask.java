package com.smartbrowser.download;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 下载任务模型
 */
public class DownloadTask implements Serializable {
    private String id;
    private String url;
    private String filename;
    private String savePath;
    private long fileSize;
    private long downloadedSize;
    private DownloadStatus status;
    private Date startTime;
    private Date completeTime;

    public enum DownloadStatus {
        PENDING, DOWNLOADING, PAUSED, COMPLETED, FAILED
    }

    public DownloadTask() {
        this.id = UUID.randomUUID().toString();
        this.startTime = new Date();
        this.status = DownloadStatus.PENDING;
    }

    public DownloadTask(String url, String filename, String savePath) {
        this();
        this.url = url;
        this.filename = filename;
        this.savePath = savePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(long downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

    public DownloadStatus getStatus() {
        return status;
    }

    public void setStatus(DownloadStatus status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public double getProgress() {
        if (fileSize <= 0) return 0;
        return (double) downloadedSize / fileSize * 100;
    }

    public void pause() {
        if (this.status == DownloadStatus.DOWNLOADING) {
            this.status = DownloadStatus.PAUSED;
        }
    }

    public void resume() {
        if (this.status == DownloadStatus.PAUSED) {
            this.status = DownloadStatus.DOWNLOADING;
        }
    }

    public void cancel() {
        this.status = DownloadStatus.FAILED;
    }

    @Override
    public String toString() {
        return "DownloadTask{" +
                "filename='" + filename + '\'' +
                ", progress=" + getProgress() + "%" +
                ", status=" + status +
                '}';
    }
}
