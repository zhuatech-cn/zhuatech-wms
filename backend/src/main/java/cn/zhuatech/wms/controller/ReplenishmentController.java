/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.wms.controller;

import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.service.ReplenishmentPlanningService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wms")
public class ReplenishmentController {
    private final ReplenishmentPlanningService service;
    public ReplenishmentController(ReplenishmentPlanningService service) { this.service = service; }

    @PostMapping("/replenishment-plan")
    public ApiResponse<ReplenishmentPlanningService.Result> plan(@Valid @RequestBody ReplenishmentPlanningService.Request request) {
        return ApiResponse.ok(service.plan(request));
    }
}
