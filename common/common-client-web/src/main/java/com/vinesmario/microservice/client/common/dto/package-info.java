package com.vinesmario.microservice.client.common.dto;

// Data Transfer Object
// 服务实现中常常允许DTO中的数据包含一些冗余
// 可以使用第三方类库JAXB生成javabean来定义DTO
// DTO是贫血模型，可在Helper类中为这些类型定义一系列辅助函数。
// 也可在服务端添加一个业务逻辑表现，即BO（Business Object）。
// PO将不会直接转化为DTO，而是转化为BO。在所有业务处理完毕并需要将数据发送给客户的时候，BO将转化为DTO以进行传输。