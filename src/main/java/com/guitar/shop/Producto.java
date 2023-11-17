package com.guitar.shop;

public class Producto {
    private String productID;
    private String api_id;
    private String productName;
    private long quantity;
    private long price;

    // Getters y setters

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getApi_id() {
        return api_id;
    }

    public void setApi_id(String api_id) {
        this.api_id = api_id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return this.price * 100;
    }

    public void setPrice(long price) {
        this.price = price;
    }

}
