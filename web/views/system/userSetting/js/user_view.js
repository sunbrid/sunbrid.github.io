var userViewParams = {
    action: {
        findMyUser: "sysUser/findMyUserVo",
        findPageMyLoginlog: "loginLog/selectPageMyLoginLog"
    }
}
$(function () {
    findMyUser();
    findPageMyLoginlog();
});

/**
 * @title:<h3> 查询个人信息 <h3>
 * @author: Enzo
 * @date: 2018-10-29 10:47
 * @params
 * @return
 **/
function findMyUser() {
    var sex = ["保密", '男', '女']
    requestUtilParams.xhrGet(userViewParams.action.findMyUser).done(function (res) {
        var user = res.data.user;
        //设置头像
        if (res.data.fileLog != null) {
            $("#portrait_img_view").attr("src", requestUtilParams.convertFilePath(res.data.fileLog.save_path));
        }
        $("#role_names_view").text(baseUtilParams.strNullFilter(res.data.role_names));
        $("#account_user_view").text(baseUtilParams.strNullFilter(user.account));
        $("#sex_user_view").text(sex[user.sex]);
        $("#birthday_user_view").text(baseUtilParams.simpDataForYYYMMDD(user.birthday));
        $("#phone_user_view").text(baseUtilParams.strNullFilter(user.phone));
        $("#email_user_view").text(baseUtilParams.strNullFilter(user.email));
        $("#create_time_user_view").text(baseUtilParams.simpDataFormat(user.create_time));
        $("#update_time_user_view").text(baseUtilParams.simpDataFormat(user.update_time));
    });
}

/**
 * @title:<h3> 分页查询登录日志 <h3>
 * @author: Enzo
 * @date: 2018-10-29 10:42
 * @params
 * @return
 **/
function findPageMyLoginlog(cpage) {
    var state = {
        0: "",
        1: "登录成功",
        2: "帐号不存在",
        3: "密码错误",
        4: "帐号被冻结",
        5: "连续登录失败帐号锁定"
    }
    var type = {
        0: "PC",
        1: "Android",
        2: "IOS",
    }
    requestUtilParams.findPageData(userViewParams.action.findPageMyLoginlog, cpage, "#table_loginLog", "printPage", findPageMyLoginlog).done(function (res) {
        var list = res.data.list;
        for (var i = 0; i < list.length; i++) {
            var $tr = $("<tr></tr>");
            $tr.append("<td>" + baseUtilParams.simpDataFormat(list[i].login_date) + "</td>");
            $tr.append("<td>" + list[i].user_account + "</td>");
            $tr.append("<td>" + list[i].request_ip + "</td>");
            $tr.append("<td>" + state[list[i].state] + "</td>");
            $tr.append("<td>" + type[list[i].type] + "</td>");
            $tr.append("<td>" + list[i].model_number + "</td>");
            $tr.append("<td>" + baseUtilParams.strNullFilter(list[i].imei) + "</td>");
            $("#table_loginLog tbody").append($tr);
        }
    })
}