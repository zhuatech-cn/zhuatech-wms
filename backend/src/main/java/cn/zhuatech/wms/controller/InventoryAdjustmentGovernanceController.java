/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.controller;

import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.service.InventoryAdjustmentGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/wms")
public class InventoryAdjustmentGovernanceController {
    private final InventoryAdjustmentGovernanceService service;
    public InventoryAdjustmentGovernanceController(InventoryAdjustmentGovernanceService service) { this.service = service; }

    @PostMapping("/inventory-adjustment")
    public ApiResponse<InventoryAdjustmentGovernanceService.Assessment> assess(
            @Valid @RequestBody InventoryAdjustmentGovernanceService.Request request) {
        return ApiResponse.ok("库存差异调整评估完成", service.assess(request));
    }
}
