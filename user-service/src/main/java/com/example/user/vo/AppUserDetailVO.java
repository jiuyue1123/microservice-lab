package com.example.user.vo;

import com.example.user.entity.AppUser;
import com.example.user.enums.Gender;
import com.example.user.enums.UserState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户详情
 */
@Getter
@Setter
public class AppUserDetailVO {

    /**
     * 字符串形式返回，避免前端 Long 精度丢失
     */
    private String id;

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
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
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
     * 乐观锁版本号，修改时需回传
     */
    private Integer version;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

    public static AppUserDetailVO from(AppUser user) {
        AppUserDetailVO vo = new AppUserDetailVO();
        vo.setId(String.valueOf(user.getId()));
        vo.setNickname(user.getNickname());
        vo.setAvatarUrl(user.getAvatarUrl());
        vo.setGender(user.getGender());
        vo.setBirthday(user.getBirthday());
        vo.setPhone(user.getPhone());
        vo.setEmail(user.getEmail());
        vo.setLocale(user.getLocale());
        vo.setTimezone(user.getTimezone());
        vo.setUserState(user.getUserState());
        vo.setRemark(user.getRemark());
        vo.setVersion(user.getVersion());
        vo.setGmtCreate(user.getGmtCreate());
        vo.setGmtModified(user.getGmtModified());
        return vo;
    }
}
