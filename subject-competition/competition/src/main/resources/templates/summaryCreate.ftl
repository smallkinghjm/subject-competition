<!DOCTYPE html>
<html>
<head>
    <title>赛事总结</title>
    <#import "common.ftl" as com>
    <@com.depend/>
    <@com.style/>
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
    <form class="layui-form" method="post" enctype="multipart/form-data">
        <div class="layui-form-item">
            <label class="layui-form-label" style="float:left">标&nbsp&nbsp&nbsp&nbsp题</label>
            <div class="layui-input-block">
                <input type="text" name="title" id="title" placeholder="标题" autocomplete="off" lay-verify="required" lay-reqtext="不能为空" class="layui-input"  >
            </div>
        </div>
        <div class="layui-form-item" >
            <@com.editorContent/>
        </div>
        <div class="layui-form-item">
            <div class="layui-row">
                <button type="button" class="layui-btn layui-btn-primary layui-border-green" id="enclosure"><i class="layui-icon"></i>上传附件</button>
                <button type="button" 	class="layui-btn layui-btn-primary layui-border-red" id="delfile" onclick="delFile();">删除附件</button>
                <input type="text" id="fileName" name="fileName" readonly="readonly" value="" style="width: 420px;border-style:none;">
                <input type="button" class="layui-btn" id="btn" value="发布" lay-submit lay-filter="*">
            </div>
        </div>
    </form>
    <@com.wangEditor/>
</div>
<script>
    layui.use(['laydate','upload','form'], function() {
        var form = layui.form;
        var $ = layui.jquery
            ,upload = layui.upload;

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
                        url:"/enclosure/delFile",
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
                url:"/admin/event/create-page",
                data:{
                    "title":$("#title").val(),
                    "type":2,
                    "content":$("#editor").val(),//富文本编辑器的内容
                    "enclosureName":$("#fileName").val()
                },
                method:"POST",
                success:function (data) {
                    if (data.status=="success"){
                        alert("发布成功");
                        window.location.href="<@com.path/>/user/information";
                    }else {
                        alert(data.data);
                    }
                },
                error:function (data) {
                    alert("失败2222"+data.data);
                    console.log(data.data);
                }
            });
        });
    })
</script>
</body>
</html>


