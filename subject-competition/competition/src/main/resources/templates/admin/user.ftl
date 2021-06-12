<!DOCTYPE html>
<html>
<head>
    <title>主页</title>
    <#import "../common.ftl" as com>
    <@com.depend/>

    <style>
        layui-nav .layui-nav-item{
            width: 20%;
        }
        body .layui-nav .layui-nav-more{display: none}
    </style>

</head>
<body>
<div class="layui-container">
    <@com.nav2/>
    <div class="layui-col-xs-offset9" style="padding-top: 10px;padding-left: 30px;">
        <button type="button" class="layui-btn layui-btn-sm" id="userExcel"><i class="layui-icon"></i>通过Excel表导入</button>
        <button type="button" onclick="window.location.href='<@com.path/>/admin/user/create'"  class="layui-btn layui-btn-sm" style="width: 90px;">注册</button>
    </div>
    <@com.tree/>
</div>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script>
    layui.use(['table','upload'], function(){
        var table = layui.table;
        var upload=layui.upload;
        upload.render({
            elem: '#userExcel'
            ,url: '/admin/user/import' //改成您自己的上传接口
            ,accept: 'file' //普通文件
            ,done: function(res){
                if (res.status=='success'){
                    alert("操作成功");
                    window.location.reload();
                }else {
                    alert(res.data);
                }
            }
            ,error: function(){
                //请求异常回调
                alert("错误");
            }
        });

        table.render({
            elem: '#test'
            ,url:'/admin/users'
            ,title: '学生用户数据表'
            ,limits:[5,10,20,50]
            ,request:{
                pageName:'currentPage'
                ,limitName:'limit'
            }
            , response: {
                statusName: 'status' //规定数据状态的字段名称，默认：code
                ,statusCode: 'success' //规定成功的状态码，默认：0
                ,countName: 'count'//规定数据总数的字段名称，默认：count
            }
            ,page:true
            ,parseData: function(res){ //res 即为原始返回的数据
                console.log(res);
                return {
                    "status": res.status, //解析接口状态
                    "count": res.total, //解析数据长度
                    "data": res.data.users, //解析数据列表
                    "count":res.data.total,
                    "page":res.data.pages,
                };
            }
            //表格自适应
            ,done: function (res, curr, count) {
                $("table").width("100%");
            }
            ,cols: [[
                {field:'userId', title:'学号', width:200,sort: true,id:'userId'}
                ,{field:'userName', title:'姓名', width:120}
                ,{field:'faculty', title:'学院',sort: true, width:180}
                ,{field:'classA', title:'班级',sort: true, width:180}
                ,{field:'gender', title:'性别',sort: true, width:80,templet: function(d){if(d.gender == 1){return '男'}else{return '女'}}}
                ,{title:'操作', toolbar: '#barDemo', width:150}
            ]]
            ,page: true

        });


        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            //console.log(obj)
            if(obj.event === 'del'){
                var del='del';
                layer.confirm('真的删除该条内容吗', function(index){
                    obj.del();
                    $.ajax({
                            type:'GET',
                            url:'/admin/user/delete',
                            data:{'userId':data.userId},
                            success:function (data){
                                if (data.status=='success'){
                                    alert('删除成功');
                                    window.location.reload();
                                }else {
                                    alert('失败，原因:'+data.data);
                                }
                            },
                            error:function (data){
                                alert(data);
                            }
                        }
                    );
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                //点击编辑按钮实现跳转编辑页面
                $("a.layui-btn-normal").attr("href","/admin/user/update/"+data.userId);
            }
        });
    });
</script>
</body>

</html>