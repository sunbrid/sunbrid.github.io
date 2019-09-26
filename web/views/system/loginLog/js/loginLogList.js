var loginLogListParams = {
    action: {
        findPageData: "loginLog/selectPageLoginLog"}
    //findPageData: "files/data/loginLog/selectPageLoginLog.json"}
    , model: {
        "user_account": {
            "comment": "帐号",
            "type": "varchar",
        }
        , "user_name": {
            "comment": "姓名"
        }
        , "state": {
            "comment": "登录状态",
            "type": "number",
            "item": {
                0:"",
                1: "登录成功",
                2: "帐号不存在",
                3: "密码错误",
                4: "帐号被冻结",
                5: "连续登录失败帐号锁定"
            }
        }, "type": {
            "comment": "登录类型",
            "type": "number",
            "item": {
                0: "PC",
                1: "Android",
                2: "IOS",
            }
        }
        , "request_ip": {
            "comment": "ip地址"
        }, "login_date": {
            "comment": "登录时间",
            "type": "date"
        }
    }
}
$(function () {
    findPageData();
    //高级查询按钮事件
    $("#btnAdvancedQuery").click(function () {
        requestUtilParams.createAdvancedQueryDialog(loginLogListParams.model, "#dialogAdvancedQuery")
    });
    $("#btn_search").click(function () {//搜索
        requestUtilParams.searchForm("#formSearch");
        findPageData()
    })
})

/**
 * @title:<h3> 登录日志分页查询列表 <h3>
 * @author: Enzo
 * @date: 2018-10-18 9:13
 * @params
 * @return
 **/
function findPageData(cpage) {
    requestUtilParams.findPageData(loginLogListParams.action.findPageData, cpage, "#tabelList", "printPage", findPageData).done(function (res) {
        var list = res.data.list;
        for (var i = 0; i < list.length; i++) {
            var $tr = $("<tr></tr>");
            $tr.append("<td>" + list[i].user_account + "</td>")
            $tr.append("<td>" + list[i].request_ip + "</td>")
            $tr.append("<td>" + baseUtilParams.simpDataFormat(list[i].login_date) + "</td>")
            $tr.append("<td>" + loginLogListParams.model.type.item[list[i].type] + "</td>")
            $tr.append("<td>" + loginLogListParams.model.state.item[list[i].state] + "</td>")
            $tr.append("<td>" + baseUtilParams.strNullFilter(list[i].model_number) + "</td>")
            $tr.append("<td>" + baseUtilParams.strNullFilter(list[i].imei) + "</td>")
            $("#tabelList tbody").append($tr);
        }
    });

}