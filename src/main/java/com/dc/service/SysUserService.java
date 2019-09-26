package com.dc.service;

import com.dc.base.pojo.BaseModel;
import com.dc.pojo.SysUser;
import com.dc.vo.SysUserVo;

import java.util.List;

public interface SysUserService {
    public BaseModel selectUserAll(Integer page, Integer maxSize);

    void inserUserVo(SysUserVo userVo, BaseModel baseModel) throws Exception;

    void selectPageUserVo(BaseModel baseModel) throws Exception;

    void selectUserVoByPk(int pk, BaseModel baseModel) throws Exception;


    void updateUserVo(SysUserVo userVo, BaseModel baseModel) throws Exception;

    void deleteUserVoInUserIds(String ids, BaseModel baseModel) throws Exception;

    void switchState(SysUser user, BaseModel baseModel) throws Exception;

    void resetPassword(SysUser user, BaseModel baseModel) throws Exception;

    String exportUserDTO(BaseModel baseModel) throws Exception;

    void impUserDTO(SysUser user,BaseModel baseModel) throws Exception;

    void updateMyUserVo(SysUserVo userVo,BaseModel baseModel)throws Exception;
}
