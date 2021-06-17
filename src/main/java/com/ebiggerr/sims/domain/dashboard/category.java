package com.ebiggerr.sims.domain.dashboard;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "itemcategory")
public class category {

    @Id
    //@JsonIgnore
    @Column(name = "categoryid")
    private Long id;

    @Column(name="categoryname")
    private String categoryName;

    // aggregated field from the categoryRepo
    // getCategoricalAnalytics()
    @Column(name="value", nullable = true)
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

