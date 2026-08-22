/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import cn.zhuatech.wms.common.BusinessException;
import cn.zhuatech.wms.dto.WmsDto.*;
import cn.zhuatech.wms.model.*;
import cn.zhuatech.wms.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class WmsService {
    private final WarehouseTaskRepository tasks;
    private final InventoryBalanceRepository inventory;
    private final InboundReceiptRepository inbounds;
    private final OutboundWaveRepository waves;
    private final WarehouseZoneRepository zones;
    private final CurrentUserService currentUser;

    public WmsService(WarehouseTaskRepository tasks, InventoryBalanceRepository inventory,
                      InboundReceiptRepository inbounds, OutboundWaveRepository waves,
                      WarehouseZoneRepository zones, CurrentUserService currentUser) {
        this.tasks = tasks; this.inventory = inventory; this.inbounds = inbounds;
        this.waves = waves; this.zones = zones; this.currentUser = currentUser;
    }

    @Transactional(readOnly = true)
    public DashboardView dashboard() {
        int inboundQty = inbounds.findAll().stream().mapToInt(InboundReceipt::getReceivedQty).sum();
        int outboundQty = waves.findAll().stream().mapToInt(OutboundWave::getPickedQty).sum();
        int inventoryQty = inventory.findAll().stream().mapToInt(item -> item.getAvailableQty() + item.getAllocatedQty()).sum();
        int occupancy = (int) Math.round(zones.findAll().stream().mapToInt(WarehouseZone::getOccupancyRate).average().orElse(0));
        int operators = (int) tasks.findAll().stream().map(WarehouseTask::getAssignee)
            .filter(Objects::nonNull).filter(value -> !value.isBlank()).distinct().count();
        DashboardStats stats = new DashboardStats(inboundQty, outboundQty,
            tasks.countByStatus(WarehouseTask.Status.WAITING), tasks.countByStatus(WarehouseTask.Status.EXCEPTION),
            inventoryQty, occupancy, 99.72, 97.8, operators);
        List<ThroughputPoint> throughput = List.of(
            new ThroughputPoint("08:00", 186, 92), new ThroughputPoint("10:00", 342, 268),
            new ThroughputPoint("12:00", 278, 316), new ThroughputPoint("14:00", 426, 388),
            new ThroughputPoint("16:00", 391, 452), new ThroughputPoint("18:00", 208, 337));
        List<WarningView> warnings = List.of(
            new WarningView("HIGH", "A-03 巷道补货延迟", "拣选位剩余 18 件，预计 24 分钟后缺货", "补货组"),
            new WarningView("MEDIUM", "冷链区库容接近上限", "C 区占用率 92%，今晚有 2 车预约到仓", "入库主管"),
            new WarningView("LOW", "盘点差异待复核", "SKU-PD-240718 差异 3 件", "库存组"));
        return new DashboardView(stats, throughput, warnings);
    }

    @Transactional(readOnly = true)
    public List<WarehouseTask> myTasks() {
        return tasks.findByAssigneeOrderByDueAtAsc(currentUser.get().getFullName());
    }

    @Transactional
    public WarehouseTask updateTask(Long id, TaskUpdateRequest request) {
        WarehouseTask task = tasks.findById(id).orElseThrow(() -> new BusinessException("仓内任务不存在"));
        try {
            task.update(WarehouseTask.Status.valueOf(request.status()), request.completedQty(), request.assignee(), request.remark());
            return task;
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("任务状态不正确");
        }
    }

    @Transactional
    public WarehouseTask createTask(TaskCreateRequest request) {
        try {
            return tasks.save(new WarehouseTask("WT" + System.currentTimeMillis(),
                WarehouseTask.Type.valueOf(request.type()), WarehouseTask.Status.WAITING,
                WarehouseTask.Priority.NORMAL, "上海嘉定一号仓", request.zoneCode(),
                request.sourceLocation(), request.targetLocation(), request.skuCode(), request.productName(),
                request.batchNo(), request.plannedQty(), 0, request.assignee(), LocalDateTime.now().plusHours(2)));
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("任务类型不正确");
        }
    }
}
