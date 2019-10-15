package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.TreeEntity;
import lombok.Data;

/**
 * 岗位职责 多对对关系
 * 岗位对应多个职责，职责是资源组
 * 岗位职责对应不同的数据范围：
 * 1，所在机构
 * 2，所在机构及直接下属机构
 * 3，所在机构及所有下属机构；
 * 4，所在部门
 * 5，所在部门及直接下属部门
 * 6，所在部门及所有下属部门
 * 7，所在岗位
 * 8，所在岗位及直接下属岗位
 * 9，所在岗位及所有下属岗位
 * 10，所在小组
 * 11，仅自己
 */
@Data
public class PositionAuthority extends TreeEntity<Long> {

    private Long positionId;
    private Long authorityId;
    private Integer dataScope;

}
