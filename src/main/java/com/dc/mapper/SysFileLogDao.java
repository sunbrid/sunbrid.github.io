package com.dc.mapper;

import com.dc.pojo.SysFileLog;
import org.apache.ibatis.annotations.Param;

public interface SysFileLogDao {
    int insertFileLog(SysFileLog fileLog);
    /**
     * @title:<h3> 根据用户id删除文件上传记录表记录 <h3>
     * @author: Enzo
     * @date: 2018-11-19 10:20
     * @params [userIds]
     * @return int
     **/
    int removeFileLogInUserIds(@Param("userIds") String userIds);
}
