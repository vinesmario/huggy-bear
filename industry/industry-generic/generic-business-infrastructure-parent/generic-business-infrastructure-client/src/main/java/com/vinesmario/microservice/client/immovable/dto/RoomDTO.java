package com.vinesmario.microservice.client.immovable.dto;

import lombok.Data;

/**
 * 房间、空间
 */
@Data
public class RoomDTO {

    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 用途：安保；教学；会议；居住；储物；其他。
     */
    private Integer purpose;
    /**
     * 教学、会议、居住 可容纳人数
     */
    private Integer capacity;
    /**
     * 容量单位
     */
    private String capacityUnit;
    /**
     * 建筑物ID，非必需项
     */
    private Long buildingId;

}
