/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.wms.repository;
import cn.zhuatech.wms.model.WarehouseZone;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface WarehouseZoneRepository extends JpaRepository<WarehouseZone, Long> {
    List<WarehouseZone> findAllByOrderByZoneCodeAsc();
}
