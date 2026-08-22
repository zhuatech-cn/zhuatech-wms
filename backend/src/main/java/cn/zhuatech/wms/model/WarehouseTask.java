/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wms_task", indexes = {
    @Index(name = "idx_wms_task_status", columnList = "status"),
    @Index(name = "idx_wms_task_assignee", columnList = "assignee")
})
public class WarehouseTask extends BaseEntity {
    public enum Type { RECEIVING, PUTAWAY, PICKING, REPLENISHMENT, COUNTING, PACKING }
    public enum Status { WAITING, IN_PROGRESS, EXCEPTION, COMPLETED }
    public enum Priority { NORMAL, HIGH, URGENT }

    @Column(nullable = false, unique = true, length = 32) private String taskNo;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 24) private Type type;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 12) private Priority priority;
    @Column(nullable = false, length = 60) private String warehouseName;
    @Column(nullable = false, length = 20) private String zoneCode;
    @Column(length = 30) private String sourceLocation;
    @Column(length = 30) private String targetLocation;
    @Column(nullable = false, length = 40) private String skuCode;
    @Column(nullable = false, length = 120) private String productName;
    @Column(length = 40) private String batchNo;
    @Column(nullable = false) private Integer plannedQty;
    @Column(nullable = false) private Integer completedQty;
    @Column(length = 40) private String assignee;
    @Column(nullable = false) private LocalDateTime dueAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    @Column(length = 200) private String remark;

    protected WarehouseTask() {}
    public WarehouseTask(String taskNo, Type type, Status status, Priority priority, String warehouseName,
                         String zoneCode, String sourceLocation, String targetLocation, String skuCode,
                         String productName, String batchNo, Integer plannedQty, Integer completedQty,
                         String assignee, LocalDateTime dueAt) {
        this.taskNo = taskNo; this.type = type; this.status = status; this.priority = priority;
        this.warehouseName = warehouseName; this.zoneCode = zoneCode; this.sourceLocation = sourceLocation;
        this.targetLocation = targetLocation; this.skuCode = skuCode; this.productName = productName;
        this.batchNo = batchNo; this.plannedQty = plannedQty; this.completedQty = completedQty;
        this.assignee = assignee; this.dueAt = dueAt;
        if (status == Status.IN_PROGRESS) this.startedAt = LocalDateTime.now().minusMinutes(18);
        if (status == Status.COMPLETED) this.completedAt = LocalDateTime.now().minusMinutes(9);
    }
    public void update(Status status, Integer completedQty, String assignee, String remark) {
        if (status == Status.IN_PROGRESS && startedAt == null) startedAt = LocalDateTime.now();
        if (status == Status.COMPLETED) completedAt = LocalDateTime.now();
        this.status = status;
        if (completedQty != null) this.completedQty = Math.min(plannedQty, Math.max(0, completedQty));
        if (assignee != null && !assignee.isBlank()) this.assignee = assignee;
        if (remark != null) this.remark = remark;
    }
    public String getTaskNo() { return taskNo; }
    public Type getType() { return type; }
    public Status getStatus() { return status; }
    public Priority getPriority() { return priority; }
    public String getWarehouseName() { return warehouseName; }
    public String getZoneCode() { return zoneCode; }
    public String getSourceLocation() { return sourceLocation; }
    public String getTargetLocation() { return targetLocation; }
    public String getSkuCode() { return skuCode; }
    public String getProductName() { return productName; }
    public String getBatchNo() { return batchNo; }
    public Integer getPlannedQty() { return plannedQty; }
    public Integer getCompletedQty() { return completedQty; }
    public String getAssignee() { return assignee; }
    public LocalDateTime getDueAt() { return dueAt; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public String getRemark() { return remark; }
}
