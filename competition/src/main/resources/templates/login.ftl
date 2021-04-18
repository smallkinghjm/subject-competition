<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <link rel="stylesheet" href="../layui/css/layui.css"  type="text/css" >
    <style>
        html{
            width: 100%;
            height: 100%;
            overflow: hidden;
            font-style: initial;
        }

        body{
            width: 100%;
            height: 100%;
            font-family: 'Open Sans',sans-serif;
            margin: 0;
            background:url("../images/5.jpg");
            background-repeat: no-repeat;
           /* background-positon: 100%, 100%;*/
           /* background-color: #4A374A;*/
        }

        #p{
            position: absolute;
            top: 50%;
            left:50%;
            margin: -150px 0 0 -150px;
            width: 300px;
            height: 300px;
        }

        #p h1{
            color: #fff;
            text-shadow:0 0 10px;
            letter-spacing: 1px;
            text-align: center;
        }

        h1{
            font-size: 2em;
            margin: 0.67em 0;

        }
        #login{
            position: relative;
            top: 40%;
            left:40%;
            margin: -120px 0 20px 50px;
            width: 300px;
            height: 300px;
        }
        #btn{
            width: 300px;
            min-height: 20px;
            display: block;
/*            background-color: #4a77d4;
            border: 1px solid #3762bc;
            color: #fff;*/
            padding: 9px 14px;
            font-size: 15px;
            line-height: normal;
            border-radius: 5px;
            margin: 0;

        }
        .layui-input{
            height: 45px;
        }
        .layui-inline{
            width: 75%;
        }

    </style>
    <script type="text/javascript" src="../layui/layui.js"></script>
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script>
            layui.use('form', function(){
                var form = layui.form;
                //自定义验证规则
                form.verify({
                    password: [
                        /^[\S]{6,20}$/
                        ,'密码必须6到20位，且不能出现空格']
                });
                form.on('submit(*)', function(data){//监听要放在use里
                    $.ajax({
                        type:"POST",
                        contentType:"application/x-www-form-urlencoded",
                        url:"/user/login",
                        data:{
                            "userId":$("#userId").val(),
                            "password":$("#password").val(),
                            "verifyCode":$("#verifyCode").val()
                        },
                        success:function (data) {
                            //alert(JSON.stringify(data));
                            if (data.status=="success"){
                                alert('登陆成功');
                                window.location.href="http://localhost:8080/user/index";
                            }else if (data.status=="11111"){
                                /*alert('您为首次登陆，为了您的信息安全，请绑定邮箱，并修改初始密码!!!');*/
                                alert(data.data);
                                window.location.href="http://localhost:8080/user/first-page";
                            } else {
                                alert(data.data);
                                console.log('错误码：'+data.status+',错误信息：'+data.data);
                            }
                        },
                        error:function (data) {
                            alert(data.data);
                            console.log('错误码：'+data.status+',错误信息：'+data.data);
                        }
                    });
                })
            });



   /*     $(document).ready(function () {
                $.ajax({
                    type:"POST",
                    contentType:"application/x-www-form-urlencoded",
                    url:"/user/login",
                    data:{
                        "userId":$("#userId").val(),
                        "password":$("#password").val(),
                        "verifyCode":$("#verifyCode").val()
                    },
                    success:function (data) {
                        //alert(JSON.stringify(data))
                        if (data.status=="success"){

                        }else {
                            //layer.msg('验证码错误', {icon: 5});
                            alert(JSON.stringify(data))
                        }
                    },
                    error:function (data) {
                        layer.msg('系统错误', {icon: 5});
                    }
                });
                return false;
            })
        })*/

    </script>
</head>
<body >
    <p id="p">
        <h1 id="login">Login</h1>
    <div class="layui-col-md5 layui-col-md-offset3">
        <form class="layui-form" method="post">
            <div class="layui-form-item">
                <label class="layui-form-label">学号或工号</label>
                <div class="layui-input-block">
                    <input type="text" name="userId" id="userId" placeholder="请输入学号或职工号" autocomplete="off" lay-verify="required" lay-reqtext="不能为空" class="layui-input" >
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">密 码</label>
                <div class="layui-input-block">
                    <input type="password" name="password" id="password" placeholder="请输入密码" lay-verify="required|password" lay-reqtext="不能为空" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">验证码</label>
                <div class="layui-input-block">
                    <div class="layui-inline" >
                        <input type="text" name="verifyCode" id="verifyCode" placeholder="请输入验证码" lay-verify="required" lay-reqtext="不能为空" autocomplete="off" class="layui-input">
                    </div>

                    <img src="/password/getVerify" id="imgCode"  onclick="function getVerify() {
                                 $('#imgCode').attr('src', '/password/getVerify/?' + Math.random());
                            }
                            getVerify()" style="position: absolute;right: 0px;">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-row layui-col-space1">
                    <div class="layui-input-block">
                        <div class="layui-col-md3 layui-col-md-offset1">
                            <input type="button" class="layui-btn" id="btn" value="立即登录" lay-submit lay-filter="*"></input>
                        </div>
                        <div class="layui-col-md2 layui-col-md-offset6">
                            <a href="/user/forgetPassword-page">忘记密码？</a>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
    </p>

</body>
</html>

