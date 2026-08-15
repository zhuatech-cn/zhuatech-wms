/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.wms.controller;

import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.service.CycleCountPlannerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wms/insights")
public class CycleCountPlannerController {
    private final CycleCountPlannerService service;
    public CycleCountPlannerController(CycleCountPlannerService service) { this.service = service; }

    @PostMapping("/cycle-count")
    public ApiResponse<CycleCountPlannerService.Result> plan(@Valid @RequestBody CycleCountPlannerService.Request request) {
        return ApiResponse.ok(service.plan(request));
    }
}
