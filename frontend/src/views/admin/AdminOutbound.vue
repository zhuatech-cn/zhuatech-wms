<!-- Copyright 2026 上海如静知华信息科技有限公司 -->
<script setup>
import { onMounted, ref } from 'vue'
import { checkWaveRelease, getWaves } from '../../api/wms'

const list = ref([])
const checking = ref('')
const releaseResult = ref(null)
const statusName = { PICKING: '拣选中', RELEASED: '已释放', PACKING: '复核打包' }
const decisionName = { READY: '可以放行', RISK: '存在风险', BLOCKED: '暂缓放行' }

const inspectWave = async wave => {
  checking.value = wave.waveNo
  try {
    releaseResult.value = await checkWaveRelease({
      waveNo: wave.waveNo,
      totalPieces: wave.pieceCount,
      pickedPieces: wave.pickedQty,
      exceptionTasks: wave.status === 'PICKING' ? 3 : wave.status === 'PACKING' ? 1 : 0,
      minutesToCutoff: wave.status === 'PICKING' ? 24 : wave.status === 'PACKING' ? 40 : 90,
      dockReady: wave.status !== 'RELEASED'
    })
  } finally { checking.value = '' }
}

onMounted(async () => { list.value = await getWaves() })
</script>

<template>
  <div>
    <div class="page-head"><div><h1>出库与波次</h1><p>按承运商截单时间组织拣选、复核和集货</p></div><div class="page-actions"><button class="btn">波次策略</button><button class="btn primary">生成波次</button></div></div>
    <section class="kpi-grid"><article class="kpi"><div class="kpi-label">待分配订单</div><div class="kpi-value">126<small>单</small></div><div class="kpi-delta">建议合并为 3 个波次</div></article><article class="kpi"><div class="kpi-label">拣选中</div><div class="kpi-value">68<small>单</small></div><div class="kpi-delta good">波次 WV20260727012</div></article><article class="kpi"><div class="kpi-label">待复核</div><div class="kpi-value">41<small>单</small></div><div class="kpi-delta warn">最早截单 10:48</div></article><article class="kpi"><div class="kpi-label">已集货</div><div class="kpi-value">87<small>单</small></div><div class="kpi-delta">6 个集货笼</div></article><article class="kpi"><div class="kpi-label">今日发运</div><div class="kpi-value">1,698<small>件</small></div><div class="kpi-delta good">准时率 97.8%</div></article></section>

    <section v-if="releaseResult" class="release-panel" :class="releaseResult.decision.toLowerCase()">
      <div><span>波次放行检查 · {{ releaseResult.waveNo }}</span><h2>{{ decisionName[releaseResult.decision] }}</h2><p>拣选完成率 {{ releaseResult.completionRate }}%，剩余 {{ releaseResult.remainingPieces }} 件</p></div>
      <ul><li v-for="action in releaseResult.actions" :key="action">{{ action }}</li></ul>
    </section>

    <div class="filters"><input class="filter-input" placeholder="搜索波次号或承运商"><select class="filter-select"><option>进行中波次</option><option>全部波次</option></select><span class="filter-spacer"></span><span class="result-count">按截单时间升序</span></div>
    <table class="data-table"><thead><tr><th>波次号</th><th>承运商</th><th>订单 / SKU</th><th>件数</th><th>拣选进度</th><th>截单时间</th><th>负责人</th><th>状态</th><th>操作</th></tr></thead><tbody><tr v-for="i in list" :key="i.waveNo"><td class="mono"><strong>{{i.waveNo}}</strong></td><td>{{i.carrierName}}</td><td><strong>{{i.orderCount}} 单</strong><small>{{i.skuCount}} 个 SKU</small></td><td>{{i.pieceCount}}</td><td><div class="progress-track"><i :style="{width:`${i.pickedQty/i.pieceCount*100}%`}"></i></div><small>{{i.pickedQty}} / {{i.pieceCount}}</small></td><td>{{i.cutoffAt.slice(11,16)}}</td><td>{{i.ownerName}}</td><td><span class="status" :class="i.status==='RELEASED'?'':'amber'">{{statusName[i.status]}}</span></td><td><button class="btn" :disabled="checking===i.waveNo" @click="inspectWave(i)">{{ checking===i.waveNo?'检查中':'放行检查' }}</button></td></tr></tbody></table>
  </div>
</template>

<style scoped>
.release-panel{display:grid;grid-template-columns:1fr 1.5fr;gap:28px;margin:18px 0;padding:20px 24px;border:1px solid #f2c78f;border-left:4px solid #d68a2e;border-radius:10px;background:#fffaf3}.release-panel.ready{border-color:#abd9ca;border-left-color:#27956f;background:#f4fbf8}.release-panel.blocked{border-color:#e9b4b1;border-left-color:#c84640;background:#fff7f6}.release-panel span{color:#718086;font-size:12px}.release-panel h2{margin:5px 0;color:#29383e;font-size:20px}.release-panel p{margin:0;color:#75858a;font-size:12px}.release-panel ul{margin:0;padding-left:18px;color:#52656b;font-size:13px;line-height:1.9}.btn:disabled{cursor:wait;opacity:.55}
</style>
