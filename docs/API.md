# ZhuaTech WMS API

Copyright © 2026 上海如静知华信息科技有限公司。

默认地址为 `http://localhost:8080/api`，除登录外均须携带 `Authorization: Bearer <token>`。统一响应结构为 `{ "success": true, "data": ..., "message": null }`。

## 身份认证

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/auth/login` | 用户名和密码登录，返回 JWT、姓名、角色与仓库 |
| GET | `/auth/me` | 获取当前用户资料 |

登录示例：

```json
{ "username": "operator", "password": "Demo@2026" }
```

## 仓储业务

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/wms/dashboard` | 当日吞吐、任务、库存、库容、准确率和预警 |
| GET | `/wms/tasks` | 查询全部仓储任务 |
| GET | `/wms/tasks/mine` | 查询当前人员任务 |
| POST | `/wms/tasks` | 创建收货、上架、拣货、补货、盘点或打包任务 |
| PATCH | `/wms/tasks/{id}` | 更新任务状态、完成数量、执行人和备注 |
| GET | `/wms/inventory` | 查询 SKU/批次/库位库存余额 |
| GET | `/wms/inbounds` | 查询预约、到仓、收货和质检状态 |
| GET | `/wms/waves` | 查询出库波次、拣选进度和截单时间 |
| GET | `/wms/zones` | 查询库区、库容、环境和运行状态 |

任务状态：`WAITING`、`IN_PROGRESS`、`EXCEPTION`、`COMPLETED`。优先级：`NORMAL`、`HIGH`、`URGENT`。

更新任务示例：

```json
{ "status": "IN_PROGRESS", "completedQty": 28, "assignee": "陈师傅", "remark": "已扫描库位和首件" }
```

接口为第一版学习实现。正式环境通常还需要分页、字段权限、幂等键、操作审计、设备签名、限流和开放接口版本管理。

## 动态补货

`POST /api/wms/replenishment-plan`：计算拣选面所需补货量、储备缺口、任务紧急度与目标完成时间。

## 循环盘点计划

`POST /api/wms/insights/cycle-count`：输入 SKU 分类、差异率、动销、盘点间隔与库存价值，返回风险分、盘点优先级和完成时限。
