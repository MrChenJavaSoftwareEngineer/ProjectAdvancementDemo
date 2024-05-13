package com.chenze.projectadvancementdemo.model.request;

public class AddCartReq {
    private Integer productId;

    private Integer userId;

    private Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "AddCartReq{" +
                "productId=" + productId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                '}';
    }
}
