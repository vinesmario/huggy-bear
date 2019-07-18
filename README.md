cloud: spring-cloud组件模块  
    -- cloud-registry-server: 注册中心   端口号[10000~10099]
    -- cloud-config-server: 配置中心   端口号[10100~10199]
    -- cloud-gateway-server: 网关  端口号[10500~10599]
    -- cloud-admin-server: spring-boot-admin-server  端口号[10200~10299]
    -- cloud-hystrix-dashboard: 断路器监控面板  端口号[10300~10399]
    -- cloud-turbine: spring-cloud-netflix-turbine  端口号[10400~10499]
    -- cloud-turbine-stream: spring-cloud-netflix-turbine-stream   端口号[10400~10499]
common:  公共模块
    -- common-tools: 工具jar
    -- common-client-web: web客户端公共jar
    -- common-server-web: web服务端公共jar
generator:  代码生成器模块
    -- generator-microservice: 微服务生成器
    -- generator-table: 表CRUD生成器
microservice:  微服务模块   
    -- uaa: 用户与账户  端口号[11000~11099]
    -- industry: 行业信息 行政区划、商品与服务分类、高校、节假日端口号[11100~11199]
    -- social: 接入第三方社交，例如微信、支付宝、微博... 端口号[11200~11299]
    -- support: 技术支撑服务，例如文件服务、邮件服务、短信服务、任务调度服务 端口号[12000~12099]
    -- open: 开放平台，对外开放接入接口
    -- hardware: 硬件集成服务 端口号[11200~11299]
    -- : 生物信息
    价值链分析
    基本活动：内部后勤、生产经营、外部后勤、市场销售、服务
    支持活动：采购管理、技术开发、人力资源管理、基础设施

    内部控制

    价值网分析
    -- merchant     商户
    -- member       会员
    -- product      产品
    -- order        订单
    -- coupon       优惠
    -- payment      支付
    -- settlement   结算
    -- logistics-storage    物流仓储
    -- human-resources      人力资源
