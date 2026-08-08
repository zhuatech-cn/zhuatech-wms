/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.wms.service;

import cn.zhuatech.wms.common.BusinessException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class WaveReleaseService {
    public Result check(Request request) {
        if (request.pickedPieces() > request.totalPieces()) throw new BusinessException("已拣件数不能超过波次总件数");
        BigDecimal completionRate = request.totalPieces() == 0 ? BigDecimal.ZERO
            : BigDecimal.valueOf(request.pickedPieces()).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(request.totalPieces()), 1, RoundingMode.HALF_UP);
        boolean blocked = !request.dockReady() || request.exceptionTasks() >= 3
            || (request.minutesToCutoff() <= 30 && completionRate.compareTo(BigDecimal.valueOf(70)) < 0);
        boolean risk = request.exceptionTasks() > 0 || request.minutesToCutoff() <= 45
            || completionRate.compareTo(BigDecimal.valueOf(95)) < 0;
        String decision = blocked ? "BLOCKED" : risk ? "RISK" : "READY";
        List<String> actions = new ArrayList<>();
        if (!request.dockReady()) actions.add("确认集货月台和装车资源后再释放");
        if (request.exceptionTasks() > 0) actions.add("清零异常任务或指定主管接受风险");
        if (completionRate.compareTo(BigDecimal.valueOf(95)) < 0) actions.add("按截单时间优先完成剩余拣选与复核");
        if (actions.isEmpty()) actions.add("波次满足发运条件，可进入装车交接");
        return new Result(request.waveNo(), completionRate, request.totalPieces() - request.pickedPieces(),
            decision, actions, "READY".equals(decision));
    }

    public record Request(@NotBlank String waveNo,
                          @Min(0) int totalPieces,
                          @Min(0) int pickedPieces,
                          @Min(0) int exceptionTasks,
                          @Min(0) int minutesToCutoff,
                          boolean dockReady) {}
    public record Result(String waveNo, BigDecimal completionRate, int remainingPieces,
                         String decision, List<String> actions, boolean releasable) {}
}
