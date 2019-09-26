package com.dc.service.impl;

import com.dc.base.pojo.BaseModel;
import com.dc.mapper.SysLoginLogDao;
import com.dc.pojo.SysLoginLog;
import com.dc.service.SysLoginLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-23 14:38
 */
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {
    @Autowired
    SysLoginLogDao loginLogDao;

    /**
     * @return void
     * @title:<h3> 分页查询登陆日志 <h3>
     * @author: Enzo
     * @date: 2018-11-23 14:39
     * @params [baseModel]
     **/
    public void selectPageData(BaseModel baseModel) throws Exception {
        PageHelper.startPage(baseModel.getQueryParams().getCurr_page(), baseModel.getQueryParams().getPage_size());
        List<SysLoginLog> list = loginLogDao.selectLoginLog(baseModel.getQueryParams());
        baseModel.setData(new PageInfo(list));
    }
}
