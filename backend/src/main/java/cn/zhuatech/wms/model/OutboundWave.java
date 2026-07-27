/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.wms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "wms_outbound_wave")
public class OutboundWave extends BaseEntity {
    public enum Status { CREATED, RELEASED, PICKING, PACKING, CLOSED, EXCEPTION }
    @Column(nullable = false, unique = true, length = 32) private String waveNo;
    @Column(nullable = false, length = 60) private String warehouseName;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status;
    @Column(nullable = false) private Integer orderCount;
    @Column(nullable = false) private Integer skuCount;
    @Column(nullable = false) private Integer pieceCount;
    @Column(nullable = false) private Integer pickedQty;
    @Column(nullable = false, length = 40) private String carrierName;
    @Column(nullable = false) private LocalDateTime cutoffAt;
    @Column(length = 40) private String ownerName;

    protected OutboundWave() {}
    public OutboundWave(String waveNo, String warehouseName, Status status, Integer orderCount,
                        Integer skuCount, Integer pieceCount, Integer pickedQty, String carrierName,
                        LocalDateTime cutoffAt, String ownerName) {
        this.waveNo = waveNo; this.warehouseName = warehouseName; this.status = status;
        this.orderCount = orderCount; this.skuCount = skuCount; this.pieceCount = pieceCount;
        this.pickedQty = pickedQty; this.carrierName = carrierName; this.cutoffAt = cutoffAt; this.ownerName = ownerName;
    }
    public String getWaveNo() { return waveNo; }
    public String getWarehouseName() { return warehouseName; }
    public Status getStatus() { return status; }
    public Integer getOrderCount() { return orderCount; }
    public Integer getSkuCount() { return skuCount; }
    public Integer getPieceCount() { return pieceCount; }
    public Integer getPickedQty() { return pickedQty; }
    public String getCarrierName() { return carrierName; }
    public LocalDateTime getCutoffAt() { return cutoffAt; }
    public String getOwnerName() { return ownerName; }
}
