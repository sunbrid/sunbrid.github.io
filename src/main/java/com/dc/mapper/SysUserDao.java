package com.dc.mapper;

import com.dc.base.pojo.QueryParams;
import com.dc.pojo.SysUser;
import com.dc.pojo.SysUserRole;
import com.dc.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface SysUserDao {
    //查询所有用户。
    public List<SysUser> selectUserAll();

    SysUser selectUserByAccount(@Param("account") String account) throws SQLException;

    int insertUser(SysUser user);

    int insertUserRole(SysUserRole userRole);

    List<SysUserVo>selectUserVo(QueryParams queryParams);


    SysUserVo selectUserVoByPk(@Param("pk") int pk);

    int updateUser(SysUser user);

    int deleteUserRoleInUserIds(@Param("userIds") String userIds);

    int removeUserInUserIds(@Param("userIds")String userIds);

    int resetPassword(SysUser user);

    int switchState(SysUser user);

    SysUser selectUserByUserId(@Param("userId")int userId);

}
