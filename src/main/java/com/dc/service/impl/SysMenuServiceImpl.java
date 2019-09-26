package com.dc.service.impl;

import com.dc.base.contants.GlobalVar;
import com.dc.base.pojo.BaseModel;
import com.dc.mapper.SysMenuDao;
import com.dc.pojo.SysMenu;
import com.dc.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-14 13:56
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    SysMenuDao menuDao;
    /**
     * @title:<h3>查询全部菜单  <h3>
     * @author: Enzo
     * @date: 2018-11-14 13:58
     * @params []
     * @return java.util.List<com.dc.pojo.SysMenu>
     **/
    public void selectAllMenu() throws Exception {
        if(GlobalVar.mapCodeMenu==null){
            refreshSysMenu(new BaseModel());
        }
    }
/**
 * @title:<h3> 刷新权限菜单 <h3>
 * @author: Enzo
 * @date: 2018-11-14 14:22
 * @params [baseModel]
 * @return void
 **/
    public void refreshSysMenu(BaseModel baseModel) throws Exception {
        GlobalVar.mapCodeMenu=new HashMap<String, SysMenu>();
        GlobalVar.mapMenuIdCode=new HashMap<Integer, String>();
        List<SysMenu>list=menuDao.selectAllMenu();
        for(int i=0;i<list.size();i++){
            GlobalVar.mapCodeMenu.put(list.get(i).getCode(),list.get(i));
            GlobalVar.mapMenuIdCode.put(list.get(i).getMenu_id(),list.get(i).getCode());
        }
        baseModel.setData(list);
    }
/**
 * @title:<h3> 查询我的权限菜单 <h3>
 * @author: Enzo
 * @date: 2018-11-20 11:14
 * @params [map, baseModel]
 * @return void
 **/
    public void findMyMenu(Map<String, String> map, BaseModel baseModel)throws Exception {
        selectAllMenu();
        Map<Integer,String>mapIdCode=new HashMap<Integer, String>();//菜单id和编号的map
        List<SysMenu>listMenu1=new ArrayList<SysMenu>();//一级菜单列表
        List<SysMenu>listMenu2=new ArrayList<SysMenu>();//二级菜单列表
        for(Map.Entry<String,String>entry:map.entrySet()){//遍历权限
            SysMenu menu=GlobalVar.mapCodeMenu.get(entry.getKey());
            if(menu==null){
                continue;
            }
            if(menu.getIs_leaf()==0){//一级菜单
                listMenu1.add(menu);
            }else{
                listMenu2.add(menu);
                //判断是否添加父级菜单
                if(menu.getFk_parent_id()!=0&&mapIdCode.get(menu.getFk_parent_id())==null){//是二级菜单，并且未添加一级菜单
                    listMenu1.add(GlobalVar.mapCodeMenu.get(GlobalVar.mapMenuIdCode.get(menu.getFk_parent_id())));//添加一级菜单
                    mapIdCode.put(menu.getFk_parent_id(),GlobalVar.mapMenuIdCode.get(menu.getFk_parent_id()));//标志一级菜单已添加
                }
            }
            mapIdCode.put(menu.getMenu_id(),menu.getCode());//编辑该菜单已添加到返回值中
        }
        Map<String,List<SysMenu>>result=new HashMap<String, List<SysMenu>>();
        result.put("menu_1",listMenu1);
        result.put("menu_2",listMenu2);
        baseModel.setData(result);
    }
}
