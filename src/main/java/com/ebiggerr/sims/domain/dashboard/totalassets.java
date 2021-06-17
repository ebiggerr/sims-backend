package com.ebiggerr.sims.domain.dashboard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="itemlisting")
public class totalassets {

    @Id
    @Column(name="totalassets")
    private String totalassets;

    public totalassets(){

    }

    public totalassets(String totalassets){
        this.totalassets=totalassets;
    }

    public String getTotalassets() {
        return totalassets;
    }

    public void setTotalassets(String totalassets) {
        this.totalassets = totalassets;
    }
}
