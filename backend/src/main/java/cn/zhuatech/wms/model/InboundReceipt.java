/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.wms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "wms_inbound_receipt")
public class InboundReceipt extends BaseEntity {
    public enum Status { APPOINTED, ARRIVED, RECEIVING, QC_HOLD, PUTAWAY, COMPLETED }
    @Column(nullable = false, unique = true, length = 32) private String receiptNo;
    @Column(nullable = false, length = 100) private String supplierName;
    @Column(nullable = false, length = 30) private String sourceType;
    @Column(nullable = false, length = 60) private String warehouseName;
    @Column(nullable = false, length = 30) private String dockCode;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status;
    @Column(nullable = false) private Integer lineCount;
    @Column(nullable = false) private Integer plannedQty;
    @Column(nullable = false) private Integer receivedQty;
    @Column(nullable = false) private LocalDateTime appointmentAt;
    @Column(length = 40) private String operatorName;

    protected InboundReceipt() {}
    public InboundReceipt(String receiptNo, String supplierName, String sourceType, String warehouseName,
                          String dockCode, Status status, Integer lineCount, Integer plannedQty,
                          Integer receivedQty, LocalDateTime appointmentAt, String operatorName) {
        this.receiptNo = receiptNo; this.supplierName = supplierName; this.sourceType = sourceType;
        this.warehouseName = warehouseName; this.dockCode = dockCode; this.status = status;
        this.lineCount = lineCount; this.plannedQty = plannedQty; this.receivedQty = receivedQty;
        this.appointmentAt = appointmentAt; this.operatorName = operatorName;
    }
    public String getReceiptNo() { return receiptNo; }
    public String getSupplierName() { return supplierName; }
    public String getSourceType() { return sourceType; }
    public String getWarehouseName() { return warehouseName; }
    public String getDockCode() { return dockCode; }
    public Status getStatus() { return status; }
    public Integer getLineCount() { return lineCount; }
    public Integer getPlannedQty() { return plannedQty; }
    public Integer getReceivedQty() { return receivedQty; }
    public LocalDateTime getAppointmentAt() { return appointmentAt; }
    public String getOperatorName() { return operatorName; }
}
