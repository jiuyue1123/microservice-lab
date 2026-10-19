package com.example.user.vo;

import com.example.user.entity.AppUser;
import com.example.user.enums.Gender;
import com.example.user.enums.UserState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户列表项
 */
@Getter
@Setter
public class AppUserVO {

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
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态
     */
    private UserState userState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    public static AppUserVO from(AppUser user) {
        AppUserVO vo = new AppUserVO();
        vo.setId(String.valueOf(user.getId()));
        vo.setNickname(user.getNickname());
        vo.setAvatarUrl(user.getAvatarUrl());
        vo.setGender(user.getGender());
        vo.setPhone(user.getPhone());
        vo.setEmail(user.getEmail());
        vo.setUserState(user.getUserState());
        vo.setGmtCreate(user.getGmtCreate());
        return vo;
    }
}
