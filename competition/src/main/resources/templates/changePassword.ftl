<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="../layui/css/layui.css"  type="text/css" >
    <script type="text/javascript" src="../layui/layui.js"></script>
    <script src="../js/jquery.min.js"></script>
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
                    url:"/user/changePassword",
                    data:{
                        "password":$("#password").val(),
                        "newPassword":$("#newPassword").val(),
                    },
                    success:function (data) {
                        //alert(JSON.stringify(data))
                        if (data.status=="success"){
                            alert("修改密码成功，您将重新登录。");
                            window.location.href="http://localhost:8080/user/login-page";
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
                // return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            })
        });

    </script>
</head>
<body>
<div class="layui-container">
    <div class="layui-col-md6 layui-col-md-offset3">
        <div class="grid-demo grid-demo-bg1">
            <form class="layui-form"  method="post" style="vertical-align:middle;">
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
                    <div class="layui-input-block">
                        <input type="button" class="layui-btn" id="btn" value="立即修改" lay-submit lay-filter="*"></input>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


</body>
</html>