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
                    url:"/password/check",
                    data:{
                        "verifyCode":$("#verifyCode").val()
                    },
                    success:function (data) {
                        if (data.status=="success"){
                            $.ajax({
                                type:"POST",
                                contentType:"application/x-www-form-urlencoded",
                                url:"/password/newForget-page",
                                data:{
                                    "str":$("#str").val()
                                }
                            });
                            window.location.href="<@com.path/>/password/resetpassword";
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
            height: 250px;
            background-color: #ffffff;
            position: absolute;
            top:50%;
            left:50%;
            margin-top:-150px;
            margin-left:-250px;
        }
        #from{
            width: 400px;
            height: 200px;
            position: absolute;
            top:50%;
            left:50%;
            margin-top: -80px;
            margin-left: -250px;
        }
    </style>
</head>
<body>
<div id="particles-js">
    <div id="box">
        <div class="grid-demo grid-demo-bg1">
            <form class="layui-form"  method="post" id="from">

                <div class="layui-form-item">
                    <label class="layui-form-label">账&nbsp&nbsp&nbsp号</label>
                    <div class="layui-input-block">
                        <input type="text" name="str" id="str" placeholder="请输入学号或邮箱号" autocomplete="off" lay-verify="required" class="layui-input" >
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
                    <div class="layui-input-block" style="padding-left: 90px;">
                        <input type="button" class="layui-btn" id="btn" value="下一步" lay-submit lay-filter="*">
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

