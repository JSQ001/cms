package com.eicas.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eicas.security.entity.AuthPermit;
import com.eicas.security.entity.AuthRole;
import com.eicas.security.entity.AuthUser;
import com.eicas.security.pojo.vo.AuthUserInfoVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统账号表 Mapper 接口
 *
 * @author osnudt
 * @since 2022-04-23
 */
public interface AuthUserMapper extends BaseMapper<AuthUser> {


    /**
     * 查询账号所拥有的角色
     *
     * @param id 账号ID
     * @return 账号所拥有的角色列表
     */
    @Select("    SELECT DISTINCT " +
            "    r.id, r.role_name, r.role_key, r.sort_order, r.data_scope, r.is_enabled, r.remarks" +
            "    FROM auth_user AS a" +
            "    LEFT JOIN auth_user_role_r AS ar ON a.id = ar.user_id" +
            "    LEFT JOIN auth_role AS r ON r.id = ar.role_id" +
            "    WHERE a.id = #{id} " +
            "    AND r.is_deleted = 0;")
    List<AuthRole> selectRoleListByUserId(Long id);

    /**
     * 查询账号所拥有的权限
     *
     * @param id 账号ID
     * @return 账号所拥有的权限列表
     */
    @Select("    SELECT DISTINCT " +
            "    p.id, p.parent_id, p.permit_name, p.permit_key, p.request_path, p.component, p.params, p.permit_type, p.sort_order, p.is_visible, p.is_enabled, p.icon, p.remarks" +
            "    FROM auth_user AS u" +
            "    LEFT JOIN auth_user_role_r AS ur ON u.id = ur.user_id" +
            "    LEFT JOIN auth_role AS r ON r.id = ur.role_id" +
            "    LEFT JOIN auth_role_permit_r AS rp ON r.id = rp.role_id" +
            "    LEFT JOIN auth_permit AS p ON p.id = rp.permit_id" +
            "    WHERE ur.user_id = #{id}" +
            "    AND r.is_deleted = 0;")
    List<AuthPermit> selectPermitListByUserId(Long id);

    @Select("SELECT " +
            "       auth_user.id," +
            "       auth_user.uuid  ," +
            "       auth_user.username ," +
            "       auth_user.is_enabled," +
            "       auth_user.is_non_expired," +
            "       auth_user.is_account_non_locked," +
            "       auth_user.is_credentials_non_expired," +
            "       auth_user.is_deleted," +
            "       sys_user_detail.id," +
            "       sys_user_detail.realname," +
            "       sys_user_detail.gender ," +
            "       sys_user_detail.age ," +
            "       sys_user_detail.portrait ," +
            "       sys_user_detail.type ," +
            "       sys_user_detail.id_card ," +
            "       sys_user_detail.contact ," +
            "       sys_user_detail.birthday ," +
            "       sys_user_detail.ethnic ," +
            "       sys_user_detail.org_id ," +
            "       sys_user_detail.organization ," +
            "       sys_user_detail.post_id ," +
            "       sys_user_detail.post ," +
            "       sys_user_detail.remarks " +
            "       FROM auth_user LEFT JOIN sys_user_detail ON auth_user.user_detail_id = sys_user_detail.id" +
            "       WHERE auth_user.is_deleted = 0;")
    List<AuthUserInfoVO> selectUserInfoByUsername(String username);
}
