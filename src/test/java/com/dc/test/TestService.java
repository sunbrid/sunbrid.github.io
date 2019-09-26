package com.dc.test;

import com.alibaba.fastjson.JSON;
import com.dc.mapper.SysLoginLogDao;
import com.dc.mapper.SysRoleDao;
import com.dc.mapper.SysUserDao;
import com.dc.pojo.SysLoginLog;
import com.dc.pojo.SysRole;
import com.dc.pojo.SysUser;
import com.dc.vo.SysRoleVo;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-12 11:11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class TestService {
    private static Logger logger = Logger.getLogger(TestService.class);
    @Autowired
    SysUserDao userDao;
    @Autowired
    SysLoginLogDao loginLogDao;

    @Autowired
    SysRoleDao roleDao;
    @Before
    public void before() {

    }

    //@Test
    public void selectUserByAccount() throws Exception {
        logger.info("测试根据用户名查询用户信息接口");
        SysUser user = userDao.selectUserByAccount("admin");
        if (user == null) {
            logger.error("未查询到用信息");
        } else {
            logger.error(user.toString());
        }
    }

    //@Test
    public void insertLoginLog() throws Exception {
        logger.info("测试登录日志新增");
        SysLoginLog login = new SysLoginLog();
        login.setUser_id(1);
        login.setUser_name("张三666");
        login.setUser_account("zhangsan666");
        int count = loginLogDao.insert(login);
        if (count == 0) {
            logger.error("新增失败");
        } else {
            logger.error("新增成功：+" + JSON.toJSONString(login));
        }
    }
    //@Test
    public void selectRoleVoByPk()throws Exception{
        logger.info("测试根据id查询角色详情");
        SysRoleVo roleVo=roleDao.selectRoleVoByPk(42);
        if(roleVo!=null){
            logger.info(JSON.toJSONString(roleVo));
        }else{
            logger.error("未查询到角色信息");
        }
    }
//@Test
    public void updateRole()throws Exception{
        logger.info("测试角色修改");
        SysRole role=new SysRole();
        role.setRole_id(52);
        role.setName("测试角色更新");
        role.setMemo("测试角色更新备注");
        role.setCreate_user_id(1);
        role.setCreate_user_name("张三");
        role.setVersion(0);
       int count= roleDao.updateRole(role);
        if(count==0){
            logger.error("更新失败");
        }else{
            logger.error("更新成");
        }
    }
@Test
    public void deleteRolePermission()throws Exception{
        logger.info("测试角色权限删除");
        int count =roleDao.deleteRolePermission("51");
        logger.error("删除了"+count+"条记录");
    }
}
