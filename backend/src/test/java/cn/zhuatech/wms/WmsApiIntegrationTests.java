/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.wms;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WmsApiIntegrationTests {
    @Autowired MockMvc mvc;

    @Test void operatorCanReadAndUpdateWarehouseTasks() throws Exception {
        String token = login("operator", "Demo@2026", "OPERATOR");
        mvc.perform(get("/api/wms/tasks/mine").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[?(@.taskNo == 'WT202607270081')]").isNotEmpty());
        mvc.perform(patch("/api/wms/tasks/1").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"IN_PROGRESS\",\"completedQty\":32,\"remark\":\"拣选进度更新\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.completedQty").value(32));
    }

    @Test void supervisorCanReadManagementDashboard() throws Exception {
        String token = login("supervisor", "Demo@2026", "SUPERVISOR");
        mvc.perform(get("/api/wms/dashboard").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.stats.inventoryAccuracy").value(99.72));
        mvc.perform(get("/api/wms/inventory").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data[0].locationCode").isNotEmpty());
    }

    @Test void anonymousRequestIsRejected() throws Exception {
        mvc.perform(get("/api/wms/dashboard")).andExpect(status().isForbidden());
    }

    @Test void supervisorCanGenerateReplenishmentPlan() throws Exception {
        String token = login("supervisor", "Demo@2026", "SUPERVISOR");
        mvc.perform(post("/api/wms/replenishment-plan").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sku\":\"FD-10021\",\"pickFaceQty\":30,\"safetyStock\":20,\"expectedDemand\":80,\"reserveQty\":100,\"leadMinutes\":90}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.requiredQty").value(70))
            .andExpect(jsonPath("$.data.replenishQty").value(70))
            .andExpect(jsonPath("$.data.urgency").value("CRITICAL"));
    }

    @Test void supervisorCanCheckWaveReleaseReadiness() throws Exception {
        String token = login("supervisor", "Demo@2026", "SUPERVISOR");
        mvc.perform(post("/api/wms/wave-release-check").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"waveNo\":\"WV20260727012\",\"totalPieces\":386,\"pickedPieces\":248,\"exceptionTasks\":3,\"minutesToCutoff\":24,\"dockReady\":true}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.completionRate").value(64.2))
            .andExpect(jsonPath("$.data.remainingPieces").value(138))
            .andExpect(jsonPath("$.data.decision").value("BLOCKED"))
            .andExpect(jsonPath("$.data.releasable").value(false));
    }

    private String login(String username, String password, String role) throws Exception {
        String body = mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.user.role").value(role))
            .andReturn().getResponse().getContentAsString();
        return JsonPath.read(body, "$.data.token");
    }
}
