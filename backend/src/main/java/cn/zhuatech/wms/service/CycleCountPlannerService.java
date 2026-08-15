/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.wms.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CycleCountPlannerService {
    public Result plan(Request request) {
        int score = switch (request.abcClass()) { case "A" -> 30; case "B" -> 18; default -> 8; };
        score += Math.min(30, request.varianceRatePercent() * 3);
        score += Math.min(20, request.dailyMovements() / 5);
        score += Math.min(20, request.daysSinceLastCount() / 5);
        if (request.stockValue().compareTo(new BigDecimal("100000")) >= 0) score += 10;
        score = Math.min(100, score);

        String priority = score >= 70 ? "URGENT" : score >= 40 ? "PLANNED" : "ROUTINE";
        int dueInDays = priority.equals("URGENT") ? 1 : priority.equals("PLANNED") ? 7 : 30;
        List<String> actions = new ArrayList<>();
        if (request.varianceRatePercent() >= 3) actions.add("复核近期收发记录并执行冻结盘点");
        if (request.dailyMovements() >= 50) actions.add("避开作业高峰并按库位分批循环盘点");
        if (request.daysSinceLastCount() >= 60) actions.add("将该 SKU 纳入本周期必盘清单");
        if (actions.isEmpty()) actions.add("按常规循环盘点计划执行抽盘");
        return new Result(request.skuCode(), score, priority, dueInDays, actions);
    }

    public record Request(@NotBlank String skuCode,
                          @Pattern(regexp = "A|B|C") String abcClass,
                          @Min(0) @Max(100) int varianceRatePercent,
                          @Min(0) int dailyMovements, @Min(0) int daysSinceLastCount,
                          @DecimalMin("0") BigDecimal stockValue) {}
    public record Result(String skuCode, int riskScore, String priority, int dueInDays, List<String> actions) {}
}
