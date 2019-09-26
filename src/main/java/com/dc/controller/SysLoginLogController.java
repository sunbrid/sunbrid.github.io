package com.dc.controller;

import com.dc.base.aop.AopOperation;
import com.dc.base.contants.BaseContants;
import com.dc.base.controller.BaseController;
import com.dc.base.em.RoleMenuEnum;
import com.dc.base.pojo.BaseModel;
import com.dc.pojo.SysLoginLog;
import com.dc.service.SysLoginLogService;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-23 14:31
 */
@Controller
@RequestMapping("loginLog")
public class SysLoginLogController extends BaseController {
    @Autowired
    SysLoginLogService loginLogService;

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 分页查询登陆日志列表 <h3>
     * @author: Enzo
     * @date: 2018-11-23 14:33
     * @params [baseModel]
     **/
    @ApiOperation(value = "查詢登录日志", httpMethod = "POST", response = SysLoginLog.class)
    @AopOperation(menu = RoleMenuEnum.NO_102, type = BaseContants.OPERATION_TYPE.SEARCH)
    @RequestMapping("selectPageLoginLog")
    public BaseModel selectPageLoginLog(BaseModel baseModel) throws Exception {
        loginLogService.selectPageData(baseModel);
        baseModel.setMessage("查询登陆日志成功");
        return baseModel;
    }
    /**
     * @title:<h3>  <h3>
     * @author: Enzo
     * @date: 2018-11-23 15:35
     * @params [baseModel]
     * @return com.dc.base.pojo.BaseModel
     **/
    @ApiOperation(value = "查询我的登录日志列表",httpMethod = "POST", response = SysLoginLog.class)
    @RequestMapping("selectPageMyLoginLog")
    public BaseModel selectPageMyLoginLog(BaseModel baseModel)throws Exception{
        baseModel.getQueryParams().setWhere("user_id="+getSessionUser().getUser_id());
        loginLogService.selectPageData(baseModel);
        return baseModel;
    }
}
