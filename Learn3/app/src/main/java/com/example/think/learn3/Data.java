package com.example.think.learn3;

/**
 * Created by think on 2016/2/16.
 */
public class Data {
    private String new_title;
    private String new_content;

    public Data(){}

    public Data(String new_title, String new_content) {
        this.new_title = new_title;
        this.new_content = new_content;
    }

    public String getNew_title() {
        return new_title;
    }

    public String getNew_content() {
        return new_content;
    }

    public void setNew_title(String new_title) {
        this.new_title = new_title;
    }

    public void setNew_content(String new_content) {
        this.new_content = new_content;
    }
}
