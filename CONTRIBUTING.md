# 贡献指南

Copyright © 2026 上海如静知华信息科技有限公司。

感谢参与 ZhuaTech WMS。提交贡献即表示你同意按照 [ZhuaTech WMS 社区源码许可协议](LICENSE)的贡献条款提供该贡献。本项目仅允许个人非商业学习交流，商业使用须取得上海如静知华信息科技有限公司的书面授权。

1. 先通过 Issue 描述业务场景、当前行为和预期结果，避免重复实现。
2. 从 `main` 创建语义清晰的分支，例如 `feat/putaway-strategy` 或 `fix/inventory-allocation`。
3. Java 代码统一使用 `cn.zhuatech.wms` 根包，新增源文件须保留公司版权头。
4. 数据库变更必须新增 Flyway 迁移，不得修改已经发布的迁移文件。
5. 提交前运行后端测试、前端构建和依赖审计；不得提交密钥、客户数据、真实凭据或构建产物。
6. Pull Request 需要描述业务背景、库存影响、测试方法；作业端和管理端的页面变化应分别附对应视口截图。

推荐使用 Conventional Commits：`feat:`、`fix:`、`docs:`、`refactor:`、`test:`、`chore:`。

参与者须遵守 [行为准则](CODE_OF_CONDUCT.md)。商业合作与深度开发请通过 [知华科技官网](https://www.zhuatech.cn/) 联系上海如静知华信息科技有限公司。
