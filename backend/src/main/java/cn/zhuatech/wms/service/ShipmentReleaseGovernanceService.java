/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipmentReleaseGovernanceService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.inventoryAllocated()) blockers.add("出库库存尚未完成锁定");
        if (!request.pickingComplete()) blockers.add("拣选任务未全部完成");
        if (!request.packingVerified()) blockers.add("复核装箱未完成");
        if (!request.carrierBooked()) blockers.add("承运商与提货计划未确认");
        if (request.dangerousGoods() && !request.dangerousGoodsDocumentsReady()) blockers.add("危险品运输文件不完整");
        if (request.coldChain() && !request.coldChainReady()) blockers.add("冷链设备或温控记录未就绪");
        if (request.openExceptions() > 0) actions.add("关闭 " + request.openExceptions() + " 个出库异常");
        if (request.minutesToCutoff() < 30) actions.add("临近截单时间，升级调度优先级");

        Decision decision = !blockers.isEmpty() ? Decision.HOLD
                : !actions.isEmpty() ? Decision.REVIEW : Decision.RELEASE;
        return new Assessment(request.shipmentNo(), decision, List.copyOf(blockers), List.copyOf(actions));
    }

    public record Request(@NotBlank String shipmentNo, boolean inventoryAllocated,
                          boolean pickingComplete, boolean packingVerified, boolean carrierBooked,
                          boolean dangerousGoods, boolean dangerousGoodsDocumentsReady,
                          boolean coldChain, boolean coldChainReady,
                          @Min(0) int openExceptions, @Min(0) int minutesToCutoff) {}
    public record Assessment(String shipmentNo, Decision decision, List<String> blockers,
                             List<String> actions) {}
    public enum Decision { RELEASE, REVIEW, HOLD }
}
