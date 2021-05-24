<!DOCTYPE html>
<html>
<head>
    <meta charset= "utf-8" >
    <title>个人信息</title>
    <#import "common.ftl" as com>
    <@com.depend/>
    <@com.style/>
    <style>

        #box{
            width: 500px;
            height: 450px;
            background-color: #ffffff;
            position: absolute;
            top:50%;
            left:50%;
            margin-top:-50px;
            margin-left:-250px;
        }
        #change{
            position: absolute;
            top:50%;
            left:50%;
            margin-top: -12px;
            margin-left: 130px;
            z-index: 500;
            color: #b3a4a4;
        }
        #from{
            width: 500px;
            height: 440px;
            position: absolute;
            top:50%;
            left:50%;
            margin-top: -200px;
            margin-left: -300px;
        }

    </style>
</head>
<body>
    <div>
        <@com.nav/>
        <div class="layui-container">
            <span class="layui-breadcrumb" lay-separator=">>">
            <p style="margin-top: 10px;">当前位置：
                <a href="<@com.path/>/home">首页</a>
                <a href="">个人中心</a>
                <a><cite>个人信息</cite></a>
            </p>
        </span>
        </div>
    </div>
    <div class="layui-container">
        <div id="box">
            <form class="layui-form" id="from">
                <div class="layui-form-item">
                    <label class="layui-form-label">学号</label>
                    <div class="layui-input-block">
                        <input type="text" name="userId" value="${user.userId}" readonly="true" class="layui-input" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">姓名</label>
                    <div class="layui-input-block">
                        <input type="text" name="userName" value="${user.userName}" readonly="true" class="layui-input" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-block">
                        <input type="text" name="password" value="*************" readonly="true" class="layui-input" >
                        <a href="<@com.path/>/user/changePassword" id="change" >修改密码</a>
                    </div>
                </div>
                <div class="layui-form-item">
                        <label class="layui-form-label">身份</label>
                    <div class="layui-input-block">
                        <input type="text" name="userId" value="学生" readonly="true" class="layui-input" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">学院</label>
                    <div class="layui-input-block">
                        <input type="text" name="collegeClass" value="${user.faculty}" readonly="true" class="layui-input" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">班级</label>
                    <div class="layui-input-block">
                        <input type="text" name="collegeClass" value="${user.classA}" readonly="true" class="layui-input" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">性别</label>
                    <div class="layui-input-block">
                        <#if user.gender==1>
                            <input type="text" name="collegeClass" value="男" readonly="true" class="layui-input" >
                            <#else >
                                <input type="text" name="collegeClass" value="女" readonly="true" class="layui-input" >
                        </#if>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">邮箱</label>
                    <div class="layui-input-block">
                        <input type="text" name="email" value="${user.email}" readonly="true" class="layui-input" >
                    </div>
                </div>
            </form>
        </div>

    </div>
</body>
</html>