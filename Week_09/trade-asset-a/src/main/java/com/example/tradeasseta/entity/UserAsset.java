package com.example.tradeasseta.entity;

import lombok.ToString;

import java.io.Serializable;
@ToString
public class UserAsset implements Serializable {
    private Long id;

    private String name;

    private Long assetCny;

    private Long assetUs;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getAssetCny() {
        return assetCny;
    }

    public void setAssetCny(Long assetCny) {
        this.assetCny = assetCny;
    }

    public Long getAssetUs() {
        return assetUs;
    }

    public void setAssetUs(Long assetUs) {
        this.assetUs = assetUs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", assetCny=").append(assetCny);
        sb.append(", assetUs=").append(assetUs);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}