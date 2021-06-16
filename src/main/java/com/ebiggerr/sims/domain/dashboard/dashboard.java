package com.ebiggerr.sims.domain.dashboard;

import java.util.List;

public class dashboard {

    private List<category> category;
    private String totalassets;

    public dashboard( List<category> list, String totalassets){
        this.category=list;
        this.totalassets=totalassets;
    }

    public List<category> getCategory() {
        return category;
    }

    public void setCategory(List<category> category) {
        this.category = category;
    }

    public String getTotalassets() {
        return totalassets;
    }

    public void setTotalassets(String totalassets) {
        this.totalassets = totalassets;
    }
}
