package com.example.user.service;

import com.example.common.result.PageResult;
import com.example.user.dto.AppUserCreateDTO;
import com.example.user.dto.AppUserQueryDTO;
import com.example.user.dto.AppUserUpdateDTO;
import com.example.user.vo.AppUserDetailVO;
import com.example.user.vo.AppUserVO;

/**
 * 用户资料服务
 */
public interface AppUserService {

    /**
     * 新增用户
     *
     * @param dto 用户资料
     * @return 新用户 ID
     */
    String create(AppUserCreateDTO dto);

    /**
     * 查询用户详情
     *
     * @param id 用户 ID
     * @return 用户详情
     */
    AppUserDetailVO getDetail(Long id);

    /**
     * 分页查询用户
     *
     * @param query 查询条件
     * @return 分页结果
     */
    PageResult<AppUserVO> page(AppUserQueryDTO query);

    /**
     * 修改用户资料
     *
     * @param id  用户 ID
     * @param dto 用户资料
     */
    void update(Long id, AppUserUpdateDTO dto);

    /**
     * 禁用用户
     *
     * @param id 用户 ID
     */
    void disable(Long id);

    /**
     * 删除用户（逻辑删除）
     *
     * @param id 用户 ID
     */
    void delete(Long id);
}
