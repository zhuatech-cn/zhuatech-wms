/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.wms.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlottingRecommendationService {
    public Result recommend(Request request) {
        String zone = request.hazardous() ? "CONTROLLED"
            : request.unitWeightKg().compareTo(new BigDecimal("20")) > 0
                || request.unitCubeM3().compareTo(new BigDecimal("0.08")) > 0 ? "BULK"
            : request.dailyPickLines() >= 50 ? "FAST_PICK" : "STANDARD";
        int priority = Math.min(100, request.dailyPickLines() + (request.currentTravelMeters() >= 80 ? 20 : 0)
            + (request.fragile() ? 10 : 0));
        BigDecimal estimatedTravelReduction = "FAST_PICK".equals(zone)
            ? BigDecimal.valueOf(request.currentTravelMeters()).multiply(new BigDecimal("0.45")).setScale(1, RoundingMode.HALF_UP)
            : BigDecimal.ZERO;
        List<String> actions = new ArrayList<>();
        if ("FAST_PICK".equals(zone)) actions.add("迁入黄金拣选带并配置整箱与拆零双库位");
        if ("BULK".equals(zone)) actions.add("分配低位大件库位并校验承重");
        if ("CONTROLLED".equals(zone)) actions.add("进入受控存储区并启用兼容性校验");
        if (request.fragile()) actions.add("设置易碎标识并限制堆码高度");
        if (actions.isEmpty()) actions.add("维持标准库位并按月复核动销等级");
        return new Result(request.skuCode(), zone, priority, estimatedTravelReduction, actions);
    }

    public record Request(@NotBlank String skuCode, @Min(0) int dailyPickLines,
                          @DecimalMin("0.0001") BigDecimal unitCubeM3,
                          @DecimalMin("0.01") BigDecimal unitWeightKg,
                          @Min(0) int currentTravelMeters, boolean hazardous, boolean fragile) {}

    public record Result(String skuCode, String recommendedZone, int relocationPriority,
                         BigDecimal estimatedTravelReductionMeters, List<String> actions) {}
}
