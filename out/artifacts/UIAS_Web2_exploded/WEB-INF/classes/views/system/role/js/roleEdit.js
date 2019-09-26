var roleEditParams = {
    action: {
        findRoleVoByPk: "role/selectRoleVoById/"
        , findRolePerssion: "index/selectEnumRoleMenu"
        , add: "role/insertRoleVo"
        ,update:"role/updateRoleVo"
    }
}
$(function () {
    requestUtilParams.staticJson=false;
    //角色权限全选事件
    $("#rolePerssionGroup").on("click", "dt .layui-form-checkbox", function () {
        if ($(this).hasClass("layui-form-checked")) {//说明选中
            $(this).parent().siblings("dd").find(".layui-form-checkbox").not(".layui-form-checked").click();
            $(this).siblings("input[type='checkbox']", true);
        } else {//说明没选中
            //alert($(this).parent().parent().siblings("dd").find(".layui-form-checkbox.layui-form-checked").length)
            $(this).parent().siblings("dd").find(".layui-form-checkbox.layui-form-checked").click();
            $(this).siblings("input[type='checkbox']", false);
        }
    });
    //角色权限选择事件
    $("#rolePerssionGroup").on("click", "dd .layui-form-checkbox", function () {
        var ddlength = $(this).parent().parent().find("dd").length;//全部的权限功能
        var chdl = $(this).parent().parent().find("dd .layui-form-checked").length;//选中的权限功能
        if (ddlength == chdl) {
            $(this).parent().siblings("dt").find(".layui-form-checkbox").addClass("layui-form-checked");
            $(this).parent().siblings("dt").find("input").prop("checked", true);
        } else {
            $(this).parent().siblings("dt").find(".layui-form-checkbox").removeClass("layui-form-checked");
            $(this).parent().siblings("dt").find("input").prop("checked", false);
        }

    });
    findRolePermission();
    //获得地址栏的地址
    var pk = baseUtilParams.GetQueryString("pk");
    if (pk != null) {//说明当前是编辑页面
       // pk = 31;
        showEditData(pk);
    }
    $("#btnSubmit").click(function () {
        submitAdd();
    })
    $("#btnSubmitUpdate").click(function () {
        submitUpdate();
    })
});


/**
 * @title:<h3> 显示角色管理编辑页面数据 <h3>
 * @author: Enzo
 * @date: 2018-10-17 10:01
 * @params pk【主键id】
 * @return
 **/
function showEditData(pk) {
    layui.use('form', function () {
        requestUtilParams.xhrGet(roleEditParams.action.findRoleVoByPk+pk).done(function (res) {
            //alert("请求成功");
            var role = res.data.role;
            var listRolePers = res.data.listRolePermission;
            $("#name_role_submit").val(role.name);
            $("#memo_role_submit").val(role.memo);
            $("#version_role_submit").val(role.version);
            $("#role_id_role_submit").val(role.role_id);
            for (var i = 0; i < listRolePers.length; i++) {
                var $dt = $("#rolePerssionGroup dt .r_" + listRolePers[i].code).parent();
                for (var j = 0; j < listRolePers[i].permission_value.length; j++) {
                    if (listRolePers[i].permission_value[j] == '1') {
                        $dt.siblings("dd").eq(j).find("input[type='checkbox']").prop("checked", true);
                        $dt.siblings("dd").eq(j).find(".layui-form-checkbox").addClass("layui-form-checked");
                    }

                }
                if($dt.siblings("dd").find("input[type='checkbox']").length
                    ==$dt.siblings("dd").find("input[type='checkbox']:checked").length){
                    $dt.find("input[type='checkbox']").prop("checked",true);
                    $dt.find(".layui-form-checkbox").addClass("layui-form-checked");
                }
            }
            $("#formSubmit :input").not(":button").prop("disabled", true);
        });
    });
}

/**
 * @title:<h3>查询权限值  <h3>
 * @author: Enzo
 * @date: 2018-10-22 14:50
 * @params
 * @return
 **/
function findRolePermission() {
    requestUtilParams.xhrGet(roleEditParams.action.findRolePerssion,{async:false}).done(function (res) {
        var list = res.data;
        $("#rolePerssionGroup").empty();
        for (var i = 0; i < list.length; i++) {
            var $dl = $("<dl></dl>");
            $dl.append('<dt class="permissionName" >' +
                '<input type="checkbox" title="' + list[i].name + '"  class="r_'+list[i].code+'">' +
                '<input type="hidden" class="in_code" name="" value="' + list[i].code + '">' +
                '<input type="hidden" class="in_permission_value"name="" value=""></dt>');
            var values = list[i].roleValue.split(",");
            for (var j = 0; j < values.length; j++) {
                $dl.append('<dd><input type="checkbox" title="' + values[j] + '"></dd>');
            }
            $("#rolePerssionGroup").append($dl);
        }
        layui.use("form", function () {
            var form = layui.form;
            form.render();
        })
    });
}

/**
 * @title:<h3> 新增角色信息 <h3>
 * @author: Enzo
 * @date: 2018-10-22 15:46
 * @params
 * @return
 **/
function submitAdd() {
    if (checkData()) {
        var data = $("#formSubmit").serialize();
        requestUtilParams.xhr(roleEditParams.action.add, data).done(function (res) {
            alert(res.message);
            $("#formSubmit")[0].reset();
        });
    }
}
/**
 * @title:<h3>修改角色信息  <h3>
 * @author: Enzo
 * @date: 2018-10-23 16:25
 * @params
 * @return
 **/
function submitUpdate() {
    if(checkData()){
        var data=$("#formSubmit").serialize();
        requestUtilParams.xhr(roleEditParams.action.update,data).done(function (res) {
           alert(res.message);
           $("#btnClose").click();
        });
    }
}

/**
 * @title:<h3> 验证角色提交 <h3>
 * @author: Enzo
 * @date: 2018-10-22 15:47
 * @params
 * @return
 **/
function checkData() {
    var str = "";
    var name = $("#name-role-submit").val();
    if (name == "") {
        str += "请输入角色名称\n";
    }
    if ($("#rolePerssionGroup input[type='checkbox']:checked").length == 0) {
        str += "请至少选择一个角色权限\n";
    }

    if (str.length > 0) {
        alert(str);
        return false;
    } else {
        //遍历角色权限
        var num = 0;
        $("#rolePerssionGroup dl").each(function () {
            if ($(this).find("input:checked").length == 0) {
                $(this).find(".in_code").attr("name", "");
                $(this).find(".in_permission_value").attr("name", "");
                return true;
            }
            $(this).find(".in_code").attr("name", "listRolePermission[" + num + "].code");
            var value = "";
            $(this).find("dd>input").each(function () {
                if ($(this).prop("checked")) {
                    value += "1";
                } else {
                    value += "0";
                }
            })
            $(this).find(".in_permission_value")
                .attr("name", "listRolePermission[" + num + "].permission_value").val(value);
            num++;
        });
        return true;
    }
}