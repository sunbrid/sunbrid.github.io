var userSettingParams = {
    action: {
        findMyUser: "sysUser/findMyUserVo",
        updatMyUser: "sysUser/updateMyUserVo"
    }
}
$(function () {
    new uploadPreview({
        UpBtn: "tempFile",
        DivShow: "imgGroup",
        ImgShow: "imgPreview"
    });
    findMyUser();
    //修改密码
    $("#btn_update_password").click(function () {
        $(".updatePasswordItem").removeClass("hidden");
        $("#btn_cancel_update_password").removeClass("hidden");
        $(this).addClass("hidden");
    });
    //取消修改密码
    $("#btn_cancel_update_password").click(function () {
        $(".updatePasswordItem").addClass("hidden").find("input").val("");
        $(this).addClass("hidden");
        $("#btn_update_password").removeClass("hidden");
    });
    //修改事件
    $("#btn_submit").click(function () {
        submitMyUser();
    });
})

/**
 * @title:<h3>查询我的个人信息  <h3>
 * @author: Enzo
 * @date: 2018-10-29 9:15
 * @params
 * @return
 **/
function findMyUser() {
    requestUtilParams.xhrGet(userSettingParams.action.findMyUser).done(function (res) {
        var user = res.data.user; //获得用户信息
        $("#version-user-submit").val(user.version);
        $("#account-user-submit").val(user.account);
        $("#name-user-submit").val(user.name);
        $("#phone-user-submit").val(user.phone);
        $("#email_user_sumit").val(user.email);
        $("#birthday-user-submit").val(baseUtilParams.simpDataForYYYMMDD(user.birthday));
        $("#sex_user_group input[value='" + user.sex + "']").prop("checked",true);
        $("#sex_user_group input[value='" + user.sex + "']").next(".layui-form-radio").click();
        //设置头像
        if (res.data.fileLog != null) {
            $("#imgPreview").attr("src", requestUtilParams.convertFilePath(res.data.fileLog.save_path));
        }
    });
}

/**
 * @title:<h3> 更新个人信息 <h3>
 * @author: Enzo
 * @date: 2018-10-29 9:15
 * @params
 * @return
 **/
function submitMyUser() {
    if (checkData()) {
        requestUtilParams.xhrUpload(userSettingParams.action.updatMyUser,"#form_submit").done(function (res) {
            alert(res.message);
            $("#btnClose").click();
        })
    }
}


function checkData() {
    var str = "";
    if ($("#btn_update_password").hasClass("hidden")) {//如果修改按钮被隐藏，则说明修改密码
        var old_password = $("#old_password_submit").val();
        var password_user_submit = $("#password_user_submit").val();
        var passowrd_user_comfim = $("#passowrd_user_comfim").val();
        if (old_password == "") {
            str += "旧密码不能为空\n";
        }
        if (passowrd_user_comfim == "" || passowrd_user_comfim == "") {
            str += "新密码不能为空\n"
        }
        if (password_user_submit != passowrd_user_comfim) {
            str += "两次输入的密码不一致\n";
        }
    }

    if (str.length > 0) {
        alert(str);
        return false;
    } else {
        return true;
    }
}