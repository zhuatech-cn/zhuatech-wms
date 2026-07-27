/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.wms.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public final class WmsDto {
    private WmsDto() {}

    public record DashboardStats(int todayInbound, int todayOutbound, long pendingTasks, long exceptionTasks,
                                 int inventoryUnits, int occupancyRate, double inventoryAccuracy,
                                 double onTimeRate, int activeOperators) {}
    public record ThroughputPoint(String hour, int inbound, int outbound) {}
    public record WarningView(String level, String title, String detail, String owner) {}
    public record DashboardView(DashboardStats stats, List<ThroughputPoint> throughput,
                                List<WarningView> warnings) {}

    public record TaskUpdateRequest(@NotBlank(message = "请选择任务状态") String status,
                                    @Min(value = 0, message = "完成数量不能小于 0") Integer completedQty,
                                    String assignee, String remark) {}
    public record TaskCreateRequest(@NotBlank(message = "请输入任务类型") String type,
                                    @NotBlank(message = "请输入库区") String zoneCode,
                                    String sourceLocation, String targetLocation,
                                    @NotBlank(message = "请输入 SKU") String skuCode,
                                    @NotBlank(message = "请输入商品名称") String productName,
                                    String batchNo,
                                    @NotNull(message = "请输入计划数量") @Min(value = 1, message = "计划数量至少为 1") Integer plannedQty,
                                    String assignee) {}
}
