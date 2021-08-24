package com.liang.lagou_minicat;

import java.util.ArrayList;
import java.util.List;

public class Context {

    private String path;

    private List<Wrapper> wrapperList = new ArrayList<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Wrapper> getWrapperList() {
        return wrapperList;
    }

    public void setWrapperList(List<Wrapper> wrapperList) {
        this.wrapperList = wrapperList;
    }
}
