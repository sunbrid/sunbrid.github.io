package com.dc.service.impl;

import com.dc.base.contants.GlobalVar;
import com.dc.base.em.ErrorMesgEnum;
import com.dc.base.em.RoleMenuEnum;
import com.dc.base.encrypt.MD5DES;
import com.dc.base.pojo.BaseModel;
import com.dc.mapper.SysLoginLogDao;
import com.dc.mapper.SysUserDao;
import com.dc.pojo.SysLoginLog;
import com.dc.pojo.SysUser;
import com.dc.service.LoginService;
import com.dc.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-12 15:24
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserDao userDao;
    @Autowired
    private SysLoginLogDao loginLogDao;
    @Autowired
    private SysMenuService menuService;

    /**
     * @return void
     * @title:<h3> 登录 <h3>
     * @author: Enzo
     * @date: 2018-11-12 15:24
     * @params [loginLog, baseModel]
     **/
    public void login(SysLoginLog loginLog, BaseModel baseModel) throws Exception {
        //1.根据用户名查询用户信息
        SysUser user = userDao.selectUserByAccount(loginLog.getUser_account());
        //2.判断帐号是否存在
        if (user == null) {//如果用户信息不存在，说明帐号不存在
            baseModel.setResultCode(1);
            baseModel.setMessage("帐号或密码错误");
            loginLog.setState(2);
        } else {//说明存在帐号
            //验证连续登录
            long time = 30 * 60 * 1000;//半小时的毫秒数
            int num = 5;//验证连续登录失败的次数
            Date date = new Date(new Date().getTime() - time);//当前时间前半小时
            SysLoginLog lastLogin = loginLogDao.selectLastLogin(loginLog);//查询最后一次登录成功记录
            if (lastLogin != null) {//说明存在登录成功记录
                if (lastLogin.getLogin_date().getTime() > date.getTime()) {//如果最后一次登录成功在半小时内
                    date = lastLogin.getLogin_date();//则取最后一次时间作为查询时间
                }
            }
            loginLog.setLogin_date(date);
            //查询登录失败的记录数
            int count = loginLogDao.selectCountLoginError(loginLog);
            if (count > num) {//验证是否超过5次
                baseModel.setResultCode(1);
                baseModel.setMessage(ErrorMesgEnum.ACCOUT_LOCK.getMesg());
                loginLog.setState(5);
            } else {
                //3.验证帐号是否激活
                if (user.getState() == 1) {//说启用
                    //4.判断密码是否正确
                    if (user.getPassword().equals(MD5DES.encrypt(loginLog.getUser_password()))) {//如果密码正确，登录成功
                        loginLog.setState(1);
                        baseModel.setResultCode(0);
                        baseModel.setMessage("登录成功");
                        user.setPassword(null);//密码等敏感数据不返回
                        baseModel.setData(user);
                        loginLog.setUser_id(user.getUser_id());
                        loginLog.setUser_name(user.getName());
                        loginLog.setUser_password(null);//登录成功的密码不保存在登录日志
                    } else {//帐号或密码错误
                        baseModel.setResultCode(1);
                        baseModel.setMessage("帐号或密码错误");
                        loginLog.setState(3);
                    }
                } else {//没启用
                    loginLog.setState(4);
                    baseModel.setResultCode(1);
                    baseModel.setMessage("帐号未激活，请联系管理员");
                    loginLog.setUser_password(null);//未激活的密码不保存在登录日志
                }
            }
        }
        //5.不管登录成功否，记录登录日志
        int count = loginLogDao.insert(loginLog);
        if (count == 0) {//新增登录日志失败
            baseModel.setResultCode(1);
            baseModel.setMessage("新增登录日志失败");
        }
    }

    /**
     * @return void
     * @title:<h3> 查询权限菜单枚举类 <h3>
     * @author: Enzo
     * @date: 2018-11-14 11:04
     * @params [baseModel]
     **/
    public void selectEnumRoleMenu(BaseModel baseModel) throws Exception {
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        menuService.selectAllMenu();
        for (RoleMenuEnum em : RoleMenuEnum.values()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", em.getName());
            map.put("code", em.getCode() + "");
            map.put("roleValue", em.getValue());
            if (GlobalVar.mapCodeMenu.get(em.getCode() + "") != null) {//说明数据库和枚举类中同时存在该权限菜单
                listMap.add(map);
            }
        }
        baseModel.setData(listMap);
    }

    /**
     * @return void
     * @title:<h3> 注销 <h3>
     * @author: Enzo
     * @date: 2018-11-23 9:34
     * @params [loginLog]
     **/
    public void loginOut(SysLoginLog loginLog) throws Exception {
        loginLogDao.updateBySessionId(loginLog);
    }
}
