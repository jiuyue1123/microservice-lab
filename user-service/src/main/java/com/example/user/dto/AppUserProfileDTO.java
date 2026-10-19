package com.example.user.dto;

import com.example.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 新增和修改用户资料共用的字段
 */
@Getter
@Setter
public abstract class AppUserProfileDTO {

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    @Size(max = 64, message = "昵称长度不能超过64个字符")
    private String nickname;

    /**
     * 头像地址
     */
    @Size(max = 256, message = "头像地址长度不能超过256个字符")
    private String avatarUrl;

    /**
     * 性别，默认 UNKNOWN
     */
    private Gender gender;

    /**
     * 生日
     */
    @Past(message = "生日必须是过去的日期")
    private LocalDate birthday;

    /**
     * 手机号，空字符串按未填写处理
     */
    @Pattern(regexp = "^$|^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(max = 256, message = "邮箱长度不能超过256个字符")
    private String email;

    /**
     * 语言区域，默认 zh-CN
     */
    @Size(max = 16, message = "语言区域长度不能超过16个字符")
    private String locale;

    /**
     * 时区，默认 Asia/Shanghai
     */
    @Size(max = 64, message = "时区长度不能超过64个字符")
    private String timezone;

    /**
     * 备注
     */
    @Size(max = 512, message = "备注长度不能超过512个字符")
    private String remark;
}
