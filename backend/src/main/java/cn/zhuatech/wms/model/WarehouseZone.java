/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.model;

import jakarta.persistence.*;

@Entity @Table(name = "wms_warehouse_zone")
public class WarehouseZone extends BaseEntity {
    public enum Type { RECEIVING, STORAGE, PICKING, FROZEN, RETURNS, SHIPPING }
    public enum Status { NORMAL, BUSY, MAINTENANCE }
    @Column(nullable = false, unique = true, length = 20) private String zoneCode;
    @Column(nullable = false, length = 60) private String zoneName;
    @Column(nullable = false, length = 60) private String warehouseName;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Type type;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status;
    @Column(nullable = false) private Integer locationCount;
    @Column(nullable = false) private Integer usedLocationCount;
    @Column(nullable = false) private Integer occupancyRate;
    @Column(length = 30) private String environment;

    protected WarehouseZone() {}
    public WarehouseZone(String zoneCode, String zoneName, String warehouseName, Type type, Status status,
                         Integer locationCount, Integer usedLocationCount, Integer occupancyRate, String environment) {
        this.zoneCode = zoneCode; this.zoneName = zoneName; this.warehouseName = warehouseName;
        this.type = type; this.status = status; this.locationCount = locationCount;
        this.usedLocationCount = usedLocationCount; this.occupancyRate = occupancyRate; this.environment = environment;
    }
    public String getZoneCode() { return zoneCode; }
    public String getZoneName() { return zoneName; }
    public String getWarehouseName() { return warehouseName; }
    public Type getType() { return type; }
    public Status getStatus() { return status; }
    public Integer getLocationCount() { return locationCount; }
    public Integer getUsedLocationCount() { return usedLocationCount; }
    public Integer getOccupancyRate() { return occupancyRate; }
    public String getEnvironment() { return environment; }
}
