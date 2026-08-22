/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import cn.zhuatech.wms.ai.OpenAiCompatibleGateway;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiLaborForecastService {
    private final OpenAiCompatibleGateway gateway;
    public AiLaborForecastService(OpenAiCompatibleGateway gateway) { this.gateway = gateway; }

    public Result forecast(Request request) {
        BigDecimal manualFactor = BigDecimal.ONE.subtract(request.automationRate().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
        BigDecimal pickSeconds = BigDecimal.valueOf(request.orderLines()).multiply(request.averagePickSeconds()).multiply(manualFactor);
        BigDecimal urgentSeconds = BigDecimal.valueOf(request.urgentOrders()).multiply(BigDecimal.valueOf(180));
        BigDecimal laborHours = pickSeconds.add(urgentSeconds).divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);
        int requiredWorkers = laborHours.multiply(BigDecimal.valueOf(60))
            .divide(BigDecimal.valueOf(request.shiftMinutes()), 0, RoundingMode.CEILING).intValue();
        int gap = Math.max(0, requiredWorkers - request.availableWorkers());
        List<String> actions = new ArrayList<>();
        if (gap > 0) actions.add("补充 %d 名拣选人员或启用跨区支援".formatted(gap));
        if (request.urgentOrders() > request.orders() * 0.15) actions.add("建立急单专用波次，减少普通订单被打断");
        if (request.orderLines() > request.orders() * 8) actions.add("按多行订单特征优化拣选路径和容器配置");
        if (actions.isEmpty()) actions.add("当前班次人力能够覆盖预测工作量");

        String context = "订单=%d，行数=%d，急单=%d，预测工时=%s，需要人员=%d，缺口=%d"
            .formatted(request.orders(), request.orderLines(), request.urgentOrders(), laborHours, requiredWorkers, gap);
        var enhanced = gateway.complete("你是仓储运营助手，请给出班次用工、波次和跨区支援建议。", context);
        var metadata = gateway.metadata();
        return new Result(laborHours, requiredWorkers, gap, gap > 3 ? "CRITICAL" : gap > 0 ? "SHORTAGE" : "COVERED",
            enhanced.orElse(String.join("；", actions)), List.copyOf(actions),
            enhanced.isPresent() ? "EXTERNAL_MODEL" : "LOCAL_RULES", metadata.provider(), metadata.model());
    }

    public record Request(@Min(1) int orders, @Min(1) int orderLines, @Min(0) int units,
                          @Min(0) int urgentOrders, @DecimalMin("1") BigDecimal averagePickSeconds,
                          @Min(0) int availableWorkers, @Min(1) int shiftMinutes,
                          @DecimalMin("0") @DecimalMax("100") BigDecimal automationRate) {}
    public record Result(BigDecimal requiredLaborHours, int requiredWorkers, int workerGap, String status,
                         String recommendation, List<String> actions, String aiMode, String provider, String model) {}
}
