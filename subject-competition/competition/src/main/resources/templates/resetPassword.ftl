<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>重置密码</title>
    <#import "common.ftl" as com>
    <@com.depend/>

    <script>
        layui.use('form', function(){
            var form = layui.form;
            form.verify({
                email: [/^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$|^1[3|4|5|7|8]\d{9}$/, '邮箱格式不对'],
                password: [/^[\S]{6,20}$/,'密码必须6到20位，且不能出现空格'],
                checkPassword:function(value) {
                    if ($('input[name=newPassword]').val() !== value)
                        return '两次密码输入不一致！';
                },
            });
            form.on('submit(*)', function(data){
                $.ajax({
                    type:"POST",
                    contentType:"application/x-www-form-urlencoded",
                    url:"/password/resetPassword-page",
                    data:{
                        "newPassword":$("#newPassword").val(),
                        "mailCode":$("#mailCode").val()
                    },
                    success:function (data) {
                        if (data.status=="success"){
                            alert('重置成功');
                            window.location.href="http://localhost:8080/user/login";
                        } else {
                            alert(data.data);
                            console.log('1错误码：'+data.status+',错误信息：'+data.data);
                        }
                    },
                    error:function (data) {
                        alert(data.data);
                        console.log('2错误码：'+data.status+',错误信息：'+data.data);
                    }
                });
            })
        });

    </script>
    <style>
        #box{
            width: 500px;
            height: 280px;
            background-color: #ffffff;
            position: absolute;
            top:50%;
            left:50%;
            margin-top:-170px;
            margin-left:-250px;
        }
        #from{
            width: 400px;
            height: 400px;
            position: absolute;
            top:50%;
            left:50%;
            margin-top: -100px;
            margin-left: -220px;
        }
    </style>
</head>
<body>
<div id="particles-js">
    <div id="box">
        <div class="grid-demo grid-demo-bg1">
            <form class="layui-form"  method="post" id="from">
                <div class="layui-form-item">
                    <label class="layui-form-label">新密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="newPassword" id="newPassword" lay-verify="required|password" placeholder="请输入密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">确认密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="checkPassword" id="checkPassword" lay-verify="required|checkPassword" placeholder="请再次输入密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">验证码</label>
                    <div class="layui-input-block">
                        <input type="text" name="mailCode" id="mailCode" lay-verify="required" placeholder="请输入邮箱验证码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block" style="padding-left: 50px;">
                        <input type="button" class="layui-btn" id="btn" value="确认重置" lay-submit lay-filter="*">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="../js/particles.min.js"></script>
<script src="../js/app.js"></script>
</body>
</html>

