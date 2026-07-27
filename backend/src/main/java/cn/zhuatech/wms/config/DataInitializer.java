/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.wms.config;

import cn.zhuatech.wms.model.*;
import cn.zhuatech.wms.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.*;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository users;
    private final WarehouseTaskRepository tasks;
    private final InventoryBalanceRepository inventory;
    private final InboundReceiptRepository inbounds;
    private final OutboundWaveRepository waves;
    private final WarehouseZoneRepository zones;
    private final PasswordEncoder encoder;
    public DataInitializer(UserRepository users, WarehouseTaskRepository tasks, InventoryBalanceRepository inventory,
                           InboundReceiptRepository inbounds, OutboundWaveRepository waves,
                           WarehouseZoneRepository zones, PasswordEncoder encoder) {
        this.users = users; this.tasks = tasks; this.inventory = inventory; this.inbounds = inbounds;
        this.waves = waves; this.zones = zones; this.encoder = encoder;
    }
    @Override public void run(String... args) {
        if (users.count() > 0) return;
        users.save(new UserAccount("admin", encoder.encode("ZhuaTech@2026"), "系统管理员", UserAccount.Role.ADMIN, "全部仓库"));
        users.save(new UserAccount("supervisor", encoder.encode("Demo@2026"), "周主管", UserAccount.Role.SUPERVISOR, "上海嘉定一号仓"));
        users.save(new UserAccount("operator", encoder.encode("Demo@2026"), "陈师傅", UserAccount.Role.OPERATOR, "上海嘉定一号仓"));
        users.save(new UserAccount("viewer", encoder.encode("Demo@2026"), "经营分析员", UserAccount.Role.VIEWER, "全部仓库"));
        LocalDateTime now = LocalDateTime.now();
        tasks.save(new WarehouseTask("WT202607270081", WarehouseTask.Type.PICKING, WarehouseTask.Status.IN_PROGRESS, WarehouseTask.Priority.URGENT, "上海嘉定一号仓", "A-03", "A-03-02-04", "PK-01", "SKU-SN-1008", "ZH 智能温湿度传感器", "B2026071802", 48, 28, "陈师傅", now.plusMinutes(34)));
        tasks.save(new WarehouseTask("WT202607270079", WarehouseTask.Type.PUTAWAY, WarehouseTask.Status.WAITING, WarehouseTask.Priority.HIGH, "上海嘉定一号仓", "B-01", "RCV-02", "B-01-04-02", "SKU-AC-8802", "工业级无线网关", "B2026072601", 36, 0, "陈师傅", now.plusMinutes(58)));
        tasks.save(new WarehouseTask("WT202607270076", WarehouseTask.Type.COUNTING, WarehouseTask.Status.WAITING, WarehouseTask.Priority.NORMAL, "上海嘉定一号仓", "A-02", "A-02-01-01", null, "SKU-PD-240718", "便携式数据采集终端", "B2026061503", 62, 0, "陈师傅", now.plusHours(2)));
        tasks.save(new WarehouseTask("WT202607270072", WarehouseTask.Type.REPLENISHMENT, WarehouseTask.Status.EXCEPTION, WarehouseTask.Priority.URGENT, "上海嘉定一号仓", "A-03", "A-08-06-01", "A-03-02-04", "SKU-SN-1008", "工业扫码枪支架", "B2026070108", 60, 18, "刘海", now.minusMinutes(12)));
        tasks.save(new WarehouseTask("WT202607270068", WarehouseTask.Type.RECEIVING, WarehouseTask.Status.COMPLETED, WarehouseTask.Priority.NORMAL, "上海嘉定一号仓", "RCV", "DOCK-03", "QC-01", "SKU-CT-1102", "六类屏蔽网线 10m", "B2026072504", 120, 120, "王芳", now.minusMinutes(20)));
        tasks.save(new WarehouseTask("WT202607270061", WarehouseTask.Type.PACKING, WarehouseTask.Status.IN_PROGRESS, WarehouseTask.Priority.HIGH, "上海嘉定一号仓", "PK", "PK-04", "DOCK-06", "SKU-MIX", "波次 WV20260727012 混合商品", null, 86, 51, "赵磊", now.plusMinutes(43)));

        inventory.save(new InventoryBalance("SKU-SN-1008", "工业扫码枪支架", "上海嘉定一号仓", "A-03", "A-03-02-04", "B2026070108", InventoryBalance.QualityStatus.QUALIFIED, 18, 12, 60, "件", null, now.minusDays(7)));
        inventory.save(new InventoryBalance("SKU-PD-240718", "便携式数据采集终端", "上海嘉定一号仓", "A-02", "A-02-01-01", "B2026061503", InventoryBalance.QualityStatus.HOLD, 59, 0, 0, "台", null, now.minusDays(16)));
        inventory.save(new InventoryBalance("SKU-AC-8802", "工业级无线网关", "上海嘉定一号仓", "B-01", "B-01-04-02", "B2026072601", InventoryBalance.QualityStatus.QUALIFIED, 144, 26, 36, "台", null, now.minusDays(2)));
        inventory.save(new InventoryBalance("SKU-CT-1102", "六类屏蔽网线 10m", "上海嘉定一号仓", "B-02", "B-02-08-03", "B2026072504", InventoryBalance.QualityStatus.QUALIFIED, 1280, 420, 0, "条", null, now.minusDays(4)));
        inventory.save(new InventoryBalance("SKU-FD-0302", "低温环境标签纸", "上海嘉定一号仓", "C-01", "C-01-03-02", "B2026071206", InventoryBalance.QualityStatus.QUALIFIED, 386, 64, 240, "卷", LocalDate.now().plusMonths(18), now.minusDays(3)));

        inbounds.save(new InboundReceipt("IN202607270031", "苏州安智设备有限公司", "采购入库", "上海嘉定一号仓", "DOCK-02", InboundReceipt.Status.RECEIVING, 8, 486, 312, now.minusMinutes(35), "王芳"));
        inbounds.save(new InboundReceipt("IN202607270034", "杭州云联电子有限公司", "采购入库", "上海嘉定一号仓", "DOCK-03", InboundReceipt.Status.ARRIVED, 5, 260, 0, now.plusMinutes(10), "李杰"));
        inbounds.save(new InboundReceipt("IN202607270038", "华东售后中心", "退货入库", "上海嘉定一号仓", "DOCK-01", InboundReceipt.Status.QC_HOLD, 12, 48, 48, now.plusMinutes(42), "孙静"));

        waves.save(new OutboundWave("WV20260727012", "上海嘉定一号仓", OutboundWave.Status.PICKING, 68, 42, 386, 248, "顺丰速运", now.plusMinutes(48), "周主管"));
        waves.save(new OutboundWave("WV20260727015", "上海嘉定一号仓", OutboundWave.Status.RELEASED, 42, 31, 214, 0, "京东物流", now.plusHours(2), "周主管"));
        waves.save(new OutboundWave("WV20260727009", "上海嘉定一号仓", OutboundWave.Status.PACKING, 55, 38, 298, 273, "中通快运", now.plusMinutes(24), "郑敏"));

        zones.save(new WarehouseZone("RCV", "收货暂存区", "上海嘉定一号仓", WarehouseZone.Type.RECEIVING, WarehouseZone.Status.BUSY, 42, 31, 74, "常温"));
        zones.save(new WarehouseZone("A-01", "轻小件拣选区", "上海嘉定一号仓", WarehouseZone.Type.PICKING, WarehouseZone.Status.NORMAL, 480, 356, 74, "常温"));
        zones.save(new WarehouseZone("A-03", "高频拣选区", "上海嘉定一号仓", WarehouseZone.Type.PICKING, WarehouseZone.Status.BUSY, 360, 319, 89, "常温"));
        zones.save(new WarehouseZone("B-01", "整箱存储区", "上海嘉定一号仓", WarehouseZone.Type.STORAGE, WarehouseZone.Status.NORMAL, 620, 468, 75, "常温"));
        zones.save(new WarehouseZone("C-01", "恒温耗材区", "上海嘉定一号仓", WarehouseZone.Type.FROZEN, WarehouseZone.Status.BUSY, 180, 166, 92, "2–8°C"));
        zones.save(new WarehouseZone("RTN", "退货处理区", "上海嘉定一号仓", WarehouseZone.Type.RETURNS, WarehouseZone.Status.NORMAL, 80, 29, 36, "常温"));
    }
}
