var userListParams = {
    page: {
        add: ["system/user/userAdd.html", "userAdd", "新增用户"],
        edit: ["system/user/userEdit.html?pk=", "userEdit", "编辑用户"]
    },
    model: {
        "su.account": {
            "comment": "用户名",
            "type": "varchar",
        }
        , "su.name": {
            "comment": "姓名"
        }
        , "su.state": {
            "comment": "状态",
            "type": "number",
            "item": {
                "0": "禁用",
                "1": "启用"
            }
        }, "su.sex": {
            "comment": "性别",
            "type": "number",
            "item": {
                "0": "保密",
                "1": "男",
                "2": "女"
            }
        }
        , "su.phone": {
            "comment": "电话"
        }, "su.birthday": {
            "comment": "出生日期",
            "type": "date"
        }, "su.create_time": {
            "comment": "创建时间",
            "type": "datetime"
        }
    },
    action: {
        findPage: "sysUser/selectPageUserVo",
        del: "sysUser/deleteUser/",
        reset: "sysUser/resetPassword/",
        changeState: "sysUser/tiggerState/",
        exp: "sysUser/expUserDTO",
        imp: "sysUser/impUserDTO"
    }
}
$(function () {
    requestUtilParams.staticJson = false;
    $("#btnAdd").click(function () {
        globalParams.tabInnerChange(userListParams.page.add);
    });
    $("#btnAdvancedSearch").click(function () {
        requestUtilParams.createAdvancedQueryDialog(userListParams.model, "#userSearchDialog", findPageData);

    });
    $("#btnSearch").click(function () {//搜索
        requestUtilParams.searchForm("#formSearch");
        findPageData()
    })

    findPageData();
    $("#btnDelete").click(function () {
        deleteUser();
    });
    $("#tableList").on("click", ".layui-form-switch", function () {
        var uId = $(this).prev(".state").val();
        requestUtilParams.xhrGet(userListParams.action.changeState + uId).done(function (res) {
            alert(res.message);
        });
    });
    $("#btnExp").click(function () {
        requestUtilParams.expData(userListParams.action.exp, $("#printPage .layui-laypage-curr").text());
    });
    $("#btnImp").click(function () {//导入
        requestUtilParams.impData(userListParams.action.imp);
    });
});

/**
 * @title:<h3>分页查询用户列表数据  <h3>
 * @author: Enzo
 * @date: 2018-10-22 13:35
 * @params
 * @return
 **/
function findPageData(cpage) {
    requestUtilParams.findPageData(userListParams.action.findPage, cpage, "#tableList", "printPage", findPageData).done(function (res) {
        var list = res.data.list;
        var $tbody = $("#tableList tbody");
        for (var i = 0; i < list.length; i++) {
            var $tr = $("<tr></tr>");
            $tr.append('<td><input type="checkbox" class="inpDel" value="' + list[i].user_id + '"></td>');
            $tr.append('<td>' + list[i].user.account + '</td>');
            $tr.append('<td>' + baseUtilParams.strNullFilter(list[i].user.phone) + '</td>');
            $tr.append('<td>' + baseUtilParams.strNullFilter(list[i].user.name) + '</td>');
            $tr.append('<td>' + baseUtilParams.strNullFilter(list[i].role_names) + '</td>');
            $tr.append('<td>' + baseUtilParams.strNullFilter(list[i].user.address) + '</td>');
            $tr.append('<td>' + baseUtilParams.simpDataForYYYMMDD(list[i].user.birthday) + '</td>');
            $tr.append('<td class="layui-form"><input class="state permission_update hidden_per" type="checkbox" lay-skin="switch" value="' + list[i].user_id + '"></td>');
            if (list[i].user.state == 1) {
                $tr.find(".state").prop("checked", true);
            }
            var $btns = $("<td></td>");
            $btns.append('<a onclick="gotoEditPage(' + list[i].user_id + ')" class="layui-btn layui-btn-xs layui-btn-normal">编辑</a>')
            $btns.append('<a onclick="resetPsw(' + list[i].user_id + ')" class="layui-btn layui-btn-xs layui-btn-warm permission_update hidden_per">重置密码</a>')
            $btns.append('<a class="layui-btn layui-btn-danger layui-btn-xs permission_delete hidden_per" onclick="deleteUser(' + list[i].user_id + ')">删除</a>');
            $tr.append($btns);
            $tbody.append($tr);
        }
        requestUtilParams.removePermissionBtns(res.permission_btns);
        $(".permission_update.hidden_per").each(function () {
            $(this).prop("disabled", true);
        })
        layui.use("form", function () {
            var form = layui.form;
            form.render();

        });

    })
}

function gotoEditPage(pk) {
    globalParams.tabInnerChange(userListParams.page.edit, pk);
}

/**
 * @title:<h3> 删除用户信息 <h3>
 * @author: Enzo
 * @date: 2018-10-26 11:31
 * @params
 * @return
 **/
function deleteUser(pk) {
    requestUtilParams.deleteData(userListParams.action.del, pk, "#tableList", findPageData);
}

/*
* @title:<h3> 重置用户密码 <h3>
* @author: Enzo
* @date: 2018-10-26 14:29
* @params
* @return
**/
function resetPsw(pk) {
    requestUtilParams.xhrGet(userListParams.action.reset + pk).done(function (res) {
        alert(res.message);
    });
}



