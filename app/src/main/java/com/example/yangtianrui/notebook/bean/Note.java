package com.example.yangtianrui.notebook.bean;

import java.io.Serializable;

public class Note implements Serializable{

    private String title;
    private String content;
    private String createTime;
    private String modifyTime;

    public Note(String title, String content, String createTime,String modifyTime) {
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.modifyTime=modifyTime;
    }

    public Note() {
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
