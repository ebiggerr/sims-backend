package com.ebiggerr.sims.domain.demand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="saleshistory")
public class demand {

    @Id
    @Column(name="mon")
    private String monthInNumber;

    @Column(name="sum")
    private double value;

    public demand(){

    }

    public demand(String monthInNumber, double value) {
        this.monthInNumber=monthInNumber;
        this.value=value;
    }

    public String getMonthInNumber() {
        return monthInNumber;
    }

    public void setMonthInNumber(String monthInNumber) {
        this.monthInNumber = monthInNumber;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
