/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.controller;
import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.service.AiLaborForecastService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/wms/ai")
public class AiLaborForecastController {
    private final AiLaborForecastService service;
    public AiLaborForecastController(AiLaborForecastService service) { this.service = service; }
    @PostMapping("/labor-forecast")
    public ApiResponse<AiLaborForecastService.Result> forecast(@Valid @RequestBody AiLaborForecastService.Request request) {
        return ApiResponse.ok(service.forecast(request));
    }
}
