package com.ebiggerr.sims.domain.request;

public class deleteRequest {

    private String skunumber;

    public deleteRequest(String skunumber){
        this.skunumber=skunumber;
    }

    public String getSkunumber(){
        return this.skunumber;
    }

    public void setSkunumber(String skunumber) {
        this.skunumber = skunumber;
    }
}
