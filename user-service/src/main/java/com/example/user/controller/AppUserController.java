package com.example.user.controller;

import com.example.common.result.PageResult;
import com.example.common.result.Result;
import com.example.user.dto.AppUserCreateDTO;
import com.example.user.dto.AppUserQueryDTO;
import com.example.user.dto.AppUserUpdateDTO;
import com.example.user.service.AppUserService;
import com.example.user.vo.AppUserDetailVO;
import com.example.user.vo.AppUserVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户资料接口
 */
@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    /**
     * 新增用户
     *
     * @param dto 用户资料
     * @return 新用户 ID
     */
    @PostMapping
    public Result<String> create(@Valid @RequestBody AppUserCreateDTO dto) {
        return Result.success(appUserService.create(dto));
    }

    /**
     * 查询用户详情
     *
     * @param id 用户 ID
     * @return 用户详情
     */
    @GetMapping("/{id}")
    public Result<AppUserDetailVO> detail(@PathVariable Long id) {
        return Result.success(appUserService.getDetail(id));
    }

    /**
     * 分页查询用户
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @GetMapping
    public Result<PageResult<AppUserVO>> page(@Valid AppUserQueryDTO query) {
        return Result.success(appUserService.page(query));
    }

    /**
     * 修改用户资料
     *
     * <p>使用 version 做乐观锁，版本不一致时返回冲突错误
     *
     * @param id  用户 ID
     * @param dto 用户资料
     * @return 操作结果
     */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody AppUserUpdateDTO dto) {
        appUserService.update(id, dto);
        return Result.success();
    }

    /**
     * 禁用用户
     *
     * @param id 用户 ID
     * @return 操作结果
     */
    @PatchMapping("/{id}/disable")
    public Result<Void> disable(@PathVariable Long id) {
        appUserService.disable(id);
        return Result.success();
    }

    /**
     * 删除用户（逻辑删除）
     *
     * @param id 用户 ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        appUserService.delete(id);
        return Result.success();
    }
}
