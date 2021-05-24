<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <#import "../common.ftl" as com/>
    <@com.depend/>
    <style>
        #box{
            width: 500px;
            height: 500px;
            background-color: #ffffff;
            position: absolute;
            top:50%;
            left:50%;
            margin-top:50px;
            margin-left:-250px;
        }
        #from{
            width: 400px;
            height: 500px;
            position: absolute;
            top:50%;
            left:50%;
            margin-top: -200px;
            margin-left: -230px;
        }
    </style>
</head>
<body style="background-color: rgba(246,110,9,0.78)">
<div class="layui-container">
    <div id="box">
        <form class="layui-form" id="from" method="post">
                <div class="layui-form-item">
                    <label class="layui-form-label">学&nbsp&nbsp&nbsp号</label>
                    <div class="layui-input-block">
                        <input type="text" id="userId" placeholder="请输入学号" autocomplete="off" lay-verify="required|id|isExist" class="layui-input" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">姓&nbsp&nbsp&nbsp名</label>
                    <div class="layui-input-block">
                        <input type="text" id="userName" placeholder="请输入姓名" lay-verify="required|name" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">初始密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="password" id="password" lay-verify="required|password" placeholder="请输入密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">确认密码</label>
                    <div class="layui-input-block">
                        <input type="password" name="checkPassword" id="checkPassword" lay-verify="required|checkPassword" placeholder="请再次输入密码" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">学&nbsp&nbsp&nbsp院</label>
                    <div class="layui-input-block">
                        <input type="text" id="faculty" lay-verify="required" placeholder="请输入所属学院,如:信息科学与工程学院" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">班&nbsp&nbsp&nbsp级</label>
                    <div class="layui-input-block">
                        <input type="text" id="classA" lay-verify="required" placeholder="请输入所属班级，如：软件工程2017-2班" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">性&nbsp&nbsp&nbsp别</label>
                    <div class="layui-input-block">
                        <input type="radio" name="gender" value="0" title="男">
                        <input type="radio" name="gender" value="1" title="女" checked="checked">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block" style="padding-left: 20px;">
                        <input type="button" class="layui-btn" id="register" value="立即登录" lay-submit lay-filter="*" >
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
            </form>
    </div>

</div>

<script>

    $(function () {
        $("#userId").bind("blur",function () {
            $.ajax({
                type:"POST",
                url:"/user/checkId",
                data:{"userId":$("#userId").val()},
                datatype:"json",
                success:function (data) {
                    if(data.status=='20001'){
                        //查找为空，该学号未注册，不做操作
                    }else {
                        layer.msg('学号已存在,请重新输入', {icon: 5});
                    }
                },
                error:function () {
                    layer.msg('系统出错，请稍后登录', {icon: 5});
                }
            })
        })
    })

    layui.use('form', function(){
        var form = layui.form;
        //自定义验证规则
        form.verify({
            id:[/^\d{13}$/
                ,'学号必须为13位数字'],
            name:[/^[\u4e00-\u9fa5]*$/
                ,'姓名必须为汉字'],
            password: [
                /^[\S]{6,20}$/
                ,'密码必须6到20位，且不能出现空格'],
            checkPassword:function(value) {
                if ($('input[name=password]').val() !== value)
                    return '两次密码输入不一致！';
            },

        });
    });
    $(function () {
        $("#register").on("click",function () {
            $.ajax({
                type:"POST",
                contentType:"application/x-www-form-urlencoded",
                url:"/user/register",
                data:{
                    "userId":$("#userId").val(),
                    "userName":$("#userName").val(),
                    "password":$("#password").val(),
                    "faculty":$("#faculty").val(),
                    "classA":$("#classA").val(),
                    "gender":$(":checked").val(),
                },
                success:function (data) {
                    //alert(JSON.stringify(data))
                    if (data.status=="success"){
                        alert('注册成功');
                        window.location.href="<@com.path/>/admin/user";
                    }else {
                        alert(data.data);
                    }
                },
                error:function (data) {
                    alert(data.data);
                }
            });
        })

    })

</script>

</body>
</html>

