package com.ebiggerr.sims.domain.sales;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name= "historicalsales2015")
public class historicalSales implements Serializable {

    @Id
    @Column(name = "month")
    private LocalDate month;

    @Column(name="salesnumber", nullable = true)
    private Integer salesNumber;


    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public Integer getSalesNumber() {
        if( salesNumber == null )return 0;
        return salesNumber;
    }

    public void setSalesNumber(Integer salesNumber) {
        this.salesNumber = salesNumber;
    }
}
