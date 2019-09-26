var userEditParams = {
    action: {
        findRoleName: "role/selectRoleName",
        addUser: "sysUser/insertUserVo"
        , findUserByPk: "sysUser/selectUserVoByUserId/"
        , updateUser: "sysUser/updateUserVo"
    }
}
$(function () {
    requestUtilParams.staticJson=false;
    findRoleName();
    $("#btnSubmit").click(function () {
        //新增用户
        submitAdd();
    });
    new uploadPreview({
        UpBtn: "tempFile",
        DivShow: "imgGroup",
        ImgShow: "imgPreview"
    });
    var pk = baseUtilParams.GetQueryString("pk");
    if (pk != null) {//说明是编辑页面
        //查询编辑页面数据
        findDataByPk(pk);
    }
    $("#btnSubmitUpdate").click(function () {
        submitUpdate();
    })
})

/**
 * @title:<h3> 获得角色信息 <h3>
 * @author: Enzo
 * @date: 2018-10-24 9:45
 * @params
 * @return
 **/
function findRoleName() {
    requestUtilParams.xhrGet(userEditParams.action.findRoleName, {async: false}).done(function (res) {
        $("#roleGroup").empty();
        var list = res.data;
        for (var i = 0; i < list.length; i++) {
            $("#roleGroup").append("<input type='checkbox' title='" + list[i].name + "' value='" + list[i].role_id + "'>")
        }
        layui.use("form", function () {
            var form = layui.form;
            form.render();
            $("#form_submit .layui-checkbox-disbaled").removeClass("layui-checkbox-disbaled");
            $("#form_submit .layui-radio-disbaled").removeClass("layui-radio-disbaled");
        });
    });
}

/**
 * @title:<h3> 新增用户 <h3>
 * @author: Enzo
 * @date: 2018-10-24 10:43
 * @params
 * @return
 **/
function submitAdd() {
    if (checkData()) {
        //var data = $("#formSubmit").serialize();
        //requestUtilParams.xhr(userEditParams.action.addUser, data).done(function (res) {
        // });
        requestUtilParams.xhrUpload(userEditParams.action.addUser, "#formSubmit").done(function (res) {
            alert(res.message);
            $("#formSubmit")[0].reset();
        })


    }
}


/**
 * @title:<h3> 验证用户提交数据 <h3>
 * @author: Enzo
 * @date: 2018-10-24 10:43
 * @params
 * @return
 **/
function checkData() {
    var str = "";
    var account = $("#account-user-submit").val();
    var name = $("#name-user-submit").val();
    if (account == "") {
        str += '用户名不能为空\n';
    }
    if (name == "") {
        str += "姓名不能为空\n"
    }
    if (str.length > 0) {
        //说明验证不通过
        alert(str);
        return false;
    } else {
        var num = 0
        $("#roleGroup input:checked").each(function () {
            $(this).attr("name", "listUserRole[" + num + "].fk_role_id");
            num++;
        });
        return true;
    }
}

/**
 * @title:<h3> 根据用户id查询用户信息 <h3>
 * @author: Enzo
 * @date: 2018-10-26 9:11
 * @params
 * @return
 **/
function findDataByPk(pk) {
    requestUtilParams.xhrGet(userEditParams.action.findUserByPk + pk).done(function (res) {
        var user = res.data.user;
        var listUserRole = res.data.listUserRole;
        $("#user_id-user-submit").val(user.user_id);
        $("#version-user-submit").val(user.version);
        $("#account-user-submit").val(user.account);
        $("#name-user-submit").val(user.name);
        $("#memo-user-submit").val(user.memo);
        $("#phone-user-submit").val(user.phone);
        //$("#address-user-submit").val(user.address);
        $("#birthday-user-submit").val(baseUtilParams.simpDataForYYYMMDD(user.birthday));
        //性别复选框选中效果，将原有的复选框全部取消选中效果，然后添加当前选中的复选框的选中效果
        //$("#sexGroup input:checked").prop("checked", false).next(".layui-form-radio").removeClass("layui-form-radioed");
        $("#sexGroup input[value='" + user.sex + "']").next(".layui-form-radio").click();
        //设置角色选择效果
        for (var i = 0; i < listUserRole.length; i++) {
            $("#roleGroup input[value='"+listUserRole[i].fk_role_id+"']").prop("checked", true).next(".layui-form-checkbox").addClass("layui-form-checked");
        }
        //设置头像
        if(res.data.fileLog!=null){
            $("#imgPreview").attr("src",requestUtilParams.convertFilePath(res.data.fileLog.save_path));
        }
        $("#btnCancel").click();
    });
}
/**
 * @title:<h3> 修改用户信息 <h3>
 * @author: Enzo
 * @date: 2018-10-26 10:27
 * @params
 * @return
 **/
function submitUpdate() {
    if(checkData()){
        requestUtilParams.xhrUpload(userEditParams.action.updateUser,"#form_submit").done(function (res) {
           alert(res.message);
           $("#btnClose").click();
        });
    }
}
