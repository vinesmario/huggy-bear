CREATE TABLE `storage_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) DEFAULT NULL,
  `uuid` varchar(32) DEFAULT NULL COMMENT 'UUID',
  `file_extension` varchar(10) DEFAULT NULL COMMENT '文件扩展名',
  `file_name` varchar(50) DEFAULT NULL COMMENT '文件名称',
  `file_absolute_path` varchar(255) DEFAULT NULL COMMENT '文件存储绝对路径',
  `file_absolute_url` varchar(255) DEFAULT NULL COMMENT '文件访问绝对url',
  `file_relative_url` varchar(255) DEFAULT NULL COMMENT '文件访问相对url',
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小，单位B',
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `storage_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(20) DEFAULT NULL,
  `uuid` varchar(32) DEFAULT NULL COMMENT 'UUID',
  `file_extension` varchar(10) DEFAULT NULL COMMENT '文件扩展名',
  `image_name` varchar(50) DEFAULT NULL COMMENT '图片名称',
  `image_absolute_path` varchar(255) DEFAULT NULL COMMENT '图片存储绝对路径',
  `image_absolute_url` varchar(255) DEFAULT NULL COMMENT '图片访问绝对url',
  `image_relative_url` varchar(255) DEFAULT NULL COMMENT '图片访问相对url',
  `image_height` int(11) DEFAULT NULL COMMENT '图片高度',
  `image_width` int(11) DEFAULT NULL COMMENT '图片宽',
  `image_size` bigint(20) DEFAULT NULL COMMENT '图片大小，单位B',
  `thumbnail_name` varchar(50) DEFAULT NULL COMMENT '缩略图名称',
  `thumbnail_absolute_path` varchar(255) DEFAULT NULL COMMENT '缩略图存储绝对路径',
  `thumbnail_absolute_url` varchar(255) DEFAULT NULL COMMENT '缩略图访问绝对url',
  `thumbnail_relative_url` varchar(255) DEFAULT NULL COMMENT '缩略图访问相对url',
  `thumbnail_height` int(11) DEFAULT NULL COMMENT '缩略图高度',
  `thumbnail_width` int(11) DEFAULT NULL COMMENT '缩略图宽度',
  `thumbnail_size` int(11) DEFAULT NULL COMMENT '缩略图大小，单位B',
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `deleted` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
