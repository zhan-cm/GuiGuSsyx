这份 README 提取了尚上优选项目的核心业务、技术栈和架构，并按照开源项目的标准格式进行了排版。你可以直接将以下内容复制并保存为项目根目录下的 README.md 文件，然后推送到 GitHub 上。

Markdown
# 🛒 尚上优选 (Shangshang Youxuan) - 企业级微服务社区团购平台

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.3.6.RELEASE-brightgreen.svg)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-Hoxton.SR8-blue.svg)
![MyBatis-Plus](https://img.shields.io/badge/MyBatis--Plus-3.4.1-yellow.svg)
![Vue](https://img.shields.io/badge/Vue.js-2.x-4FC08D.svg)

## 📖 项目简介
尚上优选是一个基于 Spring Cloud 微服务架构开发的企业级社区团购系统。项目采用前后端分离模式，涵盖了平台管理端和微信小程序用户端。业务模式主要围绕**平台、团长、用户**三个角色展开，依托真实的社区和团长社交关系，实现生鲜等商品的快速流通与本地化新零售交付。

### 核心业务流程
1. **团长开团**：团长（如社区宝妈、便利店老板）创建群聊，发布平台提供的优惠商品链接。
2. **用户下单**：社区用户提前一天通过微信小程序下单并完成支付。
3. **平台履约**：平台收集订单，调动供应链从中心大仓发货到网格仓，再配送至团长自提点。
4. **用户提货**：用户前往指定的团长自提点完成提货。

---

## 🛠️ 核心技术栈

### 后端技术
* **基础框架**：Spring Boot 2.3.6.RELEASE
* **微服务架构**：Spring Cloud & Spring Cloud Alibaba (Gateway, Nacos, Feign, Sentinel)
* **持久层框架**：MyBatis-Plus
* **关系型数据库**：MySQL 8.0
* **缓存与分布式锁**：Redis / Redisson
* **消息中间件**：RabbitMQ (异步解耦、流量削峰、分布式事务)
* **搜索引擎**：ElasticSearch + Kibana (商品全局检索)
* **对象存储**：阿里云 OSS (海报、商品图片统一存储)
* **容器化部署**：Docker

### 前端技术
* **后台管理端**：Vue.js 2.x + Element-UI (基于 vue-admin-template)
* **用户端**：微信小程序原生开发 + Uni-app

---

## 🏗️ 系统架构与模块划分

项目采用 Maven 多模块化管理，根工程为 `guigu-ssyx-parent`。

```text
guigu-ssyx-parent
├── common                  # 公共工具类与配置模块
│   ├── common-util         # 核心基础工具
│   ├── rabbit-util         # RabbitMQ 封装
│   └── service-util        # 微服务通用配置 (Redis, Swagger, 异常处理等)
├── model                   # 全局实体类库 (Entity, Vo, QueryVo)
├── service-client          # Feign 远程调用接口封装
└── service                 # 微服务群
    ├── service-acl         # 权限管理模块 (RBAC: 用户、角色、菜单)
    ├── service-sys         # 基础系统/区域仓库管理
    ├── service-product     # 商品管理模块 (分类、属性、SPU/SKU)
    ├── service-activity    # 营销活动模块 (满减满折、优惠券、秒杀)
    ├── service-search      # 搜索模块 (基于 ES 的商品上架与检索)
    ├── service-cart        # 购物车模块 (基于 Redis Hash)
    ├── service-order       # 订单模块 (确认订单、防重提交、库存锁定)
    ├── service-payment     # 支付模块 (对接微信支付)
    └── service-home        # 首页数据聚合模块
```

🔔 配置提醒：以下配置项需要根据你的实际物理机/云服务器环境进行真实替换，否则服务将无法启动或报错：

MySQL 连接：修改 spring.datasource.url、username、password。

Redis 连接：修改 spring.redis.host、password。

Nacos 地址：修改 spring.cloud.nacos.discovery.server-addr。

RabbitMQ 连接：修改 spring.rabbitmq.host、username、password。

阿里云 OSS（service-product）：修改 aliyun.endpoint、keyid、keysecret、bucketname 为你个人的真实授权信息。

微信小程序（service-user / service-payment）：修改 wx.open.app_id 和 wx.open.app_secret 为你申请的微信测试号或正式号的真实数据；weixin.partner、partnerkey 及证书路径需替换为你真实的商户号信息。

4. 启动服务
优先启动 service-gateway (网关服务，端口 8200)。

依次启动其他业务微服务（如 service-acl, service-product 等）。

访问 Nacos 控制台确认服务均已成功注册。

📝 接口文档
项目集成了 Knife4j，服务启动后可通过浏览器直接访问接口调试页面：

例如权限模块：http://localhost:8201/doc.html

统一网关入口：http://localhost:8200/doc.html

