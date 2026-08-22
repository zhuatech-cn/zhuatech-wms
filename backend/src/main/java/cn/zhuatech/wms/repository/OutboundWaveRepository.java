/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.repository;
import cn.zhuatech.wms.model.OutboundWave;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OutboundWaveRepository extends JpaRepository<OutboundWave, Long> {
    List<OutboundWave> findAllByOrderByCutoffAtAsc();
}
