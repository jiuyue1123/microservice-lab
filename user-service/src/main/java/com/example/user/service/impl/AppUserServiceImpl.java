package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.common.exception.BusinessException;
import com.example.common.result.PageResult;
import com.example.user.dto.AppUserCreateDTO;
import com.example.user.dto.AppUserProfileDTO;
import com.example.user.dto.AppUserQueryDTO;
import com.example.user.dto.AppUserUpdateDTO;
import com.example.user.entity.AppUser;
import com.example.user.enums.Gender;
import com.example.user.enums.UserErrorCode;
import com.example.user.enums.UserState;
import com.example.user.mapper.AppUserMapper;
import com.example.user.service.AppUserService;
import com.example.user.vo.AppUserDetailVO;
import com.example.user.vo.AppUserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;

/**
 * 用户资料服务实现
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements AppUserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(AppUserCreateDTO dto) {
        String phone = normalizePhone(dto.getPhone());
        String email = normalizeEmail(dto.getEmail());
        // 预检查用于返回明确错误，最终仍由数据库唯一索引兜底
        ensurePhoneAvailable(phone, null);
        ensureEmailAvailable(email, null);

        AppUser user = new AppUser();
        applyProfile(user, dto);
        user.setPhone(phone);
        user.setEmail(email);
        user.setUserState(UserState.ACTIVE);
        user.setVersion(0);
        save(user);
        return String.valueOf(user.getId());
    }

    @Override
    public AppUserDetailVO getDetail(Long id) {
        return AppUserDetailVO.from(getRequiredUser(id));
    }

    @Override
    public PageResult<AppUserVO> page(AppUserQueryDTO query) {
        long pageNum = query.getPageNum() == null ? 1L : query.getPageNum();
        long pageSize = query.getPageSize() == null ? 10L : query.getPageSize();

        LambdaQueryWrapper<AppUser> wrapper = Wrappers.lambdaQuery(AppUser.class)
                .like(StringUtils.hasText(query.getNickname()), AppUser::getNickname, trim(query.getNickname()))
                .eq(StringUtils.hasText(query.getPhone()), AppUser::getPhone, trim(query.getPhone()))
                .eq(StringUtils.hasText(query.getEmail()), AppUser::getEmail, normalizeEmail(query.getEmail()))
                .eq(query.getUserState() != null, AppUser::getUserState, query.getUserState())
                .orderByDesc(AppUser::getGmtCreate);

        Page<AppUser> result = page(new Page<>(pageNum, pageSize), wrapper);
        List<AppUserVO> records = result.getRecords().stream().map(AppUserVO::from).toList();
        return PageResult.of(result.getCurrent(), result.getSize(), result.getTotal(), records);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, AppUserUpdateDTO dto) {
        AppUser user = getRequiredUser(id);
        // 客户端版本与当前记录不一致时拒绝更新，避免覆盖并发修改
        if (!user.getVersion().equals(dto.getVersion())) {
            throw new BusinessException(UserErrorCode.USER_VERSION_CONFLICT);
        }

        String phone = normalizePhone(dto.getPhone());
        String email = normalizeEmail(dto.getEmail());
        ensurePhoneAvailable(phone, id);
        ensureEmailAvailable(email, id);

        applyProfile(user, dto);
        user.setPhone(phone);
        user.setEmail(email);
        if (!updateById(user)) {
            throw new BusinessException(UserErrorCode.USER_VERSION_CONFLICT);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disable(Long id) {
        AppUser user = getRequiredUser(id);
        if (user.getUserState() == UserState.CLOSED) {
            throw new BusinessException(UserErrorCode.USER_STATE_NOT_ALLOWED);
        }
        // 重复禁用视为成功，保持接口幂等
        if (user.getUserState() == UserState.DISABLED) {
            return;
        }
        user.setUserState(UserState.DISABLED);
        if (!updateById(user)) {
            throw new BusinessException(UserErrorCode.USER_VERSION_CONFLICT);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        getRequiredUser(id);
        // removeById 触发 @TableLogic，实际执行 deleted_at = now()
        removeById(id);
    }

    // PUT 语义为全量更新，可选字段为空时按清空处理
    private void applyProfile(AppUser user, AppUserProfileDTO dto) {
        user.setNickname(dto.getNickname().trim());
        user.setAvatarUrl(trimToEmpty(dto.getAvatarUrl()));
        user.setGender(dto.getGender() == null ? Gender.UNKNOWN : dto.getGender());
        user.setBirthday(dto.getBirthday());
        user.setLocale(defaultIfBlank(dto.getLocale(), "zh-CN"));
        user.setTimezone(defaultIfBlank(dto.getTimezone(), "Asia/Shanghai"));
        user.setRemark(trimToEmpty(dto.getRemark()));
    }

    /**
     * 查询用户，不存在时抛出业务异常
     */
    private AppUser getRequiredUser(Long id) {
        AppUser user = getById(id);
        if (user == null) {
            throw new BusinessException(UserErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    /**
     * 检查手机号是否已被其他未删除用户占用
     */
    private void ensurePhoneAvailable(String phone, Long excludeId) {
        if (phone == null) {
            return;
        }
        boolean exists = baseMapper.exists(Wrappers.lambdaQuery(AppUser.class)
                .eq(AppUser::getPhone, phone)
                .ne(excludeId != null, AppUser::getId, excludeId));
        if (exists) {
            throw new BusinessException(UserErrorCode.PHONE_ALREADY_EXISTS);
        }
    }

    /**
     * 检查邮箱是否已被其他未删除用户占用
     */
    private void ensureEmailAvailable(String email, Long excludeId) {
        if (email == null) {
            return;
        }
        boolean exists = baseMapper.exists(Wrappers.lambdaQuery(AppUser.class)
                .eq(AppUser::getEmail, email)
                .ne(excludeId != null, AppUser::getId, excludeId));
        if (exists) {
            throw new BusinessException(UserErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    /**
     * 空字符串按未填写处理，统一存 NULL
     */
    private String normalizePhone(String phone) {
        return StringUtils.hasText(phone) ? phone.trim() : null;
    }

    /**
     * 邮箱去空格并转小写，避免大小写不同产生重复账号
     */
    private String normalizeEmail(String email) {
        return StringUtils.hasText(email) ? email.trim().toLowerCase(Locale.ROOT) : null;
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }

    private String trimToEmpty(String value) {
        return value == null ? "" : value.trim();
    }

    private String defaultIfBlank(String value, String defaultValue) {
        return StringUtils.hasText(value) ? value.trim() : defaultValue;
    }
}
