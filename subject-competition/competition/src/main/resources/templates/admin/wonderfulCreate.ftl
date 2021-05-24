<!DOCTYPE html>
<html>
<head>
    <#import "../common.ftl" as com>
    <@com.depend />
    <style>
        .layui-input-inline select{
            z-index: 9999;
        }
        .layui-upload-drag {
             padding: 0px;

        }
        img{
            width: 100px;
            height: 100px;
        }
    </style>
    <script>

        layui.use(['laydate','upload','form'], function() {
            var form = layui.form;
            var $ = layui.jquery
                ,upload = layui.upload;

            //指定允许上传的文件类型
            upload.render({
                elem: '#cover'
                ,url: '/enclosure/cover/upload' //改成您自己的上传接口
                ,done: function(res){
                    layer.msg('上传成功');
                    layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', res.data.url);
                    document.getElementById("fileName").value=res.data.fileName;
                    console.log(res);
                }
            });


            form.on('submit(*)', function(data){//监听要放在use里

                $.ajax({
                    url:"/admin/event/create-page",
                    data:{
                        "title":$("#title").val(),
                        "type":3,
                        "content":$("#editor").val(),//富文本编辑器的内容
                        "enclosureName":$("#fileName").val()
                    },
                    method:"POST",
                    success:function (data) {
                        if (data.status=="success"){
                            alert("发布成功");
                            window.location.href="<@path/>/admin/wonderful";
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
        <div class="ax-flex-block ax-body">
            <div class="ax-flex-col" style="padding-left: 20px;padding-top: 30px;">
                <form class="layui-form" method="post" enctype="multipart/form-data">
                    <div class="layui-col-md3" >
                            <label class="layui-form-label">封&nbsp&nbsp&nbsp&nbsp面</label>
                            <div class="layui-upload-drag" id="cover">
                                <div  id="uploadDemoView">
                                    <img src="<@com.path/>/images/cover.png" alt="上传成功后渲染" style="max-width: 196px">
                                </div>
                            </div>
                        <div class="layui-hide">
                            <input type="text" id="fileName" name="fileName" readonly="readonly" value="" style="height: 30px;width: 400px">
                        </div>
                        </div>
                    <div class="layui-col-md9">
                            <label class="layui-form-label">标&nbsp&nbsp&nbsp&nbsp题</label>
                            <div class="layui-input-block">
                                <input type="text" name="title" id="title" placeholder="标题" autocomplete="off" lay-verify="required" lay-reqtext="不能为空" class="layui-input" >
                            </div>
                        </div>
                    <div class="layui-form-item" >
                        <div id="editor"></div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-row layui-col-space1">
                            <div class="layui-input-block">
                                <div class="layui-col-md3 layui-col-md-offset5">
                                    <input type="button" class="layui-btn" id="btn" value="发布" lay-submit lay-filter="*">
                                </div>
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