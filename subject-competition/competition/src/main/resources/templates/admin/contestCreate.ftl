<!DOCTYPE html>
<html>
<head>
    <#import "../common.ftl" as com>
    <@com.depend />
    <style>
        .layui-input-inline select{
            z-index: 9999;
        }
    </style>
    <script>

        layui.use(['laydate','upload','form'], function() {
            var form = layui.form;
            var laydate = layui.laydate;

            var $ = layui.jquery
                ,upload = layui.upload;
            //日期
            laydate.render({
                elem: '#startTime',
            });
            laydate.render({
                elem: '#endTime',
            });

            //指定允许上传的文件类型
            upload.render({
                elem: '#enclosure'
                ,url: '/enclosure/upload' //改成您自己的上传接口
                ,accept: 'file' //普通文件
                ,done: function(res){
                    if (res.status=='success'){
                        alert(res.data);
                    }
                    //上传后的文件名回显到input框
                    document.getElementById("fileName").value=res.data;
                }
                ,error: function(){
                    //请求异常回调
                    alert("错误");
                }

            });

            delFile = function (){
                var filename = $("#fileName").val();
                if (filename=="") {
                    alert("还未上传文件！")
                }  else{
                    if(confirm("确实要删除吗？")){
                        $.ajax({
                            type:"POST",
                            url:"/enclosure/deleteFile",
                            data:{fileName:$("#fileName").val()},
                            dataType:"json",
                            success:function (data) {
                                if (data.status=="success") {
                                    alert('删除成功');
                                    $("#fileName").val("");
                                }else{
                                    alert('上传失败，原因：'+data.data);
                                }
                            },
                            error:function (data) {
                                alert(data.data);
                            }
                        });
                    }
                }
            }

            form.on('submit(*)', function(data){//监听要放在use里

                $.ajax({
                    url:"/admin/contest/create-page",
                    data:{
                        "contestName":$("#title").val(),
                        "startTime":$("#startTime").val(),
                        "endTime":$("#endTime").val(),
                        "leader":$("#leader").val(),
                        "contestContent":$("#editor").val(),//富文本编辑器的内容
                        "enclosureName":$("#fileName").val()
                    },
                    method:"POST",
                    success:function (data) {
                        if (data.status=="success"){
                            alert("发布成功");
                            window.location.href="<@com.path/>/admin/contest";
                        }else {
                            alert("发布失败，"+data.data);
                            console.log(data.data);
                        }
                    },
                    error:function (data) {
                        alert("操作失败"+data.data);
                        console.log(data.data);
                    }
                });
            });
        })
    </script>
</head>
<body style="background-color: #ffffff">
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
        <div class="ax-flex-block ax-body">
            <div class="ax-flex-col" style="padding-left: 20px;padding-top: 30px;">
                <form class="layui-form" method="post" enctype="multipart/form-data">
                    <div class="layui-form-item">
                        <div class="layui-col-sm6">
                            <label class="layui-form-label">标&nbsp&nbsp&nbsp题</label>
                            <div class="layui-input-block">
                                <input type="text" name="contestName" id="title" placeholder="标题" autocomplete="off" lay-verify="required" lay-reqtext="不能为空" class="layui-input" >
                            </div>
                        </div>
                        <div class="layui-col-sm6">
                            <div class="layui-inline">
                                <label class="layui-form-label">开始时间</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" id="startTime"  placeholder="yyyy-MM-dd" lay-verify="required" lay-reqtext="不能为空">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-col-sm6">
                            <label class="layui-form-label">负责人</label>
                            <div class="layui-input-block">
                                <input type="text" class="layui-input" id="leader"  placeholder="负责人姓名" lay-verify="required" lay-reqtext="不能为空">
                            </div>
                        </div>
                        <div class="layui-col-sm6">
                            <div class="layui-inline">
                                <label class="layui-form-label">结束时间</label>
                                <div class="layui-input-inline">
                                    <input type="text" class="layui-input" id="endTime"  placeholder="yyyy-MM-dd" lay-verify="required" lay-reqtext="不能为空">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item" >
                        <@com.editorContent/>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-row">
                                <div class="layui-col-md9">
                                    <button type="button" class="layui-btn layui-btn-primary layui-border-green" id="enclosure"><i class="layui-icon"></i>上传附件</button>
                                    <button type="button" 	class="layui-btn layui-btn-primary layui-border-red" id="delfile" onclick="delFile();">删除附件</button>
                                    <input type="text" id="fileName" name="fileName" readonly="readonly" value="" style="width: 420px;border-style:none;">
                                </div>
                                <div class="layui-col-md3 ">
                                    <input type="button" class="layui-btn" id="btn" value="发布" lay-submit lay-filter="*">
                                </div>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>

</div>
</body>
<@com.wangEditor/>
</html>