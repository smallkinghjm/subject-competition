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
                elem: '#startTime'
                ,value: '${contest.contestStart}' //必须遵循format参数设定的格式
            });
            laydate.render({
                elem: '#endTime',
                value:'${contest.contestEnd}'
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
                    url:"/admin/contest/create-page",
                    data:{
                        "contestName":$("#contestName").val(),
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
<body>
<div class="layui-container">
    <div class="layui-fluid">
        <fieldset class="layui-elem-field layui-field-title">
            <legend style="text-align: center">新赛事编辑</legend>
        </fieldset>
        <div class="layui-row">
            <form class="layui-form" method="post" enctype="multipart/form-data">
                <div class="layui-form-item">
                    <div class="layui-col-sm10">
                        <label class="layui-form-label">标&nbsp&nbsp&nbsp&nbsp题</label>
                        <div class="layui-input-block">
                            <input type="text" name="contestName" id="contestName" placeholder="标题" value="${contest.contestName}" autocomplete="off" lay-verify="required" lay-reqtext="不能为空" class="layui-input" >
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-col-sm4">
                        <div class="layui-inline">
                            <label class="layui-form-label">开始时间</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="startTime"  placeholder="yyyy-MM-dd" lay-verify="required" lay-reqtext="不能为空">
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-sm4">
                        <div class="layui-inline">
                            <label class="layui-form-label">结束时间</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="endTime"  placeholder="yyyy-MM-dd" lay-verify="required" lay-reqtext="不能为空">
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-sm4">
                        <div class="layui-inline">
                            <label class="layui-form-label">负责人</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" id="leader"  placeholder="负责人姓名" value="${contest.leader}" lay-verify="required" lay-reqtext="不能为空">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item" >
                    <@com.editorContent/>

                </div>
                <div class="layui-form-item">
                    <div class="layui-row layui-col-space1">
                        <div class="layui-input-block">
                            <div class="layui-col-md3">
                                <button type="button" class="layui-btn" id="enclosure"><i class="layui-icon"></i>上传附件</button>
                            </div>
                            <div class="layui-col-md3 layui-col-md-offset6">
                                <input type="button" class="layui-btn" id="btn" value="发布" lay-submit lay-filter="*">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-row layui-col-space1">
                    <div class="layui-input-block">
                        <input type="text" id="fileName" name="fileName" readonly="readonly" value="" style="height: 30px;width: 400px">
                        <button type="button" 	class="layui-btn layui-btn-danger" id="delfile" onclick="delFile();">删除附件</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<script>
    //修改要显示附件名称
    $(document).ready(function(){
        if ("${enclosure.enclosureName}"=="无附件"){//判空处理
            return;
        }
        $("#fileName").attr("value","${enclosure.enclosureName!''}") ;
    });
</script>

<script type="text/javascript">
    $(function () {
        var E = window.wangEditor
        var editor = new E('#editor')

        editor.config.uploadImgServer = '/file/upload'
        editor.config.uploadFileName = 'file'
        editor.config.uploadImgHooks = {
            customInsert: function (insertImg, result, editor) {
                var url =result.data;//获取后台返回的url
                insertImg(url);
            }
        };
        //将编辑器内容绑定
        var $editor= $('#editor')
        editor.config.onchange = function (html) {
            // 监控变化，同步更新到 textarea
            $editor.val(html)
        };

        editor.create();
        editor.txt.append('${contest.contestContent}');//追加内容，必须放在这里
        $editor.val(editor.txt.html());
    })
</script>
</html>