/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.wms.controller;

import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.service.ReplenishmentPlanningService;
import cn.zhuatech.wms.service.WaveReleaseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wms")
public class ReplenishmentController {
    private final ReplenishmentPlanningService service;
    private final WaveReleaseService waveReleaseService;
    public ReplenishmentController(ReplenishmentPlanningService service, WaveReleaseService waveReleaseService) {
        this.service = service;
        this.waveReleaseService = waveReleaseService;
    }

    @PostMapping("/replenishment-plan")
    public ApiResponse<ReplenishmentPlanningService.Result> plan(@Valid @RequestBody ReplenishmentPlanningService.Request request) {
        return ApiResponse.ok(service.plan(request));
    }

    @PostMapping("/wave-release-check")
    public ApiResponse<WaveReleaseService.Result> checkWave(@Valid @RequestBody WaveReleaseService.Request request) {
        return ApiResponse.ok(waveReleaseService.check(request));
    }
}
