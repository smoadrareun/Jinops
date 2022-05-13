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
        dataType: "json",
        contentType: "application/json",
        data: data,
        async: false,
        url: window.location.origin + "/Jinops/vendor/login",
        success: function (data) {
            if (data.code !== 0) {
                window.location.href=window.location.origin+"/vendor/login.html";
            }else{
                $("input[name='id']").val(data.data.vendor.id);
                $("#uname").text(data.data.vendor.uname);
                var img=data.data.vendor.pic;
                if(img!==null&&img!==undefined){
                    $("#img").attr("src",img);
                }
                sessionStorage.setItem("vendor",JSON.stringify(data.data.vendor));
            }
        },
        error: function (e) {
            console.log(e);
            window.location.href=window.location.origin+"/vendor/login.html";
        }
    });

    $(".panel-body ul li a").click(function () {
        $("#a_leader_txt").text($(this).text()).attr("href",$(this).attr("href"));
        $("#a_leader_kind").text($(this).parent().parent().parent().prev().children().eq(0).text()).attr("href",$(this).attr("href"));
        $(".leftnav ul li a").removeClass("on");
        $(this).addClass("on");
    })

    $("#mybtn").click(function () {
        if ($("#leftnav").val() === "block") {
            $("#mybtn").animate({marginLeft:'5px'},600);
            $(".admin").animate({left:0},600);
            $("#leftnav").val("none");
        } else {
            $("#mybtn").animate({marginLeft:'20px'},600);
            $(".admin").animate({left:'160px'},600);
            $("#leftnav").val("block");
        }
    })
});

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

function gotoBack(){
    window.location.href=window.location.origin+'/client/index.html';
}

function gotoHome(){
    parent.window.location.href=window.location.origin+'/vendor/index.html';
}

function exit(){
    sessionStorage.removeItem('venToken');
    sessionStorage.removeItem('vendor');
    localStorage.removeItem('venToken');
    window.location.href=window.location.origin+'/vendor/login.html';
}

function list(){
    window.location.href=window.location.origin+'/vendor/list.html';
}

function getParam(param){
    var reg = new RegExp("(^|&)"+param+"=([^&]*)(&|$)");
    var paramsLi = window.location.search.substr(1).match(reg);
    if (paramsLi != null && paramsLi !="" && paramsLi!=undefined) {
        return unescape(paramsLi[2]);
    }
}

function setImagePreview() {
    var docObj = $("input[name='pic']")[0];
    var imgObjPreview = $("#preview")[0];
    var size = Math.round(docObj.files[0].size / 1024 / 1024);
    if (size > 3) {
        window.scrollTo('0','0');
        $("#tips").html(getWarning("图片的大小不能超过3MB")).on("click",".close",function(){
            $(this).parent().parent().remove();
        });
        docObj.value="";
        imgObjPreview.src="";
        return;
    }
    if (docObj.files && docObj.files[0]) {
        var reader = new FileReader();
        reader.readAsDataURL(docObj.files[0]);
        reader.onload = function () {
            $("#preview").attr("src",this.result)
                .load(function() {
                    var picWidth = this.width;
                    var picHeight = this.height;
                    if(picWidth>1024||picHeight>1024){
                        window.scrollTo('0','0');
                        $("#tips").html(getWarning("图片的尺寸不能超过1024x1024")).on("click",".close",function(){
                            $(this).parent().parent().remove();
                        });
                        docObj.value="";
                        imgObjPreview.src="";
                    }
                });
        }
    }
    var picWidth, picHeight;

}

function userInfo(){
    var pic=$("#preview").attr("src");
    if(pic===""||pic===null||pic===undefined){
        $("#tips").html(getTips("图片加载中，请稍等")).on("click",".close",function(){
            $(this).parent().parent().remove();
        });
    }else{
        var adata = {
            "id": JSON.parse(sessionStorage.getItem("vendor")).id,
            "pic": pic,
            // "name": $("input[name='name']").val(),
            // "phone": $("input[name='phone']").val(),
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
                    window.scrollTo('0','0');
                    $("#tips").html(success).on("click",".close",function(){
                        $(this).parent().parent().remove();
                    });
                    parent.window.location.reload();
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

function pass(){
    var adata = {
        "uname": JSON.parse(sessionStorage.getItem("vendor")).uname,
        "passwd": $("input[name='oldPwd']").val(),
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
                var adata = {
                    "id": JSON.parse(sessionStorage.getItem("vendor")).id,
                    "passwd": $("input[name='newPwd']").val(),
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
                            sessionStorage.removeItem("venToken");
                            sessionStorage.removeItem('vendor');
                            localStorage.removeItem("venToken");
                            parent.window.location.href=window.location.origin+'/vendor/login.html'
                            alert("修改成功，请重新登陆");
                        }
                        if (data.code === -404) {
                            $("input[name='oldPwd']").select();
                            window.scrollTo('0','0');
                            $("#tips").html(getWarning("修改的商户不存在")).on("click",".close",function(){
                                $(this).parent().parent().remove();
                            });
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
            if (data.code === -403) {
                $("input[name='oldPwd']").select();
                window.scrollTo('0','0');
                $("#tips").html(getWarning("原密码输入错误")).on("click",".close",function(){
                    $(this).parent().parent().remove();
                });
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

function logout(){
    if(confirm("您确认要注销此账号吗？一旦注销不可恢复！")){
        var adata = {
            "uname": JSON.parse(sessionStorage.getItem("vendor")).uname,
            "passwd": $("input[name='password']").val(),
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
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        contentType: "application/json",
                        async: false,
                        url: window.location.origin + "/Jinops/vendor/delete/"+JSON.parse(sessionStorage.getItem("vendor")).id,
                        success: function (data) {
                            if (data.code === 0) {
                                sessionStorage.removeItem("venToken");
                                sessionStorage.removeItem('vendor');
                                localStorage.removeItem("venToken");
                                parent.window.location.href=window.location.origin+'/vendor/login.html'
                                alert("注销成功，将退出登陆");
                            }
                            if (data.code === -404) {
                                $("input[name='oldPwd']").select();
                                window.scrollTo('0','0');
                                $("#tips").html(getWarning("注销的商户不存在")).on("click",".close",function(){
                                    $(this).parent().parent().remove();
                                });
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
                if (data.code === -403) {
                    $("input[name='oldPwd']").select();
                    window.scrollTo('0','0');
                    $("#tips").html(getWarning("密码输入错误")).on("click",".close",function(){
                        $(this).parent().parent().remove();
                    });
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

function add(){
    var pic=$("#preview").attr("src");
    if(pic===""||pic===null||pic===undefined){
        $("#tips").html(getWarning("请选择图片上传")).on("click",".close",function(){
            $(this).parent().parent().remove();
        });
    }else {
        var adata = {
            "name": $("input[name='name']").val(),
            "pic": pic,
            "price": $("input[name='price']").val(),
            "num": $("input[name='num']").val(),
            "info": $("#info").val(),
            "off": $("input[name='off']:checked").val(),
        };
        var data = JSON.stringify(adata);
        $.ajax({
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: data,
            async: false,
            url: window.location.origin + "/Jinops/goods/insert",
            success: function (data) {
                if (data.code === 0) {
                    window.scrollTo('0', '0');
                    $("#tips").html(success).on("click", ".close", function () {
                        $(this).parent().parent().remove();
                    });
                    $('#reset').trigger("click");
                    $("#preview").attr("src", "");
                }
                if (data.code === -1) {
                    window.scrollTo('0', '0');
                    $("#tips").html(failure).on("click", ".close", function () {
                        $(this).parent().parent().remove();
                    });
                }
            },
            error: function (e) {
                console.log(e);
                window.scrollTo('0', '0');
                $("#tips").html(error).on("click", ".close", function () {
                    $(this).parent().parent().remove();
                });
            }
        });
    }
}

function edit(){var pic=$("#preview").attr("src");
    var pic=$("#preview").attr("src");
    if(pic===""||pic===null||pic===undefined){
        $("#tips").html(getTips("图片加载中，请稍等")).on("click",".close",function(){
            $(this).parent().parent().remove();
        });
    }else {
        var adata = {
            "id": Number($("input[name='id']").val()),
            "name": $("input[name='name']").val(),
            "pic": pic,
            "price": $("input[name='price']").val(),
            "num": $("input[name='num']").val(),
            "info": $("#info").val(),
            "off": $("input[name='off']:checked").val(),
        };
        var data = JSON.stringify(adata);
        $.ajax({
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: data,
            async: false,
            url: window.location.origin + "/Jinops/goods/update",
            success: function (data) {
                if (data.code === 0) {
                    window.scrollTo('0', '0');
                    $("#tips").html(success).on("click", ".close", function () {
                        $(this).parent().parent().remove();
                    });
                }
                if (data.code === -404) {
                    window.scrollTo('0', '0');
                    $("#tips").html(getWarning("修改的商品不存在")).on("click", ".close", function () {
                        $(this).parent().parent().remove();
                    });
                    setTimeout(function () {
                        window.location.reload();
                    }, 3000);
                }
                if (data.code === -1) {
                    window.scrollTo('0', '0');
                    $("#tips").html(failure).on("click", ".close", function () {
                        $(this).parent().parent().remove();
                    });
                }
            },
            error: function (e) {
                console.log(e);
                window.scrollTo('0', '0');
                $("#tips").html(error).on("click", ".close", function () {
                    $(this).parent().parent().remove();
                });
            }
        });
    }
}
