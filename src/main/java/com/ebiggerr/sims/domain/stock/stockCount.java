package com.ebiggerr.sims.domain.stock;

public class stockCount {

    private String SKU;
    private String restockAmount;

    public stockCount(String SKU, String restockAmount){
        this.SKU=SKU;
        this.restockAmount=restockAmount;
    }

    public String getSKU(){
        return this.SKU;
    }

    public String getRestockAmount(){
        return this.restockAmount;
    }


}
