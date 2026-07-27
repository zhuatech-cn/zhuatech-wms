/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.wms.controller;

import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.dto.WmsDto.*;
import cn.zhuatech.wms.model.*;
import cn.zhuatech.wms.repository.*;
import cn.zhuatech.wms.service.WmsService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/wms")
public class WmsController {
    private final WmsService service;
    private final WarehouseTaskRepository tasks;
    private final InventoryBalanceRepository inventory;
    private final InboundReceiptRepository inbounds;
    private final OutboundWaveRepository waves;
    private final WarehouseZoneRepository zones;
    public WmsController(WmsService service, WarehouseTaskRepository tasks, InventoryBalanceRepository inventory,
                         InboundReceiptRepository inbounds, OutboundWaveRepository waves, WarehouseZoneRepository zones) {
        this.service = service; this.tasks = tasks; this.inventory = inventory;
        this.inbounds = inbounds; this.waves = waves; this.zones = zones;
    }
    @GetMapping("/dashboard") public ApiResponse<DashboardView> dashboard() { return ApiResponse.ok(service.dashboard()); }
    @GetMapping("/tasks") public ApiResponse<List<WarehouseTask>> tasks() { return ApiResponse.ok(tasks.findAllByOrderByDueAtAsc()); }
    @GetMapping("/tasks/mine") public ApiResponse<List<WarehouseTask>> myTasks() { return ApiResponse.ok(service.myTasks()); }
    @PatchMapping("/tasks/{id}") public ApiResponse<WarehouseTask> updateTask(@PathVariable Long id,
        @Valid @RequestBody TaskUpdateRequest request) { return ApiResponse.ok("任务已更新", service.updateTask(id, request)); }
    @PostMapping("/tasks") @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ApiResponse<WarehouseTask> createTask(@Valid @RequestBody TaskCreateRequest request) {
        return ApiResponse.ok("任务已创建", service.createTask(request));
    }
    @GetMapping("/inventory") public ApiResponse<List<InventoryBalance>> inventory() { return ApiResponse.ok(inventory.findAllByOrderByZoneCodeAscLocationCodeAsc()); }
    @GetMapping("/inbounds") public ApiResponse<List<InboundReceipt>> inbounds() { return ApiResponse.ok(inbounds.findAllByOrderByAppointmentAtAsc()); }
    @GetMapping("/waves") public ApiResponse<List<OutboundWave>> waves() { return ApiResponse.ok(waves.findAllByOrderByCutoffAtAsc()); }
    @GetMapping("/zones") public ApiResponse<List<WarehouseZone>> zones() { return ApiResponse.ok(zones.findAllByOrderByZoneCodeAsc()); }
}
