package com.vinesmario.microservice.client.finance.dto;

/**
 * 微信服务商模式下的子商户
 * 对接微信
 */
public class WeChatMerchantDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 商户ID
     */
    private Long merchantId;
    /**
     * 服务商公众账号ID
     */
    private String appid;
    /**
     * 微信支付分配的服务商商户号
     */
    private String mchId;
    /**
     * 子商户公众账号ID
     */
    private String subAppid;
    /**
     * 微信支付分配的子商户号
     */
    private String subMchId;

}
