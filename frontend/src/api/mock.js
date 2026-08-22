/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
export const dashboard = {
  stats: { todayInbound: 1168, todayOutbound: 1698, pendingTasks: 12, exceptionTasks: 3, inventoryUnits: 126840, occupancyRate: 78, inventoryAccuracy: 99.72, onTimeRate: 97.8, activeOperators: 26 },
  throughput: [
    { hour: '08:00', inbound: 186, outbound: 92 }, { hour: '10:00', inbound: 342, outbound: 268 },
    { hour: '12:00', inbound: 278, outbound: 316 }, { hour: '14:00', inbound: 426, outbound: 388 },
    { hour: '16:00', inbound: 391, outbound: 452 }, { hour: '18:00', inbound: 208, outbound: 337 },
  ],
  warnings: [
    { level: 'HIGH', title: 'A-03 巷道补货延迟', detail: '拣选位剩余 18 件，预计 24 分钟后缺货', owner: '补货组' },
    { level: 'MEDIUM', title: '冷链区库容接近上限', detail: 'C 区占用率 92%，今晚有 2 车预约到仓', owner: '入库主管' },
    { level: 'LOW', title: '盘点差异待复核', detail: 'SKU-PD-240718 差异 3 件', owner: '库存组' },
  ],
}

export const tasks = [
  { id: 1, taskNo: 'WT202607270081', type: 'PICKING', status: 'IN_PROGRESS', priority: 'URGENT', zoneCode: 'A-03', sourceLocation: 'A-03-02-04', targetLocation: 'PK-01', skuCode: 'SKU-SN-1008', productName: 'ZH 智能温湿度传感器', batchNo: 'B2026071802', plannedQty: 48, completedQty: 28, assignee: '陈师傅', dueAt: '2026-07-27T10:34:00' },
  { id: 2, taskNo: 'WT202607270079', type: 'PUTAWAY', status: 'WAITING', priority: 'HIGH', zoneCode: 'B-01', sourceLocation: 'RCV-02', targetLocation: 'B-01-04-02', skuCode: 'SKU-AC-8802', productName: '工业级无线网关', batchNo: 'B2026072601', plannedQty: 36, completedQty: 0, assignee: '陈师傅', dueAt: '2026-07-27T10:58:00' },
  { id: 3, taskNo: 'WT202607270076', type: 'COUNTING', status: 'WAITING', priority: 'NORMAL', zoneCode: 'A-02', sourceLocation: 'A-02-01-01', skuCode: 'SKU-PD-240718', productName: '便携式数据采集终端', batchNo: 'B2026061503', plannedQty: 62, completedQty: 0, assignee: '陈师傅', dueAt: '2026-07-27T12:00:00' },
  { id: 4, taskNo: 'WT202607270072', type: 'REPLENISHMENT', status: 'EXCEPTION', priority: 'URGENT', zoneCode: 'A-03', sourceLocation: 'A-08-06-01', targetLocation: 'A-03-02-04', skuCode: 'SKU-SN-1008', productName: '工业扫码枪支架', batchNo: 'B2026070108', plannedQty: 60, completedQty: 18, assignee: '刘海', dueAt: '2026-07-27T09:48:00' },
  { id: 5, taskNo: 'WT202607270068', type: 'RECEIVING', status: 'COMPLETED', priority: 'NORMAL', zoneCode: 'RCV', sourceLocation: 'DOCK-03', targetLocation: 'QC-01', skuCode: 'SKU-CT-1102', productName: '六类屏蔽网线 10m', batchNo: 'B2026072504', plannedQty: 120, completedQty: 120, assignee: '王芳', dueAt: '2026-07-27T09:30:00' },
  { id: 6, taskNo: 'WT202607270061', type: 'PACKING', status: 'IN_PROGRESS', priority: 'HIGH', zoneCode: 'PK', sourceLocation: 'PK-04', targetLocation: 'DOCK-06', skuCode: 'SKU-MIX', productName: '波次 WV20260727012 混合商品', plannedQty: 86, completedQty: 51, assignee: '赵磊', dueAt: '2026-07-27T10:43:00' },
]

export const inventory = [
  { skuCode: 'SKU-SN-1008', productName: '工业扫码枪支架', zoneCode: 'A-03', locationCode: 'A-03-02-04', batchNo: 'B2026070108', qualityStatus: 'QUALIFIED', availableQty: 18, allocatedQty: 12, inboundQty: 60, unit: '件', lastCountAt: '2026-07-20T16:20:00' },
  { skuCode: 'SKU-PD-240718', productName: '便携式数据采集终端', zoneCode: 'A-02', locationCode: 'A-02-01-01', batchNo: 'B2026061503', qualityStatus: 'HOLD', availableQty: 59, allocatedQty: 0, inboundQty: 0, unit: '台', lastCountAt: '2026-07-11T14:30:00' },
  { skuCode: 'SKU-AC-8802', productName: '工业级无线网关', zoneCode: 'B-01', locationCode: 'B-01-04-02', batchNo: 'B2026072601', qualityStatus: 'QUALIFIED', availableQty: 144, allocatedQty: 26, inboundQty: 36, unit: '台', lastCountAt: '2026-07-25T10:10:00' },
  { skuCode: 'SKU-CT-1102', productName: '六类屏蔽网线 10m', zoneCode: 'B-02', locationCode: 'B-02-08-03', batchNo: 'B2026072504', qualityStatus: 'QUALIFIED', availableQty: 1280, allocatedQty: 420, inboundQty: 0, unit: '条', lastCountAt: '2026-07-23T11:36:00' },
  { skuCode: 'SKU-FD-0302', productName: '低温环境标签纸', zoneCode: 'C-01', locationCode: 'C-01-03-02', batchNo: 'B2026071206', qualityStatus: 'QUALIFIED', availableQty: 386, allocatedQty: 64, inboundQty: 240, unit: '卷', lastCountAt: '2026-07-24T15:12:00' },
]

export const inbounds = [
  { receiptNo: 'IN202607270031', supplierName: '苏州安智设备有限公司', sourceType: '采购入库', dockCode: 'DOCK-02', status: 'RECEIVING', lineCount: 8, plannedQty: 486, receivedQty: 312, appointmentAt: '2026-07-27T09:25:00', operatorName: '王芳' },
  { receiptNo: 'IN202607270034', supplierName: '杭州云联电子有限公司', sourceType: '采购入库', dockCode: 'DOCK-03', status: 'ARRIVED', lineCount: 5, plannedQty: 260, receivedQty: 0, appointmentAt: '2026-07-27T10:10:00', operatorName: '李杰' },
  { receiptNo: 'IN202607270038', supplierName: '华东售后中心', sourceType: '退货入库', dockCode: 'DOCK-01', status: 'QC_HOLD', lineCount: 12, plannedQty: 48, receivedQty: 48, appointmentAt: '2026-07-27T10:42:00', operatorName: '孙静' },
]

export const waves = [
  { waveNo: 'WV20260727012', status: 'PICKING', orderCount: 68, skuCount: 42, pieceCount: 386, pickedQty: 248, carrierName: '顺丰速运', cutoffAt: '2026-07-27T10:48:00', ownerName: '周主管' },
  { waveNo: 'WV20260727015', status: 'RELEASED', orderCount: 42, skuCount: 31, pieceCount: 214, pickedQty: 0, carrierName: '京东物流', cutoffAt: '2026-07-27T12:00:00', ownerName: '周主管' },
  { waveNo: 'WV20260727009', status: 'PACKING', orderCount: 55, skuCount: 38, pieceCount: 298, pickedQty: 273, carrierName: '中通快运', cutoffAt: '2026-07-27T10:24:00', ownerName: '郑敏' },
]

export const zones = [
  { zoneCode: 'RCV', zoneName: '收货暂存区', type: 'RECEIVING', status: 'BUSY', locationCount: 42, usedLocationCount: 31, occupancyRate: 74, environment: '常温' },
  { zoneCode: 'A-01', zoneName: '轻小件拣选区', type: 'PICKING', status: 'NORMAL', locationCount: 480, usedLocationCount: 356, occupancyRate: 74, environment: '常温' },
  { zoneCode: 'A-03', zoneName: '高频拣选区', type: 'PICKING', status: 'BUSY', locationCount: 360, usedLocationCount: 319, occupancyRate: 89, environment: '常温' },
  { zoneCode: 'B-01', zoneName: '整箱存储区', type: 'STORAGE', status: 'NORMAL', locationCount: 620, usedLocationCount: 468, occupancyRate: 75, environment: '常温' },
  { zoneCode: 'C-01', zoneName: '恒温耗材区', type: 'FROZEN', status: 'BUSY', locationCount: 180, usedLocationCount: 166, occupancyRate: 92, environment: '2–8°C' },
  { zoneCode: 'RTN', zoneName: '退货处理区', type: 'RETURNS', status: 'NORMAL', locationCount: 80, usedLocationCount: 29, occupancyRate: 36, environment: '常温' },
]
