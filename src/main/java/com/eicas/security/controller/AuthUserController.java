package com.eicas.security.controller;


import com.eicas.common.ResultData;
import com.eicas.security.entity.AuthUser;
import com.eicas.security.pojo.param.UserLoginParam;
import com.eicas.security.pojo.param.UserPasswordParam;
import com.eicas.security.pojo.param.UserRegisterParam;
import com.eicas.security.service.IAuthPermitService;
import com.eicas.security.service.IAuthRoleService;
import com.eicas.security.service.IAuthUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

/**
 * 系统账号表 前端控制器
 *
 * @author osnudt
 * @since 2022-04-23
 */
@RestController
@RequestMapping("/auth-user")
public class AuthUserController {
    @Resource
    IAuthUserService authUserService;
//    @Resource
//    IAuthRoleService authRoleService;
//    @Resource
//    IAuthPermitService authPermitService;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultData<AuthUser> register(@Validated @RequestBody UserRegisterParam param) {
        AuthUser user = authUserService.register(param);
        if (user == null) {
            return ResultData.failed("用户注册失败");
        }
        return ResultData.success(user);
    }

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultData login(@Validated @RequestBody UserLoginParam param) {
        boolean result = authUserService.login(param.getUsername(), param.getPassword());
        return result ? ResultData.success("用户认证成功") : ResultData.validateFailed("用户名或密码错误");
    }

    @ApiOperation(value = "用户退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResultData logout() {
        return ResultData.success(null);
    }

    @ApiOperation("修改指定用户信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultData update(@PathVariable Long id, @RequestBody AuthUser user) {
        // TODO
        return ResultData.success(authUserService.save(user));
    }

    @ApiOperation("修改指定用户密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public ResultData updatePassword(@Validated @RequestBody UserPasswordParam param) {
        int status = authUserService.updatePassword(param);
        if (status > 0) {
            return ResultData.success(status);
        } else if (status == -1) {
            return ResultData.failed("提交参数不合法");
        } else if (status == -2) {
            return ResultData.failed("找不到该用户");
        } else if (status == -3) {
            return ResultData.failed("旧密码错误");
        } else {
            return ResultData.failed();
        }
    }

    @ApiOperation("删除指定用户信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Boolean delete(@PathVariable Long id) {
        return authUserService.removeById(id);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ResultData getCurrentUserInfo(Principal principal) {
        if (principal == null) {
            return ResultData.unauthorized(null);
        }
        String username = principal.getName();
        AuthUser user = authUserService.getAuthUserByUsername(username);
        return ResultData.success(user);
    }

    @ApiOperation("给用户分配角色")
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultData updateRole(@RequestParam("id") Long id,
                                 @RequestParam("roleIds") List<Long> roleIds) {
        return ResultData.failed();
    }

}
