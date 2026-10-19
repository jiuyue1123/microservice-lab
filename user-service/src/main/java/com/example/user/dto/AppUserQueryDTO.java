package com.example.user.dto;

import com.example.user.enums.UserState;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户分页查询条件
 */
@Getter
@Setter
public class AppUserQueryDTO {

    /**
     * 页码，从 1 开始
     */
    @Min(value = 1, message = "页码不能小于1")
    private Long pageNum = 1L;

    /**
     * 每页条数，最大 100
     */
    @Min(value = 1, message = "每页条数不能小于1")
    @Max(value = 100, message = "每页条数不能超过100")
    private Long pageSize = 10L;

    /**
     * 昵称，模糊匹配
     */
    @Size(max = 64, message = "昵称长度不能超过64个字符")
    private String nickname;

    /**
     * 手机号，精确匹配
     */
    @Size(max = 20, message = "手机号长度不能超过20个字符")
    private String phone;

    /**
     * 邮箱，精确匹配
     */
    @Size(max = 256, message = "邮箱长度不能超过256个字符")
    private String email;

    /**
     * 用户状态
     */
    private UserState userState;
}
