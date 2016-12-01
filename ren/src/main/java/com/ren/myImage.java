package com.ren;

import android.graphics.Bitmap;

/**
 * Created by user on 11/18/2016.
 */

public class myImage {
    private String name, url;
    private Bitmap bitmap;
    public myImage(Bitmap bitmap, String name){
        this.setBitmap(bitmap);
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }
}
