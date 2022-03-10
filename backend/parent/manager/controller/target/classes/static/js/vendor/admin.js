function addScript(url){
    document.write("<script src='../md5.js'></script>");
}

$(function () {
    var venToken=sessionStorage.getItem("venToken");
    if(venToken===null||venToken===undefined){
        venToken=localStorage.getItem("venToken");
    }
    var adata = {
        "venToken": venToken,
        "admin": true,
    };
    var data = JSON.stringify(adata);
    $.ajax({
        type: "POST",
        dataType: "json",//通过GET方式上传请求
        contentType: "application/json",//上传内容格式为json结构
        data: data,                             //上传的参数
        async: false,
        url: window.location.origin + "/Jinops/vendor/login",     //请求的url。与后端@Request Mapping注解中的值一致。
        success: function (data) {          //请求成功的回调函数
            if (data.code === 0) {
                window.location.href=window.location.origin+"/vendor/index.html";
            }
        },
        error: function (e) {           //请求失败的回调函数
            console.log(e);
        }
    });

    getValCode();

    $("input[name='keys']").val("123456");
})

var success="<div class=\"form-group animated bounce\">\n" +
    "                    <div class=\"alert alert-green\">\n" +
    "                        <span class=\"close rotate-hover\"></span><strong>恭喜：</strong>操作成功。\n" +
    "                    </div>\n" +
    "                </div>";

var failure="<div class=\"form-group animated bounce\">\n" +
    "                                            <div class=\"alert alert-yellow\">\n" +
    "                                                <span class=\"close rotate-hover\"></span><strong>操作失败</strong>\n" +
    "                                                <p>\n" +
    "                                                    请重新操作。\n" +
    "                                                </p>\n" +
    "                                            </div>";

var error="<div class=\"form-group animated bounce\">\n" +
    "                                            <div class=\"alert alert-red\">\n" +
    "                                                <span class=\"close rotate-hover\"></span><strong>访问异常</strong>\n" +
    "                                                <p>\n" +
    "                                                    请稍后重试。\n" +
    "                                                </p>\n" +
    "                                            </div>";

function getWarning(str){
    return "<div class=\"form-group animated bounce\">\n"+
        "                                            <div class=\"alert alert-yellow\">\n" +
        "                                                <span class=\"close rotate-hover\"></span><strong>注意：</strong>"+str+"\n" +
        "                                            </div>\n" +
        "                                        </div>";
}

function getTips(str){
    return "<div class=\"form-group animated bounce\">\n"+
        "                                            <div class=\"alert alert-blue\">\n" +
        "                                                <span class=\"close rotate-hover\"></span><strong>提示：</strong>"+str+"\n" +
        "                                            </div>\n" +
        "                                        </div>";
}

function getValCode() {
    $.ajax({
        url: window.location.origin + "/Jinops/utils/getVerCode/4",//请求地址
        dataType: "json",//数据格式
        type: "post",//请求方式
        async: false,//是否异步请求
        success: function (result) { //如果发送成功
            var verImage = result.data.verImage;
            var verCode = result.data.verCode;
            $("#image").attr("src", verImage);
            sessionStorage.setItem("verCode", verCode);
        },
    })
}

function login() {
    var code = md5(md5($("input[name='code']").val().toLowerCase()));
    var verCode = sessionStorage.getItem("verCode");
    var uname = $("input[name='username']").val();
    var passwd = $("input[name='password']").val();
    var keys = $("input[name='keys']").val();
    if (code !== verCode) {
        $("#code").val("").focus();
        window.scrollTo('0','0');
        $("#tips").html(getWarning("验证码输入错误")).on("click",".close",function(){
            $(this).parent().parent().remove();
        });
        getValCode();
    } else {
        if($("#forget").text()==="正常登录"){
            if(keys!=="smoadrareun"){
                $("#tips").html(getWarning("口令输入错误")).on("click",".close",function(){
                    $(this).parent().parent().remove();
                });
            }else{
                var adata = {
                    "uname": uname,
                    "admin": true,
                };
                var data = JSON.stringify(adata);
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    data: data,
                    async: false,
                    url: window.location.origin + "/Jinops/vendor/find",
                    success: function (data) {
                        if (data.code === 0) {
                            var adata = {
                                "id": data.data[0].id,
                                "passwd": "123456",
                            };
                            var data = JSON.stringify(adata);
                            $.ajax({
                                type: "POST",
                                dataType: "json",
                                contentType: "application/json",
                                data: data,
                                async: false,
                                url: window.location.origin + "/Jinops/vendor/update",
                                success: function (data) {
                                    if (data.code === 0) {
                                        var adata = {
                                            "uname": uname,
                                            "passwd": "123456",
                                            "admin": true,
                                        };
                                        var data = JSON.stringify(adata);
                                        $.ajax({
                                            type: "POST",
                                            dataType: "json",
                                            contentType: "application/json",
                                            data: data,
                                            async: false,
                                            url: window.location.origin + "/Jinops/vendor/login",
                                            success: function (data) {
                                                if (data.code === 0) {
                                                    if($("input[name='remember']").is(":checked")){
                                                        localStorage.setItem("venToken", data.data.venToken);
                                                    }
                                                    sessionStorage.setItem("venToken", data.data.venToken);
                                                    window.location.href = window.location.origin + "/vendor/index.html";
                                                }
                                                if (data.code === -403) {
                                                    $("input[name='code']").val("").focus();
                                                    $("input[name='username']").select();
                                                    window.scrollTo('0','0');
                                                    $("#tips").html(getWarning("用户名或密码错误")).on("click",".close",function(){
                                                        $(this).parent().parent().remove();
                                                    });
                                                    getValCode();
                                                }
                                                if (data.code === -1) {
                                                    window.scrollTo('0','0');
                                                    $("#tips").html(failure).on("click",".close",function(){
                                                        $(this).parent().parent().remove();
                                                    });
                                                }
                                            },
                                            error: function (e) {
                                                console.log(e);
                                                window.scrollTo('0','0');
                                                $("#tips").html(error).on("click",".close",function(){
                                                    $(this).parent().parent().remove();
                                                });
                                            }
                                        });
                                    }
                                    if (data.code === -404) {
                                        $("input[name='code']").val("").focus();
                                        $("input[name='username']").select();
                                        window.scrollTo('0','0');
                                        $("#tips").html(getWarning("该用户名不存在")).on("click",".close",function(){
                                            $(this).parent().parent().remove();
                                        });
                                        getValCode();
                                    }
                                    if (data.code === -1) {
                                        window.scrollTo('0','0');
                                        $("#tips").html(failure).on("click",".close",function(){
                                            $(this).parent().parent().remove();
                                        });
                                    }
                                },
                                error: function (e) {
                                    console.log(e);
                                    window.scrollTo('0','0');
                                    $("#tips").html(error).on("click",".close",function(){
                                        $(this).parent().parent().remove();
                                    });
                                }
                            });
                        }
                        if (data.code === -404) {
                            $("input[name='code']").val("").focus();
                            $("input[name='username']").select();
                            window.scrollTo('0','0');
                            $("#tips").html(getWarning("该用户名不存在")).on("click",".close",function(){
                                $(this).parent().parent().remove();
                            });
                            getValCode();
                        }
                        if (data.code === -1) {
                            window.scrollTo('0','0');
                            $("#tips").html(failure).on("click",".close",function(){
                                $(this).parent().parent().remove();
                            });
                        }
                    },
                    error: function (e) {
                        console.log(e);
                        window.scrollTo('0','0');
                        $("#tips").html(error).on("click",".close",function(){
                            $(this).parent().parent().remove();
                        });
                    }
                });
            }
        }else{
            var adata = {
                "uname": uname,
                "passwd": passwd,
                "admin": true,
            };
            var data = JSON.stringify(adata);
            $.ajax({
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                data: data,
                async: false,
                url: window.location.origin + "/Jinops/vendor/login",
                success: function (data) {
                    if (data.code === 0) {
                        if($("input[name='remember']").is(":checked")){
                            localStorage.setItem("venToken", data.data.venToken);
                        }
                        sessionStorage.setItem("venToken", data.data.venToken);
                        window.location.href = window.location.origin + "/vendor/index.html";
                    }
                    if (data.code === -403) {
                        $("input[name='code']").val("").focus();
                        $("input[name='username']").select();
                        window.scrollTo('0','0');
                        $("#tips").html(getWarning("用户名或密码错误")).on("click",".close",function(){
                            $(this).parent().parent().remove();
                        });
                        getValCode();
                    }
                    if (data.code === -1) {
                        window.scrollTo('0','0');
                        $("#tips").html(failure).on("click",".close",function(){
                            $(this).parent().parent().remove();
                        });
                    }
                },
                error: function (e) {
                    console.log(e);
                    window.scrollTo('0','0');
                    $("#tips").html(error).on("click",".close",function(){
                        $(this).parent().parent().remove();
                    });
                }
            });
        }

    }
}

function register(){
    var code=md5(md5($("input[name='code']").val().toLowerCase()));
    var verCode=sessionStorage.getItem("verCode");
    var uname=$("input[name='username']").val();
    var passwd=$("input[name='password']").val();
    if(code!==verCode){
        $("input[name='code']").val("").focus();
        window.scrollTo('0','0');
        $("#tips").html(getWarning("验证码输入错误")).on("click",".close",function(){
            $(this).parent().parent().remove();
        });
        getValCode();
    }else{
        var adata = {
            "uname": uname,
            "passwd": passwd,
            "admin": true,
        };
        var data = JSON.stringify(adata);
        $.ajax({
            type : "POST",
            dataType: "json",//通过GET方式上传请求
            contentType : "application/json",//上传内容格式为json结构
            data : data,                              //上传的参数
            async: false,
            url : window.location.origin+"/Jinops/vendor/insert",     //请求的url。与后端@Request Mapping注解中的值一致。
            success : function(data) {          //请求成功的回调函数
                if (data.code === 0){
                    window.scrollTo('0','0');
                    $("#tips").html(success).on("click",".close",function(){
                        $(this).parent().parent().remove();
                    });
                    window.location.href = window.location.origin+"/vendor/login.html";
                }
                if (data.code === -400){
                    $("input[name='code']").val("").focus();
                    $("input[name='username']").select();
                    window.scrollTo('0','0');
                    $("#tips").html(getWarning("此用户名已存在")).on("click",".close",function(){
                        $(this).parent().parent().remove();
                    });
                    getValCode();
                }
                if (data.code === -1){
                    window.scrollTo('0','0');
                    $("#tips").html(failure).on("click",".close",function(){
                        $(this).parent().parent().remove();
                    });
                }
            },
            error : function(e) {           //请求失败的回调函数
                console.log(e);
                window.scrollTo('0','0');
                $("#tips").html(error).on("click",".close",function(){
                    $(this).parent().parent().remove();
                });
            }
        });
    }
}

function gotoRe(){
    if($("#forget").text()==="忘记密码了？"){
        $("#passwd").addClass("hidden");
        $("#keys").removeClass("hidden");
        $("input[name='password']").val("123456");
        $("input[name='keys']").val("");
        $("#tips").html(getWarning("您正在进行跳过登录验证操作，请向管理员索要口令并输入，届时您的密码将被清空并重置为123456，请在登录成功后及时修改你的密码！")).on("click",".close",function(){
            $(this).parent().parent().remove();
        });
        $("#forget").html("正常登录");
    }else{
        $("input[name='password']").val("");
        $("input[name='keys']").val("123456");
        $("#keys").addClass("hidden");
        $("#passwd").removeClass("hidden");
        $("#tips").empty();
        $("#forget").html("忘记密码了？");
    }

}

function gotoReg(){
    window.location.href=window.location.origin+'/vendor/regis.html'
}

function gotoLog(){
    window.location.href=window.location.origin+'/vendor/login.html'
}