package com.dc.service;

import com.dc.pojo.SysRolePermission;
import com.dc.vo.SysRoleVo;
import com.dc.base.pojo.BaseModel;

import java.util.List;
import java.util.Map;

public interface SysRoleService {
     BaseModel selectRoleAll(BaseModel baseModel,String likeName);
     void selectPageRole(BaseModel baseModel)throws Exception;

     void inserRoleVo(SysRoleVo roleVo,BaseModel baseModel)throws Exception;

     void selectRoleVoByPk(BaseModel baseModel,int pk)throws Exception;

     void updateRoleVo(SysRoleVo roleVo,BaseModel baseModel)throws Exception;

     void deleteRoleInPk(String roleIds,BaseModel baseModel)throws Exception;
void selectRoleName(BaseModel baseModel)throws Exception;
     Map<String, String> selectMergeRolePermissionByUserId(int userId)throws Exception;
}
