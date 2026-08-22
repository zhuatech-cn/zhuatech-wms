/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.controller;

import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.service.SlottingRecommendationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wms/insights")
public class SlottingRecommendationController {
    private final SlottingRecommendationService service;
    public SlottingRecommendationController(SlottingRecommendationService service) { this.service = service; }

    @PostMapping("/slotting")
    public ApiResponse<SlottingRecommendationService.Result> recommend(
        @Valid @RequestBody SlottingRecommendationService.Request request) {
        return ApiResponse.ok(service.recommend(request));
    }
}
