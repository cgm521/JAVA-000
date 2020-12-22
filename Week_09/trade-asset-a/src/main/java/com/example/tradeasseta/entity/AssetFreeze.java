package com.example.tradeasseta.entity;

import java.io.Serializable;

public class AssetFreeze implements Serializable {
    private Long id;

    private Long userId;

    private Long freezeAssetCny;

    private Long freezeAssetUs;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFreezeAssetCny() {
        return freezeAssetCny;
    }

    public void setFreezeAssetCny(Long freezeAssetCny) {
        this.freezeAssetCny = freezeAssetCny;
    }

    public Long getFreezeAssetUs() {
        return freezeAssetUs;
    }

    public void setFreezeAssetUs(Long freezeAssetUs) {
        this.freezeAssetUs = freezeAssetUs;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", freezeAssetCny=").append(freezeAssetCny);
        sb.append(", freezeAssetUs=").append(freezeAssetUs);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}