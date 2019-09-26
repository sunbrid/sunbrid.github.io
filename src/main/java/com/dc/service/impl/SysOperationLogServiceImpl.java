package com.dc.service.impl;

import com.dc.base.pojo.BaseModel;
import com.dc.mapper.SysOperationLogDao;
import com.dc.pojo.SysOperationLog;
import com.dc.service.SysOperationLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-20 9:14
 */
@Service
public class SysOperationLogServiceImpl implements SysOperationLogService {
    @Autowired
    private SysOperationLogDao operationLogDao;
    /**
     * @title:<h3>分页查询操作日志列表  <h3>
     * @author: Enzo
     * @date: 2018-11-20 9:14
     * @params [baseModel]
     * @return void
     **/
    public void selectPageSysOperationLog(BaseModel baseModel) throws Exception {
        PageHelper.startPage(baseModel.getQueryParams().getCurr_page(),baseModel.getQueryParams().getPage_size());
        List<SysOperationLog>list=operationLogDao.selectSysOperationLog(baseModel.getQueryParams());
        baseModel.setData(new PageInfo(list));
    }
}
