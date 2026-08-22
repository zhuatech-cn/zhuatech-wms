/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms;

import cn.zhuatech.wms.service.SlottingRecommendationService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class SlottingRecommendationServiceTests {
    private final SlottingRecommendationService service = new SlottingRecommendationService();

    @Test void recommendsFastPickForHighVelocitySku() {
        var result = service.recommend(new SlottingRecommendationService.Request("SKU-A01", 82,
            new BigDecimal("0.015"), new BigDecimal("3.2"), 120, false, false));
        assertThat(result.recommendedZone()).isEqualTo("FAST_PICK");
        assertThat(result.estimatedTravelReductionMeters()).isEqualByComparingTo("54.0");
    }

    @Test void hazardousRuleOverridesVelocity() {
        var result = service.recommend(new SlottingRecommendationService.Request("SKU-H01", 95,
            new BigDecimal("0.02"), new BigDecimal("5"), 60, true, false));
        assertThat(result.recommendedZone()).isEqualTo("CONTROLLED");
        assertThat(result.actions()).anyMatch(action -> action.contains("受控存储区"));
    }
}
