package com.example.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 修改用户资料请求
 */
@Getter
@Setter
public class AppUserUpdateDTO extends AppUserProfileDTO {

    /**
     * 乐观锁版本号，必须与当前记录一致
     */
    @NotNull(message = "版本号不能为空")
    private Integer version;
}
