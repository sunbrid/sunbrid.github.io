package com.dc.controller;

import com.dc.base.aop.AopOperation;
import com.dc.base.aop.AopSubmit;
import com.dc.base.contants.BaseContants;
import com.dc.base.em.RoleMenuEnum;
import com.dc.pojo.SysRole;
import com.dc.vo.SysRoleVo;
import com.dc.base.controller.BaseController;
import com.dc.base.exception.BusinessException;
import com.dc.base.pojo.BaseModel;
import com.dc.service.SysRoleService;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @title:<h3> 角色管理控制层 <h3>
 * @author: Enzo
 * @date: 2018-11-14 9:11
 * @params
 * @return
 **/
@Controller
@RequestMapping("/role")
public class SysRoleController extends BaseController {
    private static Logger log = Logger.getLogger(SysRoleController.class);
    @Autowired
    private SysRoleService roleService;

    //@RequestMapping("/selectRoleAll")
    @ResponseBody
    @ApiOperation(value = "查询所有的角色信息", notes = "查询", httpMethod = "GET")
    @ApiImplicitParam(name = "likeName", value = "模糊查询值"
            , dataType = "String", paramType = "query")

    public BaseModel selectRoleAll(BaseModel baseModel,
            /* @RequestParam(value="likeName",required=false)*/String likeName) {
        log.info(baseModel.toString());
        return roleService.selectRoleAll(baseModel, likeName);
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 角色列表分页查询接口 <h3>
     * @author: Enzo
     * @date: 2018-11-14 9:11
     * @params [baseModel]
     **/
    @AopOperation(desc = "分页查询列表", type = BaseContants.OPERATION_TYPE.SEARCH, menu = RoleMenuEnum.NO_203)
    @RequestMapping("/selectPageRole")
    @ResponseBody
    @ApiOperation(value = "分页查询角色信息", notes = "查询", httpMethod = "POST", response = SysRole.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "baseModel.queryParams.curr_page", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "baseModel.queryParams.page_size", value = "数据量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "baseModel.queryParams.where", value = "查询条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "baseModel.queryParams.order", value = "排序", dataType = "String", paramType = "query")

    })
    public BaseModel selectPageRole(BaseModel baseModel) throws Exception {
        roleService.selectPageRole(baseModel);
        baseModel.setMessage("角色列表查询成功！");
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3>新增角色信息接口 <h3>
     * @author: Enzo
     * @date: 2018-11-14 15:49
     * @params [roleVo, baseModel]
     **/
    @AopSubmit
    @AopOperation(type = BaseContants.OPERATION_TYPE.ADD, menu = RoleMenuEnum.NO_203)
    @ApiOperation(value = "新增角色信息", httpMethod = "POST", response = SysRoleVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listRolePermission[0].code", value = "权限编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "listRolePermission[0].permission_value", value = "权限值", dataType = "string", paramType = "query"),
    })
    @RequestMapping(value = "insertRoleVo", method = RequestMethod.POST)
    public BaseModel insertRoleVo(@ModelAttribute SysRoleVo roleVo, BaseModel baseModel) throws Exception {
        if (roleVo == null) {
            throw new BusinessException("请输入角色信息");
        } else if (roleVo.getRole() == null) {
            throw new BusinessException("请输入角色信息");
        } else if (roleVo.getRole().getName() == null || "".equals(roleVo.getRole().getName())) {
            throw new BusinessException("请输入角色名称");
        } else if (roleVo.getListRolePermission() == null || roleVo.getListRolePermission().size() == 0) {
            throw new BusinessException("请选择角色权限");
        }
        roleVo.getRole().setCreate_user_id(getSessionUser().getUser_id());
        roleVo.getRole().setCreate_user_name(getSessionUser().getName());
        roleService.inserRoleVo(roleVo, baseModel);
        baseModel.setMessage("新增角色成功");
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 根据角色id查询角色详情 <h3>
     * @author: Enzo
     * @date: 2018-11-15 10:49
     * @params [baseModel, pk]
     **/
    @AopOperation(desc = "根据id查询角色信息", type = BaseContants.OPERATION_TYPE.SEARCH, menu = RoleMenuEnum.NO_203)
    @ApiOperation(value = "根据角色id查询角色详情", httpMethod = "GET", response = SysRoleVo.class)
    @RequestMapping("selectRoleVoById/{pk}")
    public BaseModel selectRoleVoByPk(BaseModel baseModel, @PathVariable("pk") int pk) throws Exception {
        roleService.selectRoleVoByPk(baseModel, pk);
        baseModel.setMessage("根据id查询角色详情成功");
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 修改角色信息 <h3>
     * @author: Enzo
     * @date: 2018-11-15 13:45
     * @params [roleVo, baseModel]
     **/
    @AopSubmit
    @AopOperation(type = BaseContants.OPERATION_TYPE.UPDATE, menu = RoleMenuEnum.NO_203)
    @ApiOperation(value = "修改角色信息", httpMethod = "POST", response = SysRoleVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listRolePermission[0].code", value = "权限编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "listRolePermission[0].permission_value", value = "权限值", dataType = "string", paramType = "query"),
    })
    @RequestMapping(value = "updateRoleVo", method = RequestMethod.POST)
    public BaseModel updateRoleVo(@ModelAttribute SysRoleVo roleVo, BaseModel baseModel) throws Exception {
        if (roleVo == null) {
            throw new BusinessException("请输入角色信息");
        } else if (roleVo.getRole() == null) {
            throw new BusinessException("请输入角色信息");
        } else if (roleVo.getRole().getName() == null || "".equals(roleVo.getRole().getName())) {
            throw new BusinessException("请输入角色名称");
        } else if (roleVo.getListRolePermission() == null || roleVo.getListRolePermission().size() == 0) {
            throw new BusinessException("请选择角色权限");
        } else if (roleVo.getRole().getRole_id() == null || roleVo.getRole().getRole_id() == 0) {
            throw new BusinessException("角色id不能为空");
        }
        roleVo.getRole().setUpdate_user_id(getSessionUser().getUser_id());
        roleVo.getRole().setUpdate_user_name(getSessionUser().getName());
        roleService.updateRoleVo(roleVo, baseModel);
        baseModel.setMessage("修改角色成功");
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 删除角色信息 <h3>
     * @author: Enzo
     * @date: 2018-11-15 14:36
     * @params [baseModel, roleIds]
     **/
    @AopOperation(type = BaseContants.OPERATION_TYPE.DELETE, menu = RoleMenuEnum.NO_203)

    @ApiOperation(value = "删除角色信息", httpMethod = "GET")
    @RequestMapping("deleteRole/{ids}")
    public BaseModel deleteRole(BaseModel baseModel, @PathVariable("ids") String roleIds) throws Exception {
        getSessionUser();
        roleService.deleteRoleInPk(roleIds, baseModel);
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 查询角色名称 <h3>
     * @author: Enzo
     * @date: 2018-11-15 15:42
     * @params [baseModel]
     **/
    @ApiOperation(value = "查询角色名称", httpMethod = "GET", response = SysRole.class)
    @RequestMapping("selectRoleName")
    public BaseModel selectRoleName(BaseModel baseModel) throws Exception {
        roleService.selectRoleName(baseModel);
        baseModel.setMessage("查询角色名称成功");
        return baseModel;
    }
}
