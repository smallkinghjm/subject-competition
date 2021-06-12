<!DOCTYPE html>
<html>
<head>
    <title>主页</title>
    <#import "../common.ftl" as com>
    <@com.depend/>
    <style>
        #box{
            width: 500px;
            height: 300px;
            background-color: #ffffff;
            position: absolute;
            top:50%;
            left:50%;
            margin-top:50px;
            margin-left:-250px;
        }
        #from{
            width: 500px;
            height: 300px;
            position: absolute;
            top:50%;
            left:50%;
            margin-top: -200px;
            margin-left: -280px;
        }
    </style>
</head>
<body>
<div class="layui-container">
    <@com.nav2/>
    <div class="ax-flex-row ax-admin">
        <div>
            <nav class="ax-flex-col">
                <a  class="ax-close-nav-all"><span class="ax-iconfont ax-icon-left"></span></a>
                <div class="ax-nav-header">
                    <a  class="ax-close-nav ax-iconfont ax-icon-menu-fold"></a>
                </div>
                <div class="ax-flex-block ax-nav-main">
                    <@com.admin/>
                </div>
            </nav>
        </div>
        <div class="ax-flex-block ax-body" >
            <div class="ax-flex-col" style="padding-left: 20px;">
                <div id="box">
                    <form class="layui-form" style="vertical-align:middle;" id="from">
                        </br></br>
                        <div class="layui-form-item">
                            <label class="layui-form-label">学号</label>
                            <div class="layui-input-block">
                                <input type="text" name="userId" id="userId" value="${user.userId}" autocomplete="off" lay-verify="required|id|isExist" class="layui-input" >
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">姓名</label>
                            <div class="layui-input-block">
                                <input type="text" name="userName" id="userName" value="${user.userName}" lay-verify="required|name" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">学院</label>
                            <div class="layui-input-block">
                                <input type="text" name="faculty" id="faculty" value="${user.faculty}" lay-verify="required" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">班级</label>
                            <div class="layui-input-block">
                                <input type="text" name="classA" id="classA" value="${user.classA}" lay-verify="required" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">性别</label>
                            <div class="layui-input-block">
                                <#if user.gender==1>
                                    <input type="radio" name="gender" value="1" title="男" checked="checked">
                                    <input type="radio" name="gender" value="2" title="女" >
                                <#else >
                                    <input type="radio" name="gender" value="1" title="男">
                                    <input type="radio" name="gender" value="2" title="女" checked="checked">
                                </#if>
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <div class="layui-input-block" style="padding-left: 50px;">
                                <input type="button" class="layui-btn layui-btn-normal" id="btn" value="确认修改" lay-submit lay-filter="*">
                            </div>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>


</div>
<script>
    layui.use('form', function(){
        var form = layui.form;
        //自定义验证规则
        form.verify({
            id:[/^\d{13}$/
                ,'学号必须为13位数字']
        });
        form.on('submit(*)', function(data){//监听要放在use里
            $.ajax({
                type:"POST",
                contentType:"application/x-www-form-urlencoded",
                url:"/admin/user/saveUpdate",
                data:{
                    "id":${user.id},
                    "userId":$("#userId").val(),
                    "userName":$("#userName").val(),
                    "faculty":$("#faculty").val(),
                    "classA":$("#classA").val(),
                    "gender":$(":checked").val()
                },
                success:function (data) {
                    //alert(JSON.stringify(data));
                    if (data.status=="success"){
                        alert('修改成功');
                        window.location.href="<@com.path/>/admin/user";
                    }else {
                        alert("修改失败"+data.data);
                        console.log('错误码：'+data.status+',错误信息：'+data.data);
                    }
                },
                error:function (data) {
                    alert("修改失败"+data.data);
                    console.log('错误码：'+data.status+',错误信息：'+data.data);
                }
            });
        })
    });
</script>
</body>

</html>