/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.controller;

import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.service.ShipmentReleaseGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enterprise/wms")
public class ShipmentReleaseGovernanceController {
    private final ShipmentReleaseGovernanceService service;
    public ShipmentReleaseGovernanceController(ShipmentReleaseGovernanceService service) { this.service = service; }

    @PostMapping("/shipment-release")
    public ApiResponse<ShipmentReleaseGovernanceService.Assessment> assess(
            @Valid @RequestBody ShipmentReleaseGovernanceService.Request request) {
        return ApiResponse.ok("出库放行评估完成", service.assess(request));
    }
}
