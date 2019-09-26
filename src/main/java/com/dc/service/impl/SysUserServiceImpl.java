package com.dc.service.impl;

import com.dc.base.em.ErrorMesgEnum;
import com.dc.base.encrypt.MD5DES;
import com.dc.base.exception.BusinessException;
import com.dc.base.pojo.BaseModel;
import com.dc.base.util.BaseUtil;
import com.dc.base.util.ExcelUtil;
import com.dc.controller.SysUserController;
import com.dc.dto.SysUserDTO;
import com.dc.mapper.SysFileLogDao;
import com.dc.mapper.SysRoleDao;
import com.dc.mapper.SysUserDao;
import com.dc.pojo.SysRole;
import com.dc.pojo.SysUser;
import com.dc.pojo.SysUserRole;
import com.dc.service.SysFileLogService;
import com.dc.service.SysUserService;
import com.dc.vo.SysUserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao userDao;
    @Autowired
    private SysFileLogService fileLogService;
    @Autowired
    private SysFileLogDao fileLogDao;
    @Autowired
    private SysRoleDao roleDao;

    public BaseModel selectUserAll(Integer page, Integer maxSize) {
        BaseModel baseModel = new BaseModel();
        PageHelper.startPage(page, maxSize);
        List<SysUser> users = userDao.selectUserAll();
        PageInfo userList = new PageInfo(users, 5);
        baseModel.setResultCode(0);//如果为0则表示成功
        baseModel.setMessage("查询用户列表成功！");
        baseModel.setData(userList);
        return baseModel;
    }

    /**
     * @return void
     * @title:<h3> 新增用户<h3>
     * @author: Enzo
     * @date: 2018-11-15 16:25
     * @params [userVo, baseModel]
     **/
    public void inserUserVo(SysUserVo userVo, BaseModel baseModel) throws Exception {
//验证用户名
        SysUser oldUser = userDao.selectUserByAccount(userVo.getUser().getAccount());
        if (oldUser != null) {
            throw new BusinessException("帐号已存在，请勿重复添加");
        }
        //判断是否进行文件上传
        fileLogService.uploadFiles(baseModel, userVo.getUser().getCreate_user_name());
        userVo.getUser().setHead_img_url(baseModel.getFilesArray());
        //新增用户
        userDao.insertUser(userVo.getUser());
        //新增用户角色关系
        if (userVo.getListUserRole() != null && userVo.getListUserRole().size() > 0) {//存在用户角色关系表记录
            for (int i = 0; i < userVo.getListUserRole().size(); i++) {
                SysUserRole userRole = userVo.getListUserRole().get(i);
                userRole.setFk_user_id(userVo.getUser().getUser_id());
                userDao.insertUserRole(userRole);
            }
        }
    }

    /**
     * @return void
     * @title:<h3> 分页查询用户列表信息 <h3>
     * @author: Enzo
     * @date: 2018-11-16 14:24
     * @params [baseModel]
     **/
    public void selectPageUserVo(BaseModel baseModel) throws Exception {
        PageHelper.startPage(baseModel.getQueryParams().getCurr_page(), baseModel.getQueryParams().getPage_size());
        List<SysUserVo> list = userDao.selectUserVo(baseModel.getQueryParams());
        PageInfo page = new PageInfo(list);
        baseModel.setData(page);

    }

    /**
     * @return void
     * @title:<h3> 根据id查询用户详情 <h3>
     * @author: Enzo
     * @date: 2018-11-16 15:33
     * @params [pk, baseModel]
     **/
    public void selectUserVoByPk(int pk, BaseModel baseModel) throws Exception {
        baseModel.setData(userDao.selectUserVoByPk(pk));
    }

    /**
     * @return void
     * @title:<h3> 更新用户信息 <h3>
     * @author: Enzo
     * @date: 2018-11-19 10:06
     * @params [userVo, baseModel]
     **/
    public void updateUserVo(SysUserVo userVo, BaseModel baseModel) throws Exception {
        //判断账号是否重复
        SysUser oldUser = userDao.selectUserByAccount(userVo.getUser().getAccount());
        if (oldUser != null && oldUser.getUser_id() != userVo.getUser().getUser_id()) {//存在用户信息，并且id不是当前修改的记录，则说明帐号重复
            throw new BusinessException("帐号'" + userVo.getUser().getAccount() + "'已存在，请勿重复添加");
        }
        //判断是否进行文件上传（更新上传文件id）
        fileLogService.uploadFiles(baseModel, userVo.getUser().getUpdate_user_name());
        if (baseModel.getFilesArray() != null) {//如果存在头像更新，则更新头像id
            userVo.getUser().setHead_img_url(baseModel.getFilesArray());
            //删除文件上传记录表中的记录
            fileLogDao.removeFileLogInUserIds(userVo.getUser().getUser_id() + "");
        }
        //修改用信息
        int count = userDao.updateUser(userVo.getUser());
        if (count == 0) {
            throw new BusinessException(ErrorMesgEnum.UPDATE_VES);
        }
        //删除用户角色关系表记录
        userDao.deleteUserRoleInUserIds(userVo.getUser().getUser_id() + "");
        //新增用户角色关系表记录
        if (userVo.getListUserRole() != null && userVo.getListUserRole().size() > 0) {//存在用户角色关系表记录
            for (int i = 0; i < userVo.getListUserRole().size(); i++) {
                SysUserRole userRole = userVo.getListUserRole().get(i);
                userRole.setFk_user_id(userVo.getUser().getUser_id());
                userDao.insertUserRole(userRole);
            }
        }
    }

    /**
     * @return void
     * @title:<h3> 根据用户id删除用户信息 <h3>
     * @author: Enzo
     * @date: 2018-11-19 11:16
     * @params [ids, baseModel]
     **/
    public void deleteUserVoInUserIds(String ids, BaseModel baseModel) throws Exception {
        //删除用户角色关系表记录
        userDao.deleteUserRoleInUserIds(ids);
        //删除用户头像记录
        fileLogDao.removeFileLogInUserIds(ids);
        //删除用户信息
        int count = userDao.removeUserInUserIds(ids);
        baseModel.setMessage("删除‘" + count + "’条用户信息成功");
    }

    /**
     * @return void
     * @title:<h3> 状态切换 <h3>
     * @author: Enzo
     * @date: 2018-11-19 13:48
     * @params [user, baseModel]
     **/
    public void switchState(SysUser user, BaseModel baseModel) throws Exception {
//根据用户id查询原有的记录中状态值
        SysUser oldUser = userDao.selectUserByUserId(user.getUser_id());
        if (oldUser != null) {
            if (oldUser.getState() == 1) {
                user.setState(0);
            } else if (oldUser.getState() == 0) {
                user.setState(1);
            }
        } else {
            throw new BusinessException("修改的用户信息不存在");
        }
        int count = userDao.switchState(user);
        if (count == 0) {
            throw new BusinessException("更新状态失败");
        }
        //将状态值改变
    }

    /**
     * @return void
     * @title:<h3>重置密码 <h3>
     * @author: Enzo
     * @date: 2018-11-19 13:48
     * @params [user, baseModel]
     **/
    public void resetPassword(SysUser user, BaseModel baseModel) throws Exception {
        int count = userDao.resetPassword(user);
        if (count == 0) {
            throw new BusinessException("重置密码失败");
        }
    }

    /**
     * @return java.lang.String
     * @title:<h3> 导出用户信息<h3>
     * @author: Enzo
     * @date: 2018-11-21 10:41
     * @params [baseModel]
     **/
    public String exportUserDTO(BaseModel baseModel) throws Exception {
        PageHelper.startPage(baseModel.getQueryParams().getCurr_page(), baseModel.getQueryParams().getPage_size());
        List<SysUserVo> listVo = userDao.selectUserVo(baseModel.getQueryParams());
        List<SysUserDTO> listDTO = new ArrayList<SysUserDTO>();
        String[] sex = {"保密", "男", "女"};
        String[] state = {"冻结", "激活"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < listVo.size(); i++) {
            SysUserDTO dto = new SysUserDTO();
            dto.setCount(i + 1);
            dto.setAccount(listVo.get(i).getUser().getAccount());
            dto.setName(listVo.get(i).getUser().getName());
            dto.setAddress(listVo.get(i).getUser().getAddress());
            dto.setMemo(listVo.get(i).getUser().getMemo());
            dto.setEmail(listVo.get(i).getUser().getEmail());
            dto.setPhone(listVo.get(i).getUser().getPhone());
            dto.setRole_names(listVo.get(i).getRole_names());
            dto.setSex_str(sex[listVo.get(i).getUser().getSex()]);
            dto.setState_str(state[listVo.get(i).getUser().getState()]);
            if (listVo.get(i).getUser().getBirthday() != null) {
                dto.setBirthday_str(sdf.format(listVo.get(i).getUser().getBirthday()));
            }
            listDTO.add(dto);
        }
        return ExcelUtil.expTemplateExcel(listDTO, "SysUser.xls");
    }

    /**
     * @return void
     * @title:<h3> 导入用户信息 <h3>
     * @author: Enzo
     * @date: 2018-11-21 14:42
     * @params [baseModel]
     **/
    @Transactional(rollbackFor = BusinessException.class)
    public void impUserDTO(SysUser sessionUser, BaseModel baseModel) throws Exception {
        int count = 0;
        StringBuilder userName = new StringBuilder("");
//解析excel，返回ListMap
        List<Map<String, Object>> listMap = ExcelUtil.getExcelData(baseModel.getTempMFile());
        //遍历ListMap，将map转po对象
        Map<String, Integer> mapSex = new HashMap<String, Integer>();
        mapSex.put("保密", 0);
        mapSex.put("男", 1);
        mapSex.put("女", 2);
        Map<String, Integer> mapState = new HashMap<String, Integer>();
        mapState.put("激活", 1);
        mapState.put("冻结", 0);
        Map<String, Integer> mapRole = new HashMap<String, Integer>();//角色名称id
        List<SysRole> listRole = roleDao.selectRoleName();//查询全部角色名称
        for (int i = 0; i < listRole.size(); i++) {//遍历角色名称，将其存入map
            mapRole.put(listRole.get(i).getName(), listRole.get(i).getRole_id());
        }
        for (int i = 0; i < listMap.size(); i++) {
            SysUser user = new SysUser();
            try {
                // user.setAccount(listMap.get(i).get("account").toString());
                //user.setName(listMap.get(i).get("name").toString());
                //user.setPhone(listMap.get(i).get("phone").toString());
                BaseUtil.mapToEntity(listMap.get(i), user);
                //验证用户数据
                if (user.getAccount() == null || "".equals(user.getAccount())) {
                    throw new BusinessException("第" + (i + 1) + "行,帐号不能为空");
                } else if (user.getName() == null || "".equals(user.getName())) {
                    throw new BusinessException("第" + (i + 1) + "行,用户姓名不能为空");
                }
                //验证用户名
                SysUser oldUser = userDao.selectUserByAccount(user.getAccount());
                if (oldUser != null) {
                    throw new BusinessException("第" + (i + 1) + "行,帐号‘"
                            + user.getAccount() + "’已存在，请勿重复添加");
                }
                user.setPassword(MD5DES.encrypt(SysUserController.DEFAULT_PASSOWRD));
                user.setCreate_user_name(sessionUser.getName());
                user.setCreate_user_id(sessionUser.getUser_id());

                //验证输入导入参数是否合法
                String sex_str = (String) listMap.get(i).get("sex_str");//性别
                String state_str = (String) listMap.get(i).get("state_str");//状态
                String role_names = (String) listMap.get(i).get("role_names");//角色名称
                if (sex_str != null && !"".equals(sex_str)) {//验证性别
                    if (mapSex.get(sex_str) == null) {
                        throw new BusinessException("第" + (i + 1) + "行,性别不能为“" + sex_str + "”");
                    }
                    user.setSex(mapSex.get(sex_str));
                }
                user.setState(1);
                if (state_str != null && !"".equals(state_str)) {//验证状态
                    if (mapState.get(state_str) == null) {
                        throw new BusinessException("第" + (i + 1) + "行,状态不能为“" + state_str + "”");
                    }
                    user.setState(mapState.get(state_str));
                }
                List<SysUserRole> listUserRole = new ArrayList<SysUserRole>();
                if (role_names != null && !"".equals(role_names)) {//验证角色名称
                    String[] arrRoleName = role_names.split(",");
                    for (int j = 0; j < arrRoleName.length; j++) {
                        if (mapRole.get(arrRoleName[j]) == null) {//如果角色名称不存在map中，即角色名称不存在
                            throw new BusinessException("第" + (i + 1) + "行,角色名称“" + arrRoleName[j] + "”不存在");
                        }
                        SysUserRole userRole = new SysUserRole();
                        userRole.setFk_role_id(mapRole.get(arrRoleName[j]));
                        listUserRole.add(userRole);
                    }
                }
                //验证po对象数据，进行插入
                count += userDao.insertUser(user);
                userName.append("," + user.getAccount());
                //插入用户角色关系表
                for (int j = 0; j < listUserRole.size(); j++) {
                    listUserRole.get(j).setFk_user_id(user.getUser_id());
                    userDao.insertUserRole(listUserRole.get(j));
                }
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                throw new BusinessException("第" + (i + 1) + "行数据转换异常");
            }
        }
        baseModel.setMessage("导入'" + count + "'条用户信息成功");
        if (userName.toString().length() > 0) {
            baseModel.setAop_mesg(userName.toString().substring(1));
        }
    }

    /**
     * @return void
     * @title:<h3> 更新个人信息 <h3>
     * @author: Enzo
     * @date: 2018-11-23 15:46
     * @params [userVo, baseModel]
     **/
    public void updateMyUserVo(SysUserVo userVo, BaseModel baseModel) throws Exception {
        userVo.getUser().setAccount(null);//不更新帐号
        if (userVo.getOld_password() != null && !"".equals(userVo.getOld_password())) {//如果传入了久密码，则进行密码更新验证
            SysUser oldUser = userDao.selectUserByUserId(userVo.getUser().getUser_id());
            if (!MD5DES.encrypt(userVo.getOld_password()).equals(oldUser.getPassword())) {
                throw new BusinessException("原密码不正确");
            }else{
                //密码加密
                userVo.getUser().setPassword(MD5DES.encrypt(userVo.getUser().getPassword()));
            }
        }else{//如果未传入原密码，则不更密码
            userVo.getUser().setPassword(null);
        }
        //处理头像上传
        //判断是否进行文件上传（更新上传文件id）
        fileLogService.uploadFiles(baseModel, userVo.getUser().getUpdate_user_name());
        if (baseModel.getFilesArray() != null) {//如果存在头像更新，则更新头像id
            userVo.getUser().setHead_img_url(baseModel.getFilesArray());
            //删除文件上传记录表中的记录
            fileLogDao.removeFileLogInUserIds(userVo.getUser().getUser_id() + "");
        }
        //修改用信息
        int count = userDao.updateUser(userVo.getUser());
        if (count == 0) {
            throw new BusinessException(ErrorMesgEnum.UPDATE_VES);
        }

    }
}
