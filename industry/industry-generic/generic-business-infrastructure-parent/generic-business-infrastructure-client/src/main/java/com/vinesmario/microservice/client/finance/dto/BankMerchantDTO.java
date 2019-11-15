package com.vinesmario.microservice.client.finance.dto;

/**
 * XX银行下的子商户（微信&支付宝渠道）
 * 对接XX银行
 */
public class BankMerchantDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 商户ID
     */
    private Long merchantId;
    /**
     * 银行服务商公众账号ID
     */
    private String appid;
    /**
     * 子商户公众账号ID
     */
    private String subAppid;
    /**
     * 微信支付分配的子商户号
     */
    private String subMchId;

}
