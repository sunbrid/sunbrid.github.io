var operationLogListParams = {
    action: {
        findPageData: "operationLog/selectPageOperationLog"
    },
    model: {

        "user_account": {
            "comment": "帐号",
            "type": "varchar",
        }, "user_name": {
            "comment": "姓名",
            "type": "varchar",
        }
        , "date": {
            "comment": "时间",
            "type": "date",
        }, "request_ip": {
            "comment": "IP",
            "type": "varchar",
        }
        , "module": {
            "comment": "模块",
            "type": "varchar",
        }, "type": {
            "comment": "类型",
            "type": "varchar",
        }, "content": {
            "comment": "内容",
            "type": "varchar",
        }
    }
}
$(function () {
    requestUtilParams.staticJson = false;
    findPageData();
    $("#btn_search").click(function () {//搜索
        requestUtilParams.searchForm("#formSearch");
        findPageData()
    })
    //高级查询按钮事件
    $("#btnAdvancedQuery").click(function () {
        requestUtilParams.createAdvancedQueryDialog(operationLogListParams.model, "#dialogAdvancedQuery")
    });
});

/**
 * @title:<h3> 分页查询操作日志 <h3>
 * @author: Enzo
 * @date: 2018-10-22 11:14
 * @params
 * @return
 **/
function findPageData(cpage) {
    requestUtilParams.findPageData(operationLogListParams.action.findPageData, cpage, "#myTable", "printPage", findPageData).done(function (res) {
        var list = res.data.list;
        var $tbody = $("#myTable tbody");
        for (var i = 0; i < list.length; i++) {
            var $tr = $("<tr></tr>");

            $tr.append("<td>" + list[i].user_account + "</td>");
            $tr.append("<td>" + list[i].user_name + "</td>");
            $tr.append("<td>" + list[i].request_ip + "</td>");
            $tr.append("<td>" + baseUtilParams.simpDataFormat(list[i].date) + "</td>");
            $tr.append("<td>" + list[i].module + "</td>");
            $tr.append("<td>" + list[i].type + "</td>");
            $tr.append("<td>" + list[i].content + "</td>");


            $tbody.append($tr);
        }
    });
}