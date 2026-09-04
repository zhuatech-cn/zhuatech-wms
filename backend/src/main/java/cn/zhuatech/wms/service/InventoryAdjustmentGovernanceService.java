/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryAdjustmentGovernanceService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.countTaskClosed()) blockers.add("原始盘点任务尚未完成");
        if (!request.independentRecountComplete()) blockers.add("库存差异未完成独立复盘");
        if (!request.lotSerialTraceable()) blockers.add("批次、序列号或容器追溯不完整");
        if (!request.qualityStatusResolved()) blockers.add("冻结、不合格或待检库存状态未处理");
        if (!request.stockOwnershipConfirmed()) blockers.add("自有、寄售或客户库存权属未确认");
        if (!request.negativeStockPrevented()) blockers.add("调整后可能形成负库存");
        if (!request.valueWithinAuthority()) blockers.add("差异价值超出当前审批权限");
        if (!request.costImpactReviewed()) blockers.add("移动平均价或库存成本影响未复核");
        if (!request.financeApproved()) blockers.add("财务尚未批准库存价值调整");
        if (!request.businessOwnerApproved()) blockers.add("仓库或货主负责人尚未批准");
        if (!request.makerCheckerSeparated()) blockers.add("盘点、调整与复核未职责分离");
        if (!request.auditReady()) blockers.add("盘点、复盘、审批及过账证据链不完整");
        if (!request.rootCauseAssigned()) actions.add("登记差异根因、责任人和整改期限");
        if (!request.cycleCountPolicyUpdated()) actions.add("按差异风险更新循环盘点策略");
        if (!request.reservationsRecalculated()) actions.add("重算分配、可用量和补货建议");
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED : !actions.isEmpty() ? Decision.REVIEW : Decision.POST;
        return new Assessment(request.adjustmentNo(), request.sku(), request.absoluteQuantity(),
                decision, List.copyOf(blockers), List.copyOf(actions));
    }

    public record Request(@NotBlank String adjustmentNo, @NotBlank String sku, @Positive int absoluteQuantity,
                          boolean countTaskClosed, boolean independentRecountComplete,
                          boolean lotSerialTraceable, boolean qualityStatusResolved,
                          boolean stockOwnershipConfirmed, boolean negativeStockPrevented,
                          boolean valueWithinAuthority, boolean costImpactReviewed,
                          boolean financeApproved, boolean businessOwnerApproved,
                          boolean makerCheckerSeparated, boolean auditReady,
                          boolean rootCauseAssigned, boolean cycleCountPolicyUpdated,
                          boolean reservationsRecalculated) {}
    public record Assessment(String adjustmentNo, String sku, int absoluteQuantity, Decision decision,
                             List<String> blockers, List<String> actions) {}
    public enum Decision { POST, REVIEW, BLOCKED }
}
