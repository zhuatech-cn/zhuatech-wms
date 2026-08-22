/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplenishmentPlanningService {
    public Result plan(Request request) {
        int required = Math.max(0, request.safetyStock() + request.expectedDemand() - request.pickFaceQty());
        int replenishQty = Math.min(request.reserveQty(), required);
        boolean shortage = replenishQty < required;
        String urgency = request.pickFaceQty() < request.expectedDemand() && request.leadMinutes() >= 60
            ? "CRITICAL" : required > 0 ? "NORMAL" : "NONE";
        int targetMinutes = "CRITICAL".equals(urgency) ? 30 : "NORMAL".equals(urgency) ? 90 : 0;
        List<String> actions = new ArrayList<>();
        if (replenishQty > 0) actions.add("生成从储备区到拣选面的补货任务");
        if (shortage) actions.add("标记储备库存缺口并通知库存控制员");
        if ("CRITICAL".equals(urgency)) actions.add("将任务提升为加急并锁定作业人员");
        if (actions.isEmpty()) actions.add("当前拣选面库存充足，无需补货");
        return new Result(request.sku(), required, replenishQty, shortage, urgency, targetMinutes, actions);
    }

    public record Request(@NotBlank String sku, @Min(0) int pickFaceQty,
                          @Min(0) int safetyStock, @Min(0) int expectedDemand,
                          @Min(0) int reserveQty, @Min(0) int leadMinutes) {}
    public record Result(String sku, int requiredQty, int replenishQty, boolean shortage,
                         String urgency, int targetMinutes, List<String> actions) {}
}
