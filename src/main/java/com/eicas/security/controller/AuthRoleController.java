package com.eicas.security.controller;

import com.eicas.common.ResultData;
import com.eicas.security.entity.AuthRole;
import com.eicas.security.service.IAuthRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色信息表 前端控制器
 *
 * @author osnudt
 * @since 2022-04-23
 */
@RestController
@RequestMapping("/auth-role")
public class AuthRoleController {
    @Resource
    private IAuthRoleService roleService;

    @ApiOperation("添加角色")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultData create(@RequestBody AuthRole role) {
        return ResultData.success(roleService.save(role), "");
    }
}
