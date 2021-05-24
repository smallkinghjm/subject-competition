<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <#import "common.ftl" as com>
    <@com.depend/>
    <@com.style/>
    <script>
        layui.use('form', function(){
            var form = layui.form;
            form.verify({
                checkPassword:function(value) {
                    if ($('input[name=newPassword]').val() !== value)
                        return '两次密码输入不一致！';
                },
            });
            form.on('submit(*)', function(data){//监听要放在use里
                $.ajax({
                    type:"POST",
                    contentType:"application/x-www-form-urlencoded",
                    url:"/user/changePassword-page",
                    data:{
                        "password":$("#password").val(),
                        "newPassword":$("#newPassword").val(),
                    },
                    success:function (data) {
                        //alert(JSON.stringify(data))
                        if (data.status=="success"){
                            alert("修改密码成功，您将重新登录。");
                            window.location.href="http://localhost:8080/user/login";
                        }else if (data.status=="20003"){
                            alert("错误，用户登录状态异常");
                            //alert(JSON.stringify(data))

                        }else if (data.status=="20002"){
                            alert("密码输入错误");
                        }else {
                            alert(data.data+"错误，错误码："+data.status);
                        }
                    },
                    error:function (data) {
                        layer.msg('系统错误，状态码：'+data.status, {icon: 5});
                    }
                });
            })
        });
    </script>
    <style>
        #box{
            width: 500px;
            height: 300px;
            background-color: #ffffff;
            position: absolute;
            top:50%;
            left:50%;
            margin-top:0px;
            margin-left:-250px;
        }
        #from{
            width: 450px;
            height: 300px;
            position: absolute;
            top:50%;
            left:50%;
            margin-top: -100px;
            margin-left: -250px;
        }
    </style>
</head>
<body >
<div>
    <@com.nav/>
    <div class="layui-container">
            <span class="layui-breadcrumb" lay-separator=">>">
            <p style="margin-top: 10px;">当前位置：
                <a href="<@com.path/>/home">首页</a>
                <a href="">个人中心</a>
                <a href="<@com.path/>/user/information">个人信息</a>
                <a><cite>修改密码</cite></a>
            </p>
        </span>
    </div>
</div>
<div class="layui-container">
    <div id="box" >
    <form class="layui-form"  method="post" id="from">
                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="password" id="password" lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">新密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="newPassword" id="newPassword" lay-verify="required|newPassword" placeholder="请输入密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">确认密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="checkPassword" id="checkPassword" lay-verify="required|checkPassword" placeholder="请再次输入密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block" style="padding-left: 90px;">
                        <input type="button" class="layui-btn layui-btn layui-btn-normal" id="btn" value="立即修改" lay-submit lay-filter="*">
                    </div>
                </div>
            </form>
    </div>
</div>


</body>
</html>