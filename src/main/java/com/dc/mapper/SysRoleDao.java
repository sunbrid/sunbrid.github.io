package com.dc.mapper;

import com.dc.vo.SysRoleVo;
import com.dc.base.pojo.QueryParams;
import com.dc.pojo.SysRole;
import com.dc.pojo.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface SysRoleDao {
     List<SysRole> selectRoleAll(@Param("likeName") String likeName);

     List<SysRole>selectRole(QueryParams queryParams)throws SQLException;

     int insertRole(SysRole role);
     int insertRolePermission(SysRolePermission rolePermission);
     SysRole selectRoleByName(@Param("name") String name);

    SysRoleVo selectRoleVoByPk(@Param("pk") int pk);

    int updateRole(SysRole role);

    int deleteRolePermission(@Param("fkRoleIds") String fkRoleIds);

    int removeRoleInPk(@Param("roleIds")String roleIds);

    List<SysRole>selectRoleName();

    int deletUserRoleInRoleIds(@Param("roleIds")String roleIds);

    List<SysRolePermission>selectRolePermissionByUserId(@Param("userId") int userId);
}
