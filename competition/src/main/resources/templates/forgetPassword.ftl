<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>忘记密码</title>
    <link rel="stylesheet" href="../layui/css/layui.css"  type="text/css" >
    <script type="text/javascript" src="../layui/layui.js"></script>
    <script src="../js/jquery.min.js"></script>
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
            form.on('submit(*)', function(data){//监听要放在use里
                $.ajax({
                    type:"POST",
                    contentType:"application/x-www-form-urlencoded",
                    url:"/user/forgetPassword",
                    data:{
                        "userId":$("#userId").val(),
                        "email":$("#email").val(),
                        "newPassword":$("#newPassword").val(),
                        "mailCode":$("#mailCode").val()
                    },
                    success:function (data) {
                        if (data.status=="success"){
                            alert('重置成功');
                            window.location.href="http://localhost:8080/user/login-page";
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
                // return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            })
        });


        $(function () {
            $('#getCode').click(function(){
                //发送验证码
                var email = $("#email").val();
                if(email==""){
                    return;
                }
                layer.alert('验证码已发送，请及时查看您的邮箱', {icon: 6});
                $(".code").toggle();
                //ajax实现后台邮箱的发送
                $.ajax({
                    url: "/password/sendEmail",
                    data: {"email": email},
                    type: "post",
                    success: function (data) {
                        //回调函数 data 返回流
                        if (data == "failure") {
                            alert("发送失败");
                        }
                        else if (data=="false"){
                            alert("目标邮箱不存在，请检查你的邮箱是否正确");
                        }
                    }
                });
            });
        })
    </script>
</head>
<body>
<div class="layui-container">
    <div class="layui-col-md6 layui-col-md-offset3">
        <div class="grid-demo grid-demo-bg1">
            <form class="layui-form"  method="post" style="vertical-align:middle;">
                <div class="layui-form-item">
                    <label class="layui-form-label">学号或工号</label>
                    <div class="layui-input-block">
                        <input type="text" name="userId" id="userId" placeholder="请输入学号或职工号" autocomplete="off" lay-verify="required" lay-reqtext="不能为空" class="layui-input" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">邮箱号</label>
                    <div class="layui-input-block">
                        <input type="text" name="email" id="email" placeholder="请邮箱号" autocomplete="off" lay-verify="required|email" class="layui-input" >
                    </div>
                </div>
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
                        <div class="layui-input-inline">
                            <input type="text" name="mailCode" id="mailCode" lay-verify="required" placeholder="请输入验证码" autocomplete="off" class="layui-input">
                        </div>
                        <input type="button" id="getCode" class="ui grey label" value="获取验证码">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <input type="button" class="layui-btn" id="btn" value="确认重置" lay-submit lay-filter="*"></input>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

