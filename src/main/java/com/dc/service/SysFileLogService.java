package com.dc.service;

import com.dc.base.pojo.BaseModel;

public interface SysFileLogService {
    void uploadFiles(BaseModel baseModel,String uploader)throws Exception;
}
