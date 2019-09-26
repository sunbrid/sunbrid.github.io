package com.dc.mapper;

import com.dc.base.pojo.QueryParams;
import com.dc.pojo.SysOperationLog;

import java.util.List;

public interface SysOperationLogDao {
    int insert(SysOperationLog operationLog);

    List<SysOperationLog>selectSysOperationLog(QueryParams queryParams);
}
