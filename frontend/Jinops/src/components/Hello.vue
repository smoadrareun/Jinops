<template>
  <div class="item">
    <h1>This is the Hello.vue in templates</h1>
    <span th:text="${key}"></span><br>
    <span id="datetime" th:text="${value}"></span><br>
    <a href="javascript:goto();function goto(){window.location.href=window.location.origin+'/vendor/index'}" style="color: #FFFFFF">
      <input type="button" value="后台管理页面"/></a><br>
    <a href="javascript:goto();function goto(){window.location.href=window.location.origin+'/client/index'}" style="color: #FFFFFF">
      <input type="button" value="前台商城页面"/></a><br>
    <div style="display: none" th:style="${value!=null}? 'display:inline':''">
      <span>无聊？来点验证码测试吧：</span><br>
      <img class="image" th:src="${verImage}" onclick="window.location.reload();" alt="点击查看验证码" title="点击刷新"/>
      <span id="show"></span><br>
      <input id="code" oninput="myFunction()" autofocus/>
      <input id="test" th:value="${verCode}" type="hidden"/><br>
    </div>
  </div>
  <div class="beian">
    <a target="_blank" href="https://www.beian.gov.cn/portal/registerSystemInfo?recordcode=43012402000712" style="display:inline-block;text-decoration:none;height:20px;line-height:20px;" one-link-mark="yes">
      <img src="https://www.beian.gov.cn/img/ghs.png" style="float:left;"/>
      <p style="float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px; color:#CCC;font-size: 14px;">湘公网安备 43012402000712号</p>
    </a>
    <a href="https://beian.miit.gov.cn/#/Integrated/recordQuery" target="_blank" one-link-mark="yes" style="display:inline-block;text-decoration:none;height:20px;line-height:20px;">
      <p style="float:left;height:20px;line-height:20px;margin: 0px 0px 0px 5px; color:#CCC;font-size: 14px;">湘ICP备2022002105号-1</p>
    </a>
  </div>
</template>

<script>
import md5 from "../js/md5";

export default {
  name: "Hello"
}

function myFunction() {
  var x = md5(md5(document.getElementById("code").value.toLowerCase()));
  var y = document.getElementById("test").value;
  if (x === y) {
    document.getElementById("show").innerText = "👌";
  } else if (x === "") {
    document.getElementById("show").innerText = "";
  } else {
    document.getElementById("show").innerText = "😅";
  }
}

Date.prototype.format = function (fmt) {
  var o = {
    "y+": this.getFullYear, //年
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "h+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds() //秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}
setInterval("document.getElementById('datetime').innerHTML=(new Date()).format('yyyy-MM-dd hh:mm:ss');", 1000);
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body,
.item::before {
  background: url('https://cdn.wallpaperhub.app/cloudcache/9/a/a/e/2/2/9aae2279af7ca2de3b99643f57d11b58030e9b90.jpg') no-repeat center fixed;
  background-size: cover;
}

body {
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
}

.item {
  position: relative;
  width: 100%;
  height: auto;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 0 5px 1px #FFF;
  color: #FFF;
  font-size: 15px;
  font-weight: lighter;
  line-height: 35px;
  overflow: hidden;
}

.item::before {
  content: '';
  display: block;
  position: absolute;
  z-index: -1;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  margin: -20px;
  filter: blur(16px);
}

.image {
  width: 135px;
  height: 45px;
  border: 1px solid black;
  border-radius: 7px;
  box-shadow: 1px 1px 0 0 #000;
  backdrop-filter: blur(5px);
}

.beian {
  position: relative;
  bottom: 0;
  width: 100%;
  text-align: center;
  margin: 10px;
}

h2 {
  text-align: center;
  color: #FFF;
}

p {
  font-size: 18px;
  color: #FFF;
  text-decoration: underline;
}

input {
  width: 165px;
  font-size: 17px;
  border-radius: 5px;
  background-color: transparent;
  color: #FFF;
  box-shadow: 1px 1px 0 0 #000;
}

span {
  font-weight: 400;
}
</style>