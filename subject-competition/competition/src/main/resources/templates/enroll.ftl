
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>赛事报名</title>
    <#import "common.ftl" as com>
    <@com.depend/>
        <@com.style/>
        <script>
            layui.use('form', function(){
                var form = layui.form;
                form.verify({
                });
                form.on('submit(*)', function(data){//监听要放在use里
                    $.ajax({
                        type:"POST",
                        contentType:"application/x-www-form-urlencoded",
                        url:"<@com.path/>/enroll/create",
                        data:{
                            "studentId":$("#studentId").val(),
                            "studentName":$("#studentName").val(),
                            "studentFaculty":$("#studentFaculty").val(),
                            "studentClass":$("#studentClass").val(),
                            "IDCard":$("#IDCard").val(),
                            "instructor":$("#instructor").val(),
                            "contestId":${contest.contestId}
                        },
                        success:function (data) {
                            //alert(JSON.stringify(data));
                            if (data.status=="success"){
                                alert('报名成功');
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
        </script>
</head>
<body>
    <div>
        <@com.nav/>
        <div class="layui-container">
            <span class="layui-breadcrumb" lay-separator=">>">
            <p style="margin-top: 10px;">当前位置：
                <a href="<@com.path/>/home">首页</a>
                <a href="<@com.path/>/contest">赛事项目</a>
                <a href="<@com.path/>/contest/view/${contest.contestId}/">赛事详情</a>
                <a><cite>报名参赛</cite></a>
            </p>
        </span>
        </div>
    </div>
    <div class="layui-col-md5 layui-col-md-offset3" style="margin-top: 30px">
        <form class="layui-form" method="post">
            <div class="layui-form-item">
                <label class="layui-form-label">编&nbsp&nbsp号</label>
                <div class="layui-input-block">
                    <input type="text" name="contestId" id="contestId"  value="${contest.contestId}" readonly="true" class="layui-input" >
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">赛事名称</label>
                <div class="layui-input-block">
                    <input type="text" name="contestName" id="contestName"  value="${contest.contestName}" readonly="true" class="layui-input" >
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">学&nbsp&nbsp号</label>
                <div class="layui-input-block">
                    <input type="text" name="studentId" id="studentId" placeholder="请输入学号" autocomplete="off" lay-verify="required" lay-reqtext="不能为空" class="layui-input" >
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">姓&nbsp&nbsp名</label>
                <div class="layui-input-block">
                    <input type="text" name="studentName" id="studentName" placeholder="请输入姓名" lay-verify="required " lay-reqtext="不能为空" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">学&nbsp&nbsp院</label>
                <div class="layui-input-block">
                    <input type="text" name="studentFaculty" id="studentFaculty" placeholder="请输入学院" lay-verify="required" lay-reqtext="不能为空" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">班&nbsp&nbsp级</label>
                <div class="layui-input-block">
                    <input type="text" name="studentClass" id="studentClass" placeholder="请输入班级" lay-verify="required" lay-reqtext="不能为空" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">身份证号</label>
                <div class="layui-input-block">
                    <input type="text" name="IDCard" id="IDCard" placeholder="请输入身份证号" lay-verify="required" lay-reqtext="不能为空" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">指导教师</label>
                <div class="layui-input-block">
                    <input type="text" name="instructor" id="instructor" placeholder="请输入指导教师姓名，若不确定可不填"  lay-reqtext="不能为空" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-row layui-col-space1">
                    <div class="layui-input-block">
                        <div class="layui-col-md4 layui-col-md-offset2">
                            <input type="button" class="layui-btn" lay-submit="" lay-filter="*"value="立即报名"></input>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</body>
</html>