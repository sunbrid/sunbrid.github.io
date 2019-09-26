package com.dc.controller;

import com.dc.base.aop.AopOperation;
import com.dc.base.aop.AopSubmit;
import com.dc.base.contants.BaseContants;
import com.dc.base.controller.BaseController;
import com.dc.base.em.RoleMenuEnum;
import com.dc.base.encrypt.MD5DES;
import com.dc.base.exception.BusinessException;
import com.dc.base.pojo.BaseModel;
import com.dc.base.util.UploaderFileUtil;
import com.dc.pojo.SysUser;
import com.dc.service.SysUserService;
import com.dc.vo.SysUserVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/sysUser")
public class SysUserController extends BaseController {
    public static final String DEFAULT_PASSOWRD = "111111";//默认密码
    @Autowired
    private SysUserService userService;

    @RequestMapping("/selectUserAll")
    @ResponseBody

    @ApiOperation(value = "查询所有的用户信息", notes = "查询", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", defaultValue = "1"
                    , dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "maxSize", value = "当前页最大条数"
                    , dataType = "Integer", defaultValue = "10", paramType = "query")})
    //required 是否必填，defaultValue = "1"设置默认值，@RequestParam中也包含这个两个属性
    public BaseModel selectUserAll(@RequestParam(value = "page", defaultValue = "1") Integer page
            , @RequestParam(value = "page", defaultValue = "10") Integer maxSize) {
        BaseModel baseModel = userService.selectUserAll(page, maxSize);
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 用户新增 <h3>
     * @author: Enzo
     * @date: 2018-11-15 16:38
     * @params [userVo, baseModel]
     **/
    @AopSubmit
    @AopOperation(menu = RoleMenuEnum.NO_204, type = BaseContants.OPERATION_TYPE.ADD)
    @ApiOperation(value = "新增用户信息", httpMethod = "POST", response = SysUserVo.class)
    @RequestMapping("insertUserVo")
    public BaseModel inserUserVo(@ModelAttribute SysUserVo userVo, BaseModel baseModel) throws Exception {
        if (userVo == null || userVo.getUser() == null) {
            throw new BusinessException("用户信息不能为空");
        } else if (userVo.getUser().getAccount() == null || "".equals(userVo.getUser().getAccount())) {
            throw new BusinessException("帐号不能为空");
        } else if (userVo.getUser().getName() == null || "".equals(userVo.getUser().getName())) {
            throw new BusinessException("用户姓名不能为空");
        }
        userVo.getUser().setPassword(MD5DES.encrypt(DEFAULT_PASSOWRD));
        userVo.getUser().setCreate_user_id(getSessionUser().getUser_id());
        userVo.getUser().setCreate_user_name(getSessionUser().getName());
        userService.inserUserVo(userVo, baseModel);
        baseModel.setMessage("新增用户成功");
        baseModel.setTempMFile(null);
        baseModel.setAop_mesg("用户id:" + userVo.getUser().getUser_id() + ",姓名：" + userVo.getUser().getName());

        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 分页查询用户列表 <h3>
     * @author: Enzo
     * @date: 2018-11-16 14:29
     * @params [baseModel]
     **/
    @AopOperation(menu = RoleMenuEnum.NO_204, type = BaseContants.OPERATION_TYPE.SEARCH)
    @ApiOperation(value = "分页查询用户列表", httpMethod = "POST", response = SysUserVo.class)
    @RequestMapping("selectPageUserVo")
    public BaseModel selectPageUserVo(BaseModel baseModel) throws Exception {
        userService.selectPageUserVo(baseModel);
        baseModel.setMessage("分页查询用户列表成功");
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3>根据用户id查询用户信息 <h3>
     * @author: Enzo
     * @date: 2018-11-16 15:38
     * @params [pk, baseModel]
     **/
    @AopOperation(desc = "根据用户id查询用户信息", type = BaseContants.OPERATION_TYPE.SEARCH, menu = RoleMenuEnum.NO_204)
    @ApiOperation(value = "根据用户id查询用户信息", httpMethod = "GET", response = SysUserVo.class)
    @RequestMapping("selectUserVoByUserId/{pk}")
    public BaseModel selectUserVoByPk(@PathVariable("pk") int pk, BaseModel baseModel) throws Exception {
        userService.selectUserVoByPk(pk, baseModel);
        baseModel.setMessage("根据用户id查询用户信息成功");
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 更新用户信息 <h3>
     * @author: Enzo
     * @date: 2018-11-19 10:33
     * @params [userVo, baseModel]
     **/
    @AopSubmit
    @AopOperation(menu = RoleMenuEnum.NO_204, type = BaseContants.OPERATION_TYPE.UPDATE)
    @ApiOperation(value = "更新用户信息", httpMethod = "POST", response = SysUserVo.class)
    @RequestMapping("updateUserVo")
    public BaseModel updateUserVo(@ModelAttribute SysUserVo userVo, BaseModel baseModel) throws Exception {
        if (userVo == null || userVo.getUser() == null) {
            throw new BusinessException("用户信息不能为空");
        } else if (userVo.getUser().getUser_id() == 0) {
            throw new BusinessException("用户id不能为空");
        } else if (userVo.getUser().getAccount() == null || "".equals(userVo.getUser().getAccount())) {
            throw new BusinessException("帐号不能为空");
        } else if (userVo.getUser().getName() == null || "".equals(userVo.getUser().getName())) {
            throw new BusinessException("用户姓名不能为空");
        }
        userVo.getUser().setUpdate_user_id(getSessionUser().getUser_id());
        userVo.getUser().setUpdate_user_name(getSessionUser().getName());
        userService.updateUserVo(userVo, baseModel);
        baseModel.setMessage("修改用户信息成功");
        baseModel.setTempMFile(null);
        baseModel.setAop_mesg("用户id:" + userVo.getUser().getUser_id() + ",姓名：" + userVo.getUser().getName());
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 删除用户信息<h3>
     * @author: Enzo
     * @date: 2018-11-19 11:19
     * @params [userIds, baseModel]
     **/
    @AopOperation(menu = RoleMenuEnum.NO_204, type = BaseContants.OPERATION_TYPE.DELETE)
    @ApiOperation(value = "删除用户信息", httpMethod = "GET")
    @RequestMapping("deleteUser/{userIds}")
    public BaseModel deleteUser(@PathVariable("userIds") String userIds, BaseModel baseModel) throws Exception {
        userService.deleteUserVoInUserIds(userIds, baseModel);
        baseModel.setAop_mesg(userIds);
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 重置密码 <h3>
     * @author: Enzo
     * @date: 2018-11-19 14:00
     * @params [pk, baseModel]
     **/
    @AopOperation(desc = "重置密码", menu = RoleMenuEnum.NO_204, type = BaseContants.OPERATION_TYPE.UPDATE)
    @ApiOperation(value = "重置密码", httpMethod = "GET")
    @RequestMapping("resetPassword/{pk}")
    public BaseModel resetPassword(@PathVariable("pk") int pk, BaseModel baseModel) throws Exception {
        SysUser user = new SysUser();
        user.setUser_id(pk);
        user.setUpdate_user_name(getSessionUser().getName());
        user.setUpdate_user_id(getSessionUser().getUser_id());
        user.setPassword(MD5DES.encrypt(DEFAULT_PASSOWRD));
        userService.resetPassword(user, baseModel);
        baseModel.setMessage("重置密码成功");
        baseModel.setAop_mesg(pk + "");
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 状态切换 <h3>
     * @author: Enzo
     * @date: 2018-11-19 14:02
     * @params [userId, baseModel]
     **/
    @AopOperation(desc = "状态切换", menu = RoleMenuEnum.NO_204, type = BaseContants.OPERATION_TYPE.UPDATE)
    @ApiOperation(value = "状态切换", httpMethod = "GET")
    @RequestMapping("tiggerState/{pk}")
    public BaseModel tiggerState(@PathVariable("pk") int userId, BaseModel baseModel) throws Exception {
        SysUser user = new SysUser();
        user.setUser_id(userId);
        user.setUpdate_user_name(getSessionUser().getName());
        user.setUpdate_user_id(getSessionUser().getUser_id());
        userService.switchState(user, baseModel);
        baseModel.setMessage("状态切换成功");
        baseModel.setAop_mesg(userId + "");
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 查询当前登录用户信息 <h3>
     * @author: Enzo
     * @date: 2018-11-20 9:41
     * @params [baseModel]
     **/
    @ApiOperation(value = "查询当前登录用户信息", httpMethod = "GET")
    @RequestMapping("findMyUserVo")
    public BaseModel findMyUserVo(BaseModel baseModel) throws Exception {
        userService.selectUserVoByPk(getSessionUser().getUser_id(), baseModel);
        return baseModel;
    }

    /**
     * @return void
     * @title:<h3> 导出用户信息 <h3>
     * @author: Enzo
     * @date: 2018-11-21 11:07
     * @params [baseModel]
     **/
    @AopOperation(type = BaseContants.OPERATION_TYPE.EXPORT, menu = RoleMenuEnum.NO_204)
    @ApiOperation(value = "导出用户信息", httpMethod = "POST")
    @RequestMapping("expUserDTO")
    public void expUserDTO(BaseModel baseModel) throws Exception {
        String destPath = userService.exportUserDTO(baseModel);
        UploaderFileUtil.downFile(response, destPath, "用户信息表.xls");
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 导入用户信息 <h3>
     * @author: Enzo
     * @date: 2018-11-21 14:34
     * @params [baseModel]
     **/
    @ApiOperation(value = "导入用户信息", httpMethod = "POST")
    @AopOperation(menu = RoleMenuEnum.NO_204, type = BaseContants.OPERATION_TYPE.IMPORT)
    @RequestMapping("impUserDTO")
    public BaseModel impUserDTO(BaseModel baseModel) throws Exception {
        userService.impUserDTO(getSessionUser(), baseModel);
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 更新个人信息 <h3>
     * @author: Enzo
     * @date: 2018-11-23 15:55
     * @params [userVo, baseModel]
     **/

    @RequestMapping("updateMyUserVo")
    @ApiOperation(value = "更新个人信息", httpMethod = "POST", response = SysUserVo.class)
    public BaseModel updateMyUserVo(SysUserVo userVo, BaseModel baseModel) throws Exception {
        if (userVo == null || userVo.getUser() == null) {
            throw new BusinessException("用户信息不能为空");
        }
        userVo.getUser().setUpdate_user_id(getSessionUser().getUser_id());
        userVo.getUser().setUpdate_user_name(getSessionUser().getName());
        userVo.getUser().setUser_id(getSessionUser().getUser_id());
        userService.updateMyUserVo(userVo, baseModel);
        baseModel.setMessage("更新个人信息成功");
        return baseModel;
    }
}
