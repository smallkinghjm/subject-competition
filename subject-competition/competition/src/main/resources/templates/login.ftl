<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
    <#import "common.ftl" as com>
    <@com.depend/>

    <style>
        #box{
            width: 500px;
            height: 300px;
            background-color: #ffffff;
            position: absolute;
            top:50%;
            left:50%;
            margin-top:-150px;
            margin-left:-250px;
        }
        #p{
           font-size: 30px;
            top: 50%;
            left: 50%;
            position: absolute;
            margin-top: -200px;
            margin-left: -50px;
            font-weight: bold;
            color: #dee2e6;
        }
        #from{
            width: 400px;
            height: 400px;
            position: absolute;
            top:50%;
            left:50%;
            margin-top: -100px;
            margin-left: -250px;
        }
    </style>
</head>
<body >

<!-- particles.js container -->
<div id="particles-js">
    <p id="p">登录</p>
    <div id="box">
        <form class="layui-form" method="post" id="from">
            <div class="layui-form-item">
                <label class="layui-form-label">学 号</label>
                <div class="layui-input-block">
                    <input type="text" name="userId" id="userId" placeholder="请输入学号" autocomplete="off" lay-verify="required" lay-reqtext="不能为空" class="layui-input" >
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
                        <input type="text" name="verifyCode" id="verifyCode" placeholder="验证码不区分大小写" lay-verify="required" lay-reqtext="不能为空" autocomplete="off" class="layui-input">
                    </div>

                    <img src="/password/getVerify" id="imgCode"  onclick="function getVerify() {
                                 $('#imgCode').attr('src', '<@com.path/>/password/getVerify/?' + Math.random());
                            }
                            getVerify()" style="position: absolute;right: 0px;">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-row layui-col-space1">
                    <div class="layui-input-block">
                        <div class="layui-col-md3 layui-col-md-offset1">
                            <input type="button" class="layui-btn" id="btn" value="立即登录" lay-submit lay-filter="*">
                        </div>
                        <div class="layui-col-md3 layui-col-md-offset5">
                            <a href="/password/newforget">忘记密码？</a>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- scripts -->
<script src="../js/particles.min.js"></script>
<script src="../js/app.js"></script>

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
                    url:"/user/login-page",
                    data:{
                        "userId":$("#userId").val(),
                        "password":$("#password").val(),
                        "verifyCode":$("#verifyCode").val()
                    },
                    success:function (data) {
                        //alert(JSON.stringify(data));
                        if (data.status=="success"){
                            //alert('登陆成功');
                            window.location.href="http://localhost:8080/user/home";
                        }else if (data.status=="11111"){
                            alert(data.data);
                            window.location.href="http://localhost:8080/user/first";
                        } else {
                            alert(data.data);
                            console.log('错误码：'+data.status+',错误信息：'+data.data);
                            //window.location.reload();
                        }
                    },
                    error:function (data) {
                        alert(data.data);
                        console.log('错误码：'+data.status+',错误信息：'+data.data);
                        //window.location.reload();
                    }
                });
            })
        });
    </script>
</body>
</html>

