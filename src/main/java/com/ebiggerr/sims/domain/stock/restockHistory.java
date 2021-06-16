package com.ebiggerr.sims.domain.stock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="restockhistory")
public class restockHistory {

    @Id
    @Column(name="itemid")
    private Long itemid;

    @Column(name="quantity")
    private int quantity;

    @Column(name="restocktime")
    private LocalDateTime restockTime;

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getRestockTime() {
        return restockTime;
    }

    public void setRestockTime(LocalDateTime restockTime) {
        this.restockTime = restockTime;
    }
}
