package com.example.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.example.user.enums.Gender;
import com.example.user.enums.UserState;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * app_user 表实体
 */
@Getter
@Setter
@TableName("app_user")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 雪花 ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 生日，允许更新为 NULL 清空
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private LocalDate birthday;

    /**
     * 手机号，允许更新为 NULL 清空
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String phone;

    /**
     * 邮箱，允许更新为 NULL 清空
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String email;

    /**
     * 语言区域
     */
    private String locale;

    /**
     * 时区
     */
    private String timezone;

    /**
     * 用户状态
     */
    private UserState userState;

    /**
     * 备注
     */
    private String remark;

    /**
     * 乐观锁版本号
     */
    @Version
    private Integer version;

    /**
     * 逻辑删除时间，NULL 表示未删除
     */
    @TableLogic(value = "null", delval = "now()")
    private LocalDateTime deletedAt;

    /**
     * 创建时间，由 AuditMetaObjectHandler 填充
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间；数据库同时配置了 ON UPDATE 兜底
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;
}
