/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.repository;
import cn.zhuatech.wms.model.WarehouseTask;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface WarehouseTaskRepository extends JpaRepository<WarehouseTask, Long> {
    List<WarehouseTask> findAllByOrderByDueAtAsc();
    long countByStatus(WarehouseTask.Status status);
    List<WarehouseTask> findByAssigneeOrderByDueAtAsc(String assignee);
}
