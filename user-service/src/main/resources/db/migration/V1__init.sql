-- 待建表：user_address、tenant_user、role、permission、role_permission、user_role

CREATE TABLE `app_user` (
  -- 雪花算法生成，不使用数据库自增
  `id`            bigint unsigned  NOT NULL                 COMMENT '主键',
  `nickname`      varchar(64)      NOT NULL DEFAULT ''      COMMENT '昵称',
  `avatar_url`    varchar(256)     NOT NULL DEFAULT ''      COMMENT '头像地址',
  `gender`        varchar(20)      NOT NULL DEFAULT 'UNKNOWN' COMMENT '性别：UNKNOWN-未知，MALE-男，FEMALE-女',
  `birthday`      date             NULL     DEFAULT NULL    COMMENT '生日',
  `phone`         varchar(20)      NULL     DEFAULT NULL    COMMENT '手机号',
  `email`         varchar(256)     NULL     DEFAULT NULL    COMMENT '邮箱',
  `locale`        varchar(16)      NOT NULL DEFAULT 'zh-CN' COMMENT '语言区域',
  `timezone`      varchar(64)      NOT NULL DEFAULT 'Asia/Shanghai' COMMENT '时区',
  `user_state`    varchar(20)      NOT NULL DEFAULT 'ACTIVE' COMMENT '用户状态：ACTIVE-正常，DISABLED-停用，LOCKED-锁定，CLOSED-注销',
  `remark`        varchar(512)     NOT NULL DEFAULT ''      COMMENT '备注',

  `version`       int unsigned     NOT NULL DEFAULT 0       COMMENT '乐观锁版本号',
  `deleted_at`    datetime         NULL     DEFAULT NULL    COMMENT '删除时间，为空表示未删除',
  `gmt_create`    datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified`  datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

  -- 软删除后释放唯一键
  `active_phone`  varchar(20)  AS (IF(`deleted_at` IS NULL, `phone`, NULL)) VIRTUAL COMMENT '未删除用户的手机号',
  `active_email`  varchar(256) AS (IF(`deleted_at` IS NULL, `email`, NULL)) VIRTUAL COMMENT '未删除用户的邮箱',

  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_active_phone` (`active_phone`),
  UNIQUE KEY `uk_active_email` (`active_email`),
  KEY `idx_phone` (`phone`(20)),
  KEY `idx_email` (`email`(64)),
  KEY `idx_user_state` (`user_state`(20)),
  CONSTRAINT `chk_app_user_gender` CHECK (`gender` IN ('UNKNOWN', 'MALE', 'FEMALE')),
  CONSTRAINT `chk_app_user_user_state` CHECK (`user_state` IN ('ACTIVE', 'DISABLED', 'LOCKED', 'CLOSED'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基础信息表';
