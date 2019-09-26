package com.dc.service.impl;

import com.dc.base.contants.BaseContants;
import com.dc.base.pojo.BaseModel;
import com.dc.mapper.SysFileLogDao;
import com.dc.pojo.SysFileLog;
import com.dc.service.SysFileLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author Enzo
 * @Description 文件上级记录业务实现
 * @date 2018-11-16 9:46
 */
@Service
public class SysFileLogServiceImpl implements SysFileLogService {
    @Autowired
    SysFileLogDao fileLogDao;

    /**
     * @return void
     * @title:<h3> 文件上传 <h3>
     * @author: Enzo
     * @date: 2018-11-16 9:47
     * @params [baseModel, uploader]
     **/
    public void uploadFiles(BaseModel baseModel, String uploader) throws Exception {
        //判断文件是否为空
        if (baseModel.getTempMFile() == null || baseModel.getTempMFile().length == 0) {
            return;
        }
        StringBuilder fileIds = new StringBuilder("");
        //文件不为空
        for (MultipartFile file : baseModel.getTempMFile()) {
            if (file.isEmpty() || file.getSize() == 0 || file.getName() == null) {//判断文件是否有效
                return;
            }
            SysFileLog fileLog = new SysFileLog();
            //获得原始文件名
            fileLog.setFile_name(file.getOriginalFilename());
            //获得文件类型
            fileLog.setFile_type(file.getContentType());
            //获得文件长度
            fileLog.setFile_size(file.getSize());
            //重名文件
            fileLog.setFile_rename(UUID.randomUUID().toString() + fileLog.getFile_name().substring(fileLog.getFile_name().lastIndexOf(".")));
            //保存文件，获得保存文件的相对地址
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            Date time = new Date();
//            String path = time.getYear() + File.separator + sdf.format(time).substring(0, 4)
//                    + File.separator + sdf.format(time) + File.separator + fileLog.getFile_rename();
            Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH)+1;
            int date = c.get(Calendar.DATE);
            fileLog.setSave_path( year + File.separator + year + month + File.separator
                    + year + month + date + File.separator + fileLog.getFile_rename());
            //上传者
            fileLog.setUploader(uploader);
            //进行文件上传
//文件上传物理路径
            String path=System.getProperty(BaseContants.WEBAPP_ROOT)+File.separator+"files"
                    +File.separator+"upload"+File.separator+fileLog.getSave_path();
            uploderFile(file,path);
            //执行文件新增的sql
            fileLogDao.insertFileLog(fileLog);
            fileIds.append(",");
            fileIds.append(fileLog.getFile_log_id());
        }
        //返回上传文件id
        if (fileIds.length() > 1) {
            baseModel.setFilesArray(fileIds.toString().substring(1));
        }
    }
/**
 * @title:<h3> 上传文件 <h3>
 * @author: Enzo
 * @date: 2018-11-16 10:56
 * @params [file：文件对象, desPath：输出文件的物理路径]
 * @return void
 **/
    private void uploderFile(MultipartFile file,String desPath)throws Exception{
//判断文件上传的目录是否存在，若不存在，则创建文件夹
        File desFile=new File(desPath);//设置目标文件
        if(!desFile.getParentFile().exists()){//目标文件的文件夹是否存在，若不存在，则创建文件夹
            desFile.getParentFile().mkdirs();
        }
        FileOutputStream outputStream=new FileOutputStream(desFile);
        outputStream.write(file.getBytes());
        if(outputStream!=null){
            outputStream.flush();
            outputStream.close();
        }
    }
}
