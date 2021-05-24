<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>赛果</title>
    <#import "../common.ftl" as com>
    <@com.depend/>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body>

<form role="form-group" style="display: inherit;width: 460px;margin-top: 20px;margin-left: 20px" id="tb">
    帐号: <input type="text"
               placeholder="请输入帐号">
    密码: <input type="password"
               placeholder="请输入密码">
    <input type="button" onclick="add()" value="添加">
</form>
<div class="btn btn-default" style="margin-top: 20px;margin-left: 200px">提交</div>
</body>
</html>

<script>
    function add() {
        var form = document.createElement("form-group");
        form.id = new Date().getTime();
        form.innerHTML = "<form role='form-group' style='display: inherit;width: 550px'>" +
            "帐号: " + "<input  placeholder='请输入帐号'/>" +
            " 密码: <input  placeholder='请输入密码'/>" +
            "<input type='button' onclick='del(this)' value='删除'></form>"
        document.getElementById("tb").appendChild(form);
    }

    function del(obj) {
        var formID = obj.parentNode.parentNode.id;
        var form = document.getElementById(formID);
        document.getElementById("tb").removeChild(form);
    }
</script>