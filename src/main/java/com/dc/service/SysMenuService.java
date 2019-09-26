package com.dc.service;

import com.dc.base.pojo.BaseModel;

import java.util.Map;

public interface SysMenuService {
    void selectAllMenu()throws Exception;

    void refreshSysMenu(BaseModel baseModel)throws Exception;

    void findMyMenu(Map<String,String> map,BaseModel baseModel)throws Exception;
}
