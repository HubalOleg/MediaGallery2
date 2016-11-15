package com.oleg.hubal.mediagallery2.model;

/**
 * Created by User on 15.11.2016.
 */

public class ImageItem implements ThumbnailItem {

    private String path;
    private String date;
    private String mimeType;
    private boolean selection;

    public ImageItem(String path, String date, String mimeType) {
        this.path = path;
        this.date = date;
        this.mimeType = mimeType;
    }

    @Override
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

    @Override
    public boolean getSelection() {
        return selection;
    }

    @Override
    public void setSelection(boolean selection) {
        this.selection = selection;
    }
}
