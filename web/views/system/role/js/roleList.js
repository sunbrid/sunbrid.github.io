var roleListParams = {
    page: {
        add: ["system/role/roleAdd.html", "nav_roleAdd", "角色新增"],
        update: ["system/role/roleEdit.html?pk=", "nav_roleEdit", "编辑角色信息"]
    },
    action: {
        findPageData: "role/selectPageRole",
        delRole: "role/deleteRole/"
    }
}
$(function () {
    requestUtilParams.staticJson = false;
    $("#btnAdd").click(function () {
        globalParams.tabInnerChange(roleListParams.page.add);
    });
    findPageData();
    $("#btnDelete").click(function () {
        deleteRole();
    })
    $("#btnSearch").click(function () {//搜索
        requestUtilParams.searchForm("#formSearch");
        findPageData()
    })
});

/**
 * @title:<h3> 分页查询角色列表信息 <h3>
 * @author: Enzo
 * @date: 2018-10-15 16:02
 * @params
 * @return
 **/
function findPageData(cpage) {
    requestUtilParams.findPageData(roleListParams.action.findPageData, cpage, "#tabelList", "printPage", findPageData).done(function (res) {
        var list = res.data.list;
        var $tbody = $("#tabelList tbody");
        for (var i = 0; i < list.length; i++) {
            var $tr = $("<tr></tr>");
            $tr.append(" <td><input type=\"checkbox\" class=\"inpDel\" value='" + list[i].role_id + "'></td>");
            $tr.append("<td>" + list[i].name + "</td>")
            $tr.append("<td>" + list[i].memo + "</td>")
            if (list[i].update_user_name == null || list[i].update_user_name == "") {
                $tr.append("<td>" + (list[i].create_user_name) + "</td>")
            } else {
                $tr.append("<td>" + (list[i].update_user_name) + "</td>")
            }
            if (list[i].update_time == null || list[i].update_time == "") {
                $tr.append("<td>" + baseUtilParams.simpDataFormat(list[i].create_time) + "</td>")
            } else {
                $tr.append("<td>" + baseUtilParams.simpDataFormat(list[i].update_time) + "</td>")
            }
            var $btns = $("<td></td>");
            $btns.append("<a class=\"layui-btn layui-btn-xs layui-btn-normal\" onclick=gotoEditPage(" + list[i].role_id + ")>编辑</a>")
            $btns.append("<a class=\"layui-btn layui-btn-xs layui-btn-danger permission_delete hidden_per\"  onclick='deleteRole(" + list[i].role_id + ")'>删除</a>");
            $tr.append($btns);
            $tbody.append($tr);

        }
        requestUtilParams.removePermissionBtns(res.permission_btns);

    });
}

/**
 * @title:<h3>跳转到编辑页面  <h3>
 * @author: Enzo
 * @date: 2018-10-15 16:31
 * @params
 * @return
 **/
function gotoEditPage(pk) {
    globalParams.tabInnerChange(roleListParams.page.update, pk);
}

/*
* @title:<h3> 删除角色 <h3>
* @author: Enzo
* @date: 2018-10-26 14:51
* @params
* @return
**/
function deleteRole(pk) {
    requestUtilParams.deleteData(roleListParams.action.delRole, pk, "#tabelList", findPageData);
}
