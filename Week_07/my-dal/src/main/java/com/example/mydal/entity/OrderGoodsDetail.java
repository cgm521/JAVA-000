package com.example.mydal.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderGoodsDetail implements Serializable {
    private Long id;

    private Long orderId;

    private Long goodsId;

    private BigDecimal snapshotRealPayment;

    private BigDecimal snapshotPrice;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getSnapshotRealPayment() {
        return snapshotRealPayment;
    }

    public void setSnapshotRealPayment(BigDecimal snapshotRealPayment) {
        this.snapshotRealPayment = snapshotRealPayment;
    }

    public BigDecimal getSnapshotPrice() {
        return snapshotPrice;
    }

    public void setSnapshotPrice(BigDecimal snapshotPrice) {
        this.snapshotPrice = snapshotPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", snapshotRealPayment=").append(snapshotRealPayment);
        sb.append(", snapshotPrice=").append(snapshotPrice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}