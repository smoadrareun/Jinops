$(function () {
    var cliToken = sessionStorage.getItem("cliToken");
    if (cliToken === null || cliToken === undefined) {
        cliToken = localStorage.getItem("cliToken");
    }

    var adata = {
        "token": cliToken,
    };
    var data = JSON.stringify(adata);
    $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: data,
        async: false,
        url: window.location.origin + "/Jinops/client/login",
        success: function (data) {
            if (data.code === 0) {
                var str = "<img id=\"img\" src=\"../../static/images/y.jpg\" class=\"radius-circle rotate-hover margin-little-bottom\" height=\"40\" width=\"40\" style=\"object-fit: cover;\" alt=\"\"/>&nbsp;\n" +
                    "                <span class=\"text-default\">\n" +
                    "                    <strong>欢迎</strong>\n" +
                    "                    <span id=\"uname\"></span>\n" +
                    "                    <input name=\"id\" hidden=\"hidden\"/>\n" +
                    "                </span>\n" +
                    "                <a class=\"button button-little bg-red margin-left\" href=\"javascript:exit();\">\n" +
                    "                <span class=\"icon-power-off\">&nbsp;</span> 退出登录</a>";
                $("#top").append(str);
                str = "<li class=\"nav-more padding-little-top\" class=\"bg-gray\"><a href=\"#\">我的订餐<span class=\"arrow\"></span></a>\n" +
                    "                    <ul class=\"drop-menu bg-gray\">\n" +
                    "                        <a href=\"cart.html\" target=\"right\">购物车</a></li>\n" +
                    "                        <a href=\"order.html\" target=\"right\">历史订单</a></li>\n" +
                    "                    </ul>\n" +
                    "                </li>\n" +
                    "                <li class=\"nav-more padding-little-top\" class=\"bg-gray\"><a href=\"#\">用户设置<span class=\"arrow\"></span></a>\n" +
                    "                    <ul class=\"drop-menu bg-gray\">\n" +
                    "                        <li><a href=\"user.html\" target=\"right\">个人信息</a></li>\n" +
                    "                        <li><a href=\"pass.html\" target=\"right\">修改密码</a></li>\n" +
                    "                        <li><a href=\"logout.html\" target=\"right\">注销账号</a></li>\n" +
                    "                    </ul>\n" +
                    "                </li>";
                $("#topnav").append(str);
                $("input[name='id']").val(data.data.id);
                $("#uname").text(data.data.uname);
                var img = data.data.pic;
                if (img !== null && img !== undefined) {
                    $("#img").attr("src", img);
                }
                sessionStorage.setItem("client", JSON.stringify(data.data));
            } else {
                var str = "<a class=\"button button-little bg-green\" href=\"javascript:gotoLog();\">\n" +
                    "                <span class=\"icon-desktop\">&nbsp;</span>登录账号\n" +
                    "            </a> &nbsp;&nbsp;\n" +
                    "            <a href=\"javascript:gotoReg();\" class=\"button button-little bg-yellow\">\n" +
                    "                <span class=\"icon-pencil\">&nbsp;</span>注册账号\n" +
                    "            </a>";
                $("#top").append(str);
            }
        },
        error: function (e) {
            console.log(e);
            var str = "<a class=\"button button-little bg-green\" href=\"javascript:gotoLog();\">\n" +
                "                <span class=\"icon-desktop\">&nbsp;</span>登录账号\n" +
                "            </a> &nbsp;&nbsp;\n" +
                "            <a href=\"javascript:gotoReg();\" class=\"button button-little bg-yellow\">\n" +
                "                <span class=\"icon-pencil\">&nbsp;</span>注册账号\n" +
                "            </a>";
            $("#top").append(str);
        }
    });

    $(".panel-body ul li a").click(function () {
        $("#a_leader_txt").text($(this).text());
        $(".leftnav ul li a").removeClass("on");
        $(this).addClass("on");
    })

    $("#mybtn").click(function () {
        if ($("#leftnav").val() === "block") {
            $("#mybtn").animate({marginLeft: '5px'}, 600);
            $(".admin").animate({left: 0}, 600);
            $("#leftnav").val("none");
        } else {
            $("#mybtn").animate({marginLeft: '20px'}, 600);
            $(".admin").animate({left: '160px'}, 600);
            $("#leftnav").val("block");
        }
    })
});

var success = "<div class=\"form-group animated bounce\">\n" +
    "                    <div class=\"alert alert-green\">\n" +
    "                        <span class=\"close rotate-hover\"></span><strong>恭喜：</strong>操作成功。\n" +
    "                    </div>\n" +
    "                </div>";

var failure = "<div class=\"form-group animated bounce\">\n" +
    "                                            <div class=\"alert alert-yellow\">\n" +
    "                                                <span class=\"close rotate-hover\"></span><strong>操作失败</strong>\n" +
    "                                                <p>\n" +
    "                                                    请重新操作。\n" +
    "                                                </p>\n" +
    "                                            </div>";

var error = "<div class=\"form-group animated bounce\">\n" +
    "                                            <div class=\"alert alert-red\">\n" +
    "                                                <span class=\"close rotate-hover\"></span><strong>访问异常</strong>\n" +
    "                                                <p>\n" +
    "                                                    请稍后重试。\n" +
    "                                                </p>\n" +
    "                                            </div>";

function getWarning(str) {
    return "<div class=\"form-group animated bounce\">\n" +
        "                                            <div class=\"alert alert-yellow\">\n" +
        "                                                <span class=\"close rotate-hover\"></span><strong>注意：</strong>" + str + "\n" +
        "                                            </div>\n" +
        "                                        </div>";
}

function getTips(str) {
    return "<div class=\"form-group animated bounce\">\n" +
        "                                            <div class=\"alert alert-blue\">\n" +
        "                                                <span class=\"close rotate-hover\"></span><strong>提示：</strong>" + str + "\n" +
        "                                            </div>\n" +
        "                                        </div>";
}

function gotoBack() {
    window.location.href = window.location.origin + '/client/index.html';
}

function gotoHome() {
    parent.window.location.href = window.location.origin + '/client/index.html';
}

function exit() {
    sessionStorage.removeItem('cliToken');
    sessionStorage.removeItem('client');
    localStorage.removeItem('cliToken');
    window.location.href = window.location.origin + '/client/index.html';
}

function list() {
    window.location.href = window.location.origin + '/client/home.html';
}

function getParam(param) {
    var reg = new RegExp("(^|&)" + param + "=([^&]*)(&|$)");
    var paramsLi = window.location.search.substr(1).match(reg);
    if (paramsLi != null && paramsLi != "" && paramsLi != undefined) {
        return unescape(paramsLi[2]);
    }
}

function setImagePreview() {
    var docObj = $("input[name='pic']")[0];
    var size = Math.round(docObj.files[0].size / 1024 / 1024);
    if (size > 10) {
        window.scrollTo('0', '0');
        $("#tips").html(getWarning("图片的大小不能超过10MB")).on("click", ".close", function () {
            $(this).parent().parent().remove();
        });
        docObj.value = "";
    } else {
        var formData = new FormData();
        formData.set("name","jinops");
        formData.append("files",docObj.files[0]);
        $.ajax({
            type: "POST",
            dataType: "json",
            processData : false,
            contentType : false,
            data: formData,
            async: false,
            url: window.location.origin + "/Jinops/files/upload",
            success: function (data) {
                if (data.code === 0) {
                    window.scrollTo('0', '0');
                    $("#tips").html(success).on("click", ".close", function () {
                        $(this).parent().parent().remove();
                    });
                    $("#preview").attr("src", data.data[0]);
                }
                if (data.code === -1) {
                    window.scrollTo('0', '0');
                    $("#tips").html(failure).on("click", ".close", function () {
                        $(this).parent().parent().remove();
                    });
                    docObj.value = "";
                }
            },
            error: function (e) {
                console.log(e);
                window.scrollTo('0', '0');
                $("#tips").html(error).on("click", ".close", function () {
                    $(this).parent().parent().remove();
                });
                docObj.value = "";
            }
        });
    }
    // var docObj = $("input[name='pic']")[0];
    // var imgObjPreview = $("#preview")[0];
    // var size = Math.round(docObj.files[0].size / 1024 / 1024);
    // if (size > 3) {
    //     window.scrollTo('0', '0');
    //     $("#tips").html(getWarning("图片的大小不能超过3MB")).on("click", ".close", function () {
    //         $(this).parent().parent().remove();
    //     });
    //     docObj.value = "";
    //     imgObjPreview.src = "";
    //     return;
    // }
    // if (docObj.files && docObj.files[0]) {
    //     var reader = new FileReader();
    //     reader.readAsDataURL(docObj.files[0]);
    //     reader.onload = function () {
    //         $("#preview").attr("src", this.result)
    //             .load(function () {
    //                 var picWidth = this.width;
    //                 var picHeight = this.height;
    //                 if (picWidth > 1024 || picHeight > 1024) {
    //                     window.scrollTo('0', '0');
    //                     $("#tips").html(getWarning("图片的尺寸不能超过1024x1024")).on("click", ".close", function () {
    //                         $(this).parent().parent().remove();
    //                     });
    //                     docObj.value = "";
    //                     imgObjPreview.src = "";
    //                 }
    //             });
    //     }
    // }
    // var picWidth, picHeight;

}

function userInfo() {

    var adata = {
        "id": JSON.parse(sessionStorage.getItem("client")).id,
        "uname": JSON.parse(sessionStorage.getItem("client")).uname,
        "pic": $("#preview").attr("src"),
    };
    var data = JSON.stringify(adata);
    $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: data,
        async: false,
        url: window.location.origin + "/Jinops/client/update",
        success: function (data) {
            if (data.code === 0) {
                window.scrollTo('0', '0');
                $("#tips").html(success).on("click", ".close", function () {
                    $(this).parent().parent().remove();
                });
                parent.window.location.reload();
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
    var adata = {
        "cliId": JSON.parse(sessionStorage.getItem("client")).id,
        "name": $("input[name='name']").val(),
        "phone": $("input[name='phone']").val(),
        "address": $("input[name='address']").val(),
    };
    var data = JSON.stringify(adata);
    $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: data,
        async: false,
        url: window.location.origin + "/Jinops/client/insertAddrInfo",
        success: function (data) {
            if (data.code === 0) {
                window.scrollTo('0', '0');
                $("#tips").html(success).on("click", ".close", function () {
                    $(this).parent().parent().remove();
                });
                setTimeout(function () {
                    parent.window.location.reload();
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

function pass() {
    var adata = {
        "uname": JSON.parse(sessionStorage.getItem("client")).uname,
        "passwd": $("input[name='oldPwd']").val(),
    };
    var data = JSON.stringify(adata);
    $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: data,
        async: false,
        url: window.location.origin + "/Jinops/client/login",
        success: function (data) {
            if (data.code === 0) {
                var adata = {
                    "id": JSON.parse(sessionStorage.getItem("client")).id,
                    "passwd": $("input[name='newPwd']").val(),
                };
                var data = JSON.stringify(adata);
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json",
                    data: data,
                    async: false,
                    url: window.location.origin + "/Jinops/client/update",
                    success: function (data) {
                        if (data.code === 0) {
                            sessionStorage.removeItem("cliToken");
                            sessionStorage.removeItem('client');
                            localStorage.removeItem("cliToken");
                            parent.window.location.href = window.location.origin + '/client/login.html'
                            alert("修改成功，请重新登陆");
                        }
                        if (data.code === -404) {
                            $("input[name='oldPwd']").select();
                            window.scrollTo('0', '0');
                            $("#tips").html(getWarning("修改的客户不存在")).on("click", ".close", function () {
                                $(this).parent().parent().remove();
                            });
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
            if (data.code === -403) {
                $("input[name='oldPwd']").select();
                window.scrollTo('0', '0');
                $("#tips").html(getWarning("原密码输入错误")).on("click", ".close", function () {
                    $(this).parent().parent().remove();
                });
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

function logout() {
    if (confirm("您确认要注销此账号吗？一旦注销不可恢复！")) {
        var adata = {
            "uname": JSON.parse(sessionStorage.getItem("client")).uname,
            "passwd": $("input[name='password']").val(),
        };
        var data = JSON.stringify(adata);
        $.ajax({
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: data,
            async: false,
            url: window.location.origin + "/Jinops/client/login",
            success: function (data) {
                if (data.code === 0) {
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        contentType: "application/json",
                        async: false,
                        url: window.location.origin + "/Jinops/client/delete/" + JSON.parse(sessionStorage.getItem("client")).id,
                        success: function (data) {
                            if (data.code === 0) {
                                sessionStorage.removeItem("cliToken");
                                sessionStorage.removeItem('client');
                                localStorage.removeItem("cliToken");
                                parent.window.location.href = window.location.origin + '/client/login.html'
                                alert("注销成功，将退出登陆");
                            }
                            if (data.code === -404) {
                                $("input[name='oldPwd']").select();
                                window.scrollTo('0', '0');
                                $("#tips").html(getWarning("注销的客户不存在")).on("click", ".close", function () {
                                    $(this).parent().parent().remove();
                                });
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
                if (data.code === -403) {
                    $("input[name='oldPwd']").select();
                    window.scrollTo('0', '0');
                    $("#tips").html(getWarning("密码输入错误")).on("click", ".close", function () {
                        $(this).parent().parent().remove();
                    });
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


function gotoReg() {
    window.location.href = window.location.origin + '/client/regis.html';
}

function gotoLog() {
    window.location.href = window.location.origin + '/client/login.html';
}
