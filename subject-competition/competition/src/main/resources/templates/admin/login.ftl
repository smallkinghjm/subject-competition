<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理员主页</title>
    <#import "../common.ftl" as com>
    <@com.depend/>
    <script>
        layui.use('form', function(){
            var form = layui.form;
            form.on('submit(*)', function(data){
                $.ajax({
                    type:"POST",
                    contentType:"application/x-www-form-urlencoded",
                    url:"/admin/login",
                    data:{
                        "name":$("#name").val(),
                        "password":$("#password").val(),
                    },
                    success:function (data) {
                        //alert(JSON.stringify(data));
                        if (data.status=="success"){
                            window.location.href="http://localhost:8080/admin/home";
                        } else {
                            alert(data.data);
                            window.location.reload();
                        }
                    },
                    error:function (data) {
                        alert(data.data);
                        //window.location.reload();
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
        #p{
            font-size: 40px;
            top: 50%;
            left: 50%;
            position: absolute;
            margin-top: -230px;
            margin-left: -100px;
            font-weight: bold;
            color: #000000;
        }
        #from{
            width: 500px;
            height: 300px;
            position: absolute;
            top:50%;
            left:50%;
            margin-top: -70px;
            margin-left: -280px;
        }
    </style>
</head>
<body>

<div id="particles-js">
    <p id="p">管理员登录</p>
    <div id="box">
        <form class="layui-form" method="post" id="from">
            <div class="layui-form-item">
                <label class="layui-form-label">账 号</label>
                <div class="layui-input-block">
                    <input type="text" name="name" id="name" placeholder="请输入管理员账号" autocomplete="off" lay-verify="required" lay-reqtext="不能为空" class="layui-input" >
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">密 码</label>
                <div class="layui-input-block">
                    <input type="password" name="password" id="password" placeholder="请输入密码" lay-verify="required" lay-reqtext="不能为空" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item" style="padding-left: 110px;">
                <div class="layui-input-block">
                    <input type="button" class="layui-btn" id="btn" value="立即登录" lay-submit lay-filter="*" >
                </div>
            </div>
        </form>
    </div>
</div>
<!-- scripts -->
<script src="../../js/particles.min.js"></script>
<script src="../../js/app.js"></script>
</body>
</html>