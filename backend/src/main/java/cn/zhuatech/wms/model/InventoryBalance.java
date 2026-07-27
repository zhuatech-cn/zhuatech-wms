/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.wms.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "wms_inventory", uniqueConstraints = @UniqueConstraint(name = "uk_wms_inventory", columnNames = {"skuCode", "locationCode", "batchNo"}))
public class InventoryBalance extends BaseEntity {
    public enum QualityStatus { QUALIFIED, HOLD, DAMAGED }
    @Column(nullable = false, length = 40) private String skuCode;
    @Column(nullable = false, length = 120) private String productName;
    @Column(nullable = false, length = 60) private String warehouseName;
    @Column(nullable = false, length = 20) private String zoneCode;
    @Column(nullable = false, length = 30) private String locationCode;
    @Column(nullable = false, length = 40) private String batchNo;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 16) private QualityStatus qualityStatus;
    @Column(nullable = false) private Integer availableQty;
    @Column(nullable = false) private Integer allocatedQty;
    @Column(nullable = false) private Integer inboundQty;
    @Column(nullable = false, length = 12) private String unit;
    private LocalDate expiryDate;
    private LocalDateTime lastCountAt;

    protected InventoryBalance() {}
    public InventoryBalance(String skuCode, String productName, String warehouseName, String zoneCode,
                            String locationCode, String batchNo, QualityStatus qualityStatus, Integer availableQty,
                            Integer allocatedQty, Integer inboundQty, String unit, LocalDate expiryDate,
                            LocalDateTime lastCountAt) {
        this.skuCode = skuCode; this.productName = productName; this.warehouseName = warehouseName;
        this.zoneCode = zoneCode; this.locationCode = locationCode; this.batchNo = batchNo;
        this.qualityStatus = qualityStatus; this.availableQty = availableQty; this.allocatedQty = allocatedQty;
        this.inboundQty = inboundQty; this.unit = unit; this.expiryDate = expiryDate; this.lastCountAt = lastCountAt;
    }
    public String getSkuCode() { return skuCode; }
    public String getProductName() { return productName; }
    public String getWarehouseName() { return warehouseName; }
    public String getZoneCode() { return zoneCode; }
    public String getLocationCode() { return locationCode; }
    public String getBatchNo() { return batchNo; }
    public QualityStatus getQualityStatus() { return qualityStatus; }
    public Integer getAvailableQty() { return availableQty; }
    public Integer getAllocatedQty() { return allocatedQty; }
    public Integer getInboundQty() { return inboundQty; }
    public String getUnit() { return unit; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public LocalDateTime getLastCountAt() { return lastCountAt; }
}
