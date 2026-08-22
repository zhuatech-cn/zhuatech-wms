<!-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ -->
<script setup>
import { onMounted, ref } from 'vue'
import { getDashboard, getTasks } from '../../api/wms'

const data = ref({ stats: {}, throughput: [], warnings: [] })
const tasks = ref([])
const typeName = { PICKING: '拣货', PUTAWAY: '上架', COUNTING: '盘点', REPLENISHMENT: '补货', RECEIVING: '收货', PACKING: '复核打包' }
const statusName = { WAITING: '待领取', IN_PROGRESS: '作业中', EXCEPTION: '异常', COMPLETED: '已完成' }
const statusClass = (v) => v === 'COMPLETED' ? 'green' : v === 'EXCEPTION' ? 'red' : v === 'IN_PROGRESS' ? 'amber' : ''
onMounted(async () => { data.value = await getDashboard(); tasks.value = (await getTasks()).slice(0, 5) })
</script>

<template>
  <div>
    <div class="page-head"><div><h1>仓库运营总览</h1><p>上海嘉定一号仓 · 数据更新于 10:26:38</p></div><div class="page-actions"><button class="btn">导出日报</button><button class="btn primary">新建作业任务</button></div></div>
    <section class="kpi-grid">
      <article class="kpi"><div class="kpi-label">今日入库 <i>较昨日</i></div><div class="kpi-value">{{ data.stats.todayInbound?.toLocaleString() }}<small>件</small></div><div class="kpi-delta good">↑ 8.4% · 计划达成 86%</div></article>
      <article class="kpi"><div class="kpi-label">今日出库 <i>较昨日</i></div><div class="kpi-value">{{ data.stats.todayOutbound?.toLocaleString() }}<small>件</small></div><div class="kpi-delta good">↑ 12.1% · 准时率 {{ data.stats.onTimeRate }}%</div></article>
      <article class="kpi"><div class="kpi-label">待执行任务 <i>实时</i></div><div class="kpi-value">{{ data.stats.pendingTasks }}<small>项</small></div><div class="kpi-delta warn">3 项将在 30 分钟内超时</div></article>
      <article class="kpi"><div class="kpi-label">库存总量 <i>在库</i></div><div class="kpi-value">{{ (data.stats.inventoryUnits / 10000).toFixed(2) }}<small>万件</small></div><div class="kpi-delta">账实准确率 {{ data.stats.inventoryAccuracy }}%</div></article>
      <article class="kpi"><div class="kpi-label">库容占用 <i>全仓</i></div><div class="kpi-value">{{ data.stats.occupancyRate }}<small>%</small></div><div class="kpi-delta">作业人员 {{ data.stats.activeOperators }} 人在线</div></article>
    </section>
    <section class="admin-grid">
      <article class="panel"><header class="panel-head"><h2>今日吞吐趋势</h2><div class="chart-legend"><span><i></i>入库</span><span><i class="out"></i>出库</span></div></header><div class="chart"><div v-for="item in data.throughput" :key="item.hour" class="chart-group"><i class="chart-bar" :style="{height: `${item.inbound / 5}px`}"></i><i class="chart-bar out" :style="{height: `${item.outbound / 5}px`}"></i><small>{{ item.hour }}</small></div></div></article>
      <article class="panel"><header class="panel-head"><h2>运营预警</h2><button>查看全部 →</button></header><div class="warning-list"><div v-for="item in data.warnings" :key="item.title" class="warning-item" :class="item.level.toLowerCase()"><i></i><div><strong>{{ item.title }}</strong><p>{{ item.detail }}</p></div><span>{{ item.owner }}</span></div></div></article>
    </section>
    <section class="panel"><header class="panel-head"><h2>现场任务动态</h2><button>进入任务调度 →</button></header><table class="data-table"><thead><tr><th>任务编号</th><th>作业类型</th><th>作业路径</th><th>商品 / 批次</th><th>执行人</th><th>进度</th><th>要求完成</th><th>状态</th></tr></thead><tbody><tr v-for="item in tasks" :key="item.id"><td class="mono">{{ item.taskNo }}</td><td>{{ typeName[item.type] }}</td><td><strong>{{ item.sourceLocation }}</strong><small>至 {{ item.targetLocation || '原库位' }}</small></td><td><strong>{{ item.productName }}</strong><small>{{ item.skuCode }} · {{ item.batchNo }}</small></td><td>{{ item.assignee }}</td><td><div class="progress-track"><i :style="{width: `${item.completedQty / item.plannedQty * 100}%`}"></i></div><small>{{ item.completedQty }}/{{ item.plannedQty }}</small></td><td>{{ item.dueAt.slice(11,16) }}</td><td><span class="status" :class="statusClass(item.status)">{{ statusName[item.status] }}</span></td></tr></tbody></table>
    </section>
  </div>
</template>
