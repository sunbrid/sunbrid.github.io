package com.dc.service.impl;

import com.dc.base.em.ErrorMesgEnum;
import com.dc.vo.SysRoleVo;
import com.dc.base.exception.BusinessException;
import com.dc.base.pojo.BaseModel;
import com.dc.mapper.SysRoleDao;
import com.dc.pojo.SysRole;
import com.dc.pojo.SysRolePermission;
import com.dc.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title:<h3> 角色管理接口实现 <h3>
 * @author: Enzo
 * @date: 2018-11-14 9:37
 * @params
 * @return
 **/
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleDao roleDao;

    public BaseModel selectRoleAll(BaseModel baseModel, String likeName) {
        List<SysRole> roles = roleDao.selectRoleAll(likeName);
        baseModel.setResultCode(0);
        baseModel.setMessage("角色列表查询成功！");
        baseModel.setData(roles);
        return baseModel;
    }

    /**
     * @return void
     * @title:<h3> 角色列表分页查询接口实现 <h3>
     * @author: Enzo
     * @date: 2018-11-14 9:37
     * @params [baseModel]
     **/
    public void selectPageRole(BaseModel baseModel) throws Exception {
        // baseModel.getQueryParams().setWhere(" name like '%张三%' and memo like'%备注%'");
        PageHelper.startPage(baseModel.getQueryParams().getCurr_page(), baseModel.getQueryParams().getPage_size());
        List<SysRole> roles = roleDao.selectRole(baseModel.getQueryParams());
        PageInfo roleList = new PageInfo(roles);
        baseModel.setData(roleList);
    }

    /**
     * @return void
     * @title:<h3> 新增角色信息业务 <h3>
     * @author: Enzo
     * @date: 2018-11-14 16:09
     * @params [roleVo, baseModel]
     **/
    public void inserRoleVo(SysRoleVo roleVo, BaseModel baseModel) throws Exception {
//1.判断角色信息是否 已存在，若不存在则新增，否则抛出角色信息已存在异常
        SysRole oldRole = roleDao.selectRoleByName(roleVo.getRole().getName());
        if (oldRole != null) {
            throw new BusinessException("角色名已存在，请勿重复添加");
        }
        //2.新增角色信息
        roleDao.insertRole(roleVo.getRole());
        //3.获得新增的角色信息的id，保存在角色权限中，新增角色权限信息
        for (int i = 0; i < roleVo.getListRolePermission().size(); i++) {
            SysRolePermission permission = roleVo.getListRolePermission().get(i);
            permission.setFk_role_id(roleVo.getRole().getRole_id());
            permission.setCreate_user_id(roleVo.getRole().getCreate_user_id());
            permission.setCreate_user_name(roleVo.getRole().getCreate_user_name());
            //4.新增角色权限信息，如果角色权限值全部为0(或不存在1)，则不保存权限信息
            if (permission.getPermission_value() != null && permission.getPermission_value().indexOf("1") > -1) {
                roleDao.insertRolePermission(permission);
            }
        }
    }

    /**
     * @return void
     * @title:<h3> 根据角色id查询角色详情 <h3>
     * @author: Enzo
     * @date: 2018-11-15 10:51
     * @params [baseModel, pk]
     **/
    public void selectRoleVoByPk(BaseModel baseModel, int pk) throws Exception {
        SysRoleVo roleVo = roleDao.selectRoleVoByPk(pk);
        if (roleVo == null) {
            throw new BusinessException("该角色信息已被删除");
        }
        baseModel.setData(roleVo);
    }

    /**
     * @return void
     * @title:<h3> 修改角色信息 <h3>
     * @author: Enzo
     * @date: 2018-11-15 13:33
     * @params [roleVo, baseModel]
     **/
    public void updateRoleVo(SysRoleVo roleVo, BaseModel baseModel) throws Exception {
        //1.判断角色名称是否重复，根据角色名称查询角色信息
        SysRole oldRole = roleDao.selectRoleByName(roleVo.getRole().getName());
        if (oldRole == null) {//说明角色名不存（不重复）
        } else if (oldRole.getRole_id() == roleVo.getRole().getRole_id()) {//说明角色未重复，当前角色名称未未发生变化
        } else {
            //2.如果存角色信息，则判断查询到的角色id和当前id是否一致，如果不一致，则说明角色名称重复
            throw new BusinessException("角色名称‘" + roleVo.getRole().getName() + "’已存在，请勿重复添加");
        }
//3.更新角色信息
        int count = roleDao.updateRole(roleVo.getRole());
        if (count == 0) {
            throw new BusinessException(ErrorMesgEnum.UPDATE_VES);
        }
        //4.删除原有角色权限
        roleDao.deleteRolePermission(roleVo.getRole().getRole_id() + "");
        //5.添加角色权限信息
        for (int i = 0; i < roleVo.getListRolePermission().size(); i++) {
            SysRolePermission permission = roleVo.getListRolePermission().get(i);
            permission.setFk_role_id(roleVo.getRole().getRole_id());
            permission.setCreate_user_id(roleVo.getRole().getCreate_user_id());
            permission.setCreate_user_name(roleVo.getRole().getCreate_user_name());
            //新增角色权限信息，如果角色权限值全部为0(或不存在1)，则不保存权限信息
            if (permission.getPermission_value() != null && permission.getPermission_value().indexOf("1") > -1) {
                roleDao.insertRolePermission(permission);
            }
        }
    }

    /**
     * @return void
     * @title:<h3> 根据id删除角色信息 <h3>
     * @author: Enzo
     * @date: 2018-11-15 14:31
     * @params [roleIds, baseModel]
     **/
    public void deleteRoleInPk(String roleIds, BaseModel baseModel) throws Exception {
        //删除角色权限表记录
        roleDao.deleteRolePermission(roleIds);
        //删除用户角色关系表记录
        roleDao.deletUserRoleInRoleIds(roleIds);
        //删除角色信息
        int count = roleDao.removeRoleInPk(roleIds);
        baseModel.setMessage("删除" + count + "条角色信息成功");
    }

    /**
     * @return void
     * @title:<h3> 查询角色名称 <h3>
     * @author: Enzo
     * @date: 2018-11-15 15:39
     * @params [baseModel]
     **/
    public void selectRoleName(BaseModel baseModel) throws Exception {
        baseModel.setData(roleDao.selectRoleName());
    }

    /**
     * @return java.util.List<com.dc.pojo.SysRolePermission>
     * @title:<h3> 根据用户id查询并合并权限值 <h3>
     * @author: Enzo
     * @date: 2018-11-20 10:01
     * @params [userId]
     **/
    public Map<String, String> selectMergeRolePermissionByUserId(int userId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        List<SysRolePermission> list = roleDao.selectRolePermissionByUserId(userId);
        for (SysRolePermission permission : list) {
            if (map.get(permission.getCode()) == null) {//如果map中不存在编号
                map.put(permission.getCode(), permission.getPermission_value());
            } else {//如果已存在权限编号，则进行权限合并
                StringBuilder mapValue = new StringBuilder(map.get(permission.getCode()));
                StringBuilder perValue = new StringBuilder(permission.getPermission_value());
                if (mapValue.length() >= perValue.length()) {
                    //100111     1101
                    for (int i = 0; i < perValue.length(); i++) {//循环短的权限值
                        if (mapValue.charAt(i) == '0' && perValue.charAt(i) == '1') {
                            mapValue.replace(i,i+1,"1");
                        }
                    }
                    map.put(permission.getCode(),mapValue.toString());
                }else{//1100   1111101
                    for (int i = 0; i < mapValue.length(); i++) {//循环短的权限值
                        if ( perValue.charAt(i) == '0'&&mapValue.charAt(i)=='1') {
                            perValue.replace(i,i+1,"1");
                        }
                    }
                    map.put(permission.getCode(),perValue.toString());
                }
            }
        }
        return map;
    }
}
