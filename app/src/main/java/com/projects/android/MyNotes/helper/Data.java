package com.projects.android.MyNotes.helper;

public class Data {
    private String title, text, date;
    private byte[] image;

    public Data(String title, String text, String date, byte[] image) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public byte[] getImage() {
        return image;
    }
}
