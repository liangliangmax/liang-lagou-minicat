package com.liang.lagou_minicat;

import com.liang.minicat.servlet.api.Request;
import com.liang.minicat.servlet.api.Servlet;

import java.util.*;

public class Mapper {

    private List<Host> hosts = new ArrayList<>();

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

}
