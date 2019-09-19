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
    -- common-microservice-client: 微服务client公共jar  
    -- common-microservice-server: 微服务server公共jar  
generator:  代码生成器模块
    -- generator-microservice: 微服务生成器
    -- generator-table: 表CRUD生成器
microservice:  微服务模块   
    -- uaa:         用户与账户  端口号[11000~11099]    鉴权、密码
    -- industry:    行业应用    端口号[11100~11199]    经济活动中的分类、标准，例如行政区划、商品与服务分类、高校、节假日...
    -- industry-education:      行业应用——教育行业（小学、中学、大学）
    -- industry-business:       行业应用——商业
    -- operation:   运营        端口号[11200~11299]    管理
    -- merchant:    商户平台    端口号[11300~11399]    活动、营销、推广
    -- merchant-mall:      商城，销售商品
    -- merchant-groupon:   团购，销售服务
    -- merchant-coupon :   商户优惠券
    -- merchant-card :     商户权益卡
    -- person:      个人业务    端口号[11400~11499]    会员、积分、钱包、卡券
    -- social:      第三方社交  端口号[11900~11999]    微信、支付宝、微博...
    -- order:       订单        端口号[11500~11599]    购货、消费
    -- payment:     支付        端口号[11600~11699]
    -- payment-channel:    支付渠道
    -- payment-route:      支付路由
    -- logistics:    物流       端口号[11700~11799]
    -- finance:      财务       端口号[11800~11899]
    -- finance-capital:      资金

    -- support: 技术支撑服务，例如文件存储服务、邮件服务、短信服务、验证码、文档导入导出 端口号[12000~12099]
    -- support-schedule: 任务调度服务 端口号[12100~12199]  
    -- hardware: 硬件集成服务，网络摄像机 端口号[13000~13099]

    价值链的增值活动可以分为基本增值活动和辅助性增值活动两大部分。
    企业的基本增值活动，即一般意义上的“生产经营环节”，如材料供应、成品开发、生产运行、成品储运、市场营销和售后服务。
这些活动都与商品实体的加工流转直接相关。
    企业的辅助性增值活动，包括组织建设、人事管理、技术开发和采购管理。这里的技术和采购都是广义的，既可以包括生产性技术，
也包括非生产性的开发管理，例如，决策技术、信息技术、计划技术；采购管理既包括生产原材料，也包括其他资源投入的管理，例如，
聘请有关咨询公司为企业进行广告策划、市场预测、法律咨询、信息系统设计和长期战略计划等。

    价值链分析
    基本活动：内部后勤、生产经营、外部后勤、市场销售、服务
    支持活动：采购管理、技术开发、人力资源管理、基础设施
    -- value-chain

    -- inbound-logistics            内部后勤
    -- operation                    生产经营
        -- product      产品（小额、高频消费场景的消费品或服务）
    -- outbound-logistics           外部后勤
        -- logistics-storage    物流仓储
    -- marketing-sale               市场销售：电商
        -- mall         电商
        -- merchant     商户
        -- member       消费者升级为会员
        -- order        订单（小额、高频消费场景）
        -- coupon       优惠（小额、高频消费场景）
        -- payment      支付（小额、高频消费场景）
    -- service                      服务

    -- procurement                  采购管理
    -- technological-development    技术开发
    -- human-resource-management    人力资源管理
    -- infrastructure               基础设施：组织结构、企业文化、内部控制（18条应用指引）

        -- bpm-activiti         微小企业，流程不完善，
        -- payment-account 支付账户（与用户账户互相独立）
        -- payment-channel 支付渠道（自建渠道和第三方渠道）
        -- settlement   结算

    价值网分析
