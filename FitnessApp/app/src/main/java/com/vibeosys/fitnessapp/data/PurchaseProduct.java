package com.vibeosys.fitnessapp.data;

/**
 * Created by akshay on 13-05-2016.
 */
public class PurchaseProduct {

    private String productName;
    private String desc;
    private double price;

    public PurchaseProduct(String productName, String desc, double price) {
        this.productName = productName;
        this.desc = desc;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
