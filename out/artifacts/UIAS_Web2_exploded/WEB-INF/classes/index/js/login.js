var loginParams = {
    action: {login: "index/login"}
}
$(function () {
    if(parent.$("#btnLoginOut").length>0){//如果是在框架页面加载的登录页面
        parent.$("#btnLoginOut").click();//注销
    }
    requestUtilParams.staticJson=false;
    $("#btnSubmit").buttonCaptcha({// #submit请修改为你评论提交按钮的ID
        codeWord: 'u',// 码字必须装配件
        codeZone: 'nlock',// 如果你愿意,你可以添加一个域区码字(长度必须从2到4);
        captchaHeader: '请将字符移动到框内',// / /问题上面的验证码;
        captchaUnlocked: '通过验证'// 文本时验证码解锁的头;
    });
    $(".captcha_gbws_wrap").appendTo("#checkGroup");
    $("#btnSubmit").click(function () {
        login();
    });
    getCookie();
});

/**
 * @title:<h3> 获得cooke中的信息，是否记住密码 <h3>
 * @author: Enzo
 * @date: 2018-10-29 11:19
 * @params
 * @return
 **/
function getCookie() {
    layui.use('form', function () {
        var form = layui.form;
        console.log($("#is_save").next(".layui-form-switch").length);
        if ($.cookie("is_save")=="true") {
            $("#is_save").next(".layui-form-switch").click();
            $("#user_account-submit").val($.cookie("user_account"));
            $("#user_password-submit").val($.cookie("user_password"));
        }

    });


}

/**
 * @title:<h3> 登录 <h3>
 * @author: Enzo
 * @date: 2018-10-19 9:14
 * @params
 * @return
 **/
function login() {
    if (checkData()) {//如果验证通过，则进行登录请求
        var pass = rasEncrypt($("#user_password-submit").val());
        $("#user_password-submit_hidden").val(pass);
        var data = $("#formSubmit").serialize();
        requestUtilParams.xhr(loginParams.action.login, data).done(function (res) {
            if (res.resultCode > 0) {
                //登录失败
                alert(res.message);
            } else {
                if ($("#is_save").prop("checked")) {
                    $.cookie("user_account", $("#user_account-submit").val());
                    $.cookie("user_password", $("#user_password-submit").val());
                    $.cookie("is_save", true);
                } else {
                    $.cookie("is_save", false);
                }
                window.location.href = "views/index.html";
            }
        });
    }
}

/**
 * @title:<h3> 验证提交 <h3>
 * @author: Enzo
 * @date: 2018-10-19 9:18
 * @params
 * @return
 **/
function checkData() {
    var str = "";
    var user_account = $("#user_account-submit").val();
    var user_password = $("#user_password-submit").val();
    if (user_account == "") {//帐号为空
        str += "用户名不能为空\n";
    }
    if (user_password == "") {
        str += "密码不能为空\n";
    }
    if (str.length > 0) {//如果消息不为空，则说验证不通过
        alert(str);
        return false;
    }
    return true;


}

/**
 * @title:<h3> 加密密码 <h3>
 * @author: Enzo
 * @date: 2018-10-19 9:42
 * @params
 * @return
 **/
function rasEncrypt(str) {
    var encrypedPwd = "";
    $.ajax({
        url: requestUtilParams.host + "rsa/generateRSAJsKey"
        , async: false
        , success: function (result) {
            if (result != null) { //加密模
                var Modulus = result.split(';')[0];//公钥指数
                var public_exponent = result.split(';')[1];
                //通过模和公钥参数获取公钥
                var key = new RSAUtils.getKeyPair(public_exponent, "", Modulus); //颠倒密码的顺序，要不然后解密后会发现密码顺序是反的
                var reversedPwd = str.split("").reverse().join("");//对密码进行加密传输
                encrypedPwd = RSAUtils.encryptedString(key, reversedPwd);
            }
        }, error: function () {
            alert("请求异常");
        }
    });
    return encrypedPwd;
}
