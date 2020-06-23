package com.DO.Prediction.client.entity;

/**
 * Created by Zheng Chao You on 2020/1/24 0024.
 */

public class BotBean {
   private String content; //图标名字
    private int uncheckedId; //未选中时的图标

    public BotBean(String content, int unchecked) {
        this.content = content;
        this.uncheckedId = unchecked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUncheckedId() {
        return uncheckedId;
    }

    public void setUncheckedId(int uncheckedId) {
        this.uncheckedId = uncheckedId;
    }
}
