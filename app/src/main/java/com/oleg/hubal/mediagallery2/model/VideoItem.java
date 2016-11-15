package com.oleg.hubal.mediagallery2.model;

/**
 * Created by User on 15.11.2016.
 */

public class VideoItem implements ThumbnailItem {

    private String path;
    private String date;
    private String mimeType;
    private long duration;
    private boolean selection;

    public VideoItem(String path, String date, String mimeType, String duration) {
        this.path = path;
        this.date = date;
        this.mimeType = mimeType;
        this.duration = Long.valueOf(duration);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public boolean getSelection() {
        return selection;
    }

    @Override
    public void setSelection(boolean selection) {
        this.selection = selection;
    }

}
