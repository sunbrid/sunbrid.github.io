package com.dc.service;

import com.dc.base.pojo.BaseModel;
import com.dc.pojo.SysLoginLog;
import com.dc.pojo.SysUser;

public interface LoginService {
    void login(SysLoginLog loginLog, BaseModel baseModel)throws Exception;


    void selectEnumRoleMenu(BaseModel baseModel)throws Exception;

    void loginOut(SysLoginLog loginLog)throws Exception;
}
