package com.dc.mapper;

import com.dc.base.pojo.QueryParams;
import com.dc.pojo.SysLoginLog;

import java.sql.SQLException;
import java.util.List;

public interface SysLoginLogDao {
    int insert(SysLoginLog loginLog) throws SQLException;

    int updateBySessionId(SysLoginLog loginLog);

    SysLoginLog selectLastLogin(SysLoginLog loginLog);

    int selectCountLoginError(SysLoginLog loginLog);

    List<SysLoginLog> selectLoginLog(QueryParams queryParams);
}
