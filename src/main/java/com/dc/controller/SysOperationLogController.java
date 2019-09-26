package com.dc.controller;

import com.dc.base.aop.AopOperation;
import com.dc.base.contants.BaseContants;
import com.dc.base.controller.BaseController;
import com.dc.base.em.RoleMenuEnum;
import com.dc.base.pojo.BaseModel;
import com.dc.pojo.SysOperationLog;
import com.dc.service.SysOperationLogService;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-20 9:18
 */
@Controller
@RequestMapping("operationLog")
@ResponseBody
public class SysOperationLogController extends BaseController {
    @Autowired
   private SysOperationLogService operationLogService;
    /**
     * @title:<h3> 分页查询操作日志列表 <h3>
     * @author: Enzo
     * @date: 2018-11-20 9:22
     * @params [baseModel]
     * @return com.dc.base.pojo.BaseModel
     **/
    @ApiOperation(value = "分页查询操作日志列表",httpMethod = "POST",response = SysOperationLog.class)
    @AopOperation(menu = RoleMenuEnum.NO_103,type = BaseContants.OPERATION_TYPE.SEARCH)
    @RequestMapping("selectPageOperationLog")
    public BaseModel selectPageOperationLog(BaseModel baseModel)throws Exception{
        operationLogService.selectPageSysOperationLog(baseModel);
        return baseModel;
    }
}
