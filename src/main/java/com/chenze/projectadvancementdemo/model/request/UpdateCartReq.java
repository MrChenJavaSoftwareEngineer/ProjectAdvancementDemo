package com.chenze.projectadvancementdemo.model.request;

public class UpdateCartReq {
    private Integer id;

    private Integer productId;
    private Integer quantity;

    @Override
    public String toString() {
        return "UpdateCartReq{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
