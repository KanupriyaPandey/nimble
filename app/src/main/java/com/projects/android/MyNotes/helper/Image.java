package com.projects.android.MyNotes.helper;

public class Image {
    private int imageResource;
    private int id;

    public Image(int drawable, int id) {
        imageResource = drawable;
        this.id = id;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getId() {
        return id;
    }

}

