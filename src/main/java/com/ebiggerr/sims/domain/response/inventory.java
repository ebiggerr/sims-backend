package com.ebiggerr.sims.domain.response;

import com.ebiggerr.sims.domain.inventory.itemDTO;

import java.util.List;

public class inventory {

    private int currentpage;
    private int totalpage;
    List<itemDTO> list;

    public inventory(int currentpage,int totalpage, List<itemDTO> list){
        this.currentpage=currentpage;
        this.totalpage=totalpage;
        this.list=list;
    }

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public List<itemDTO> getList() {
        return list;
    }

    public void setList(List<itemDTO> list) {
        this.list = list;
    }
}
