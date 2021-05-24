<!DOCTYPE html>
<html>
<head>
    <title>主页</title>
    <#import "common.ftl" as com>
    <@com.depend/>

    <style>
        layui-nav .layui-nav-item{
            width: 20%;
        }
        body .layui-nav .layui-nav-more{display: none}
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
                <a><cite>已报赛事</cite></a>
            </p>
        </span>
        <div style="padding-left: 30px;">
            <table class="layui-hide" id="test" lay-filter="test"></table>
        </div>
    </div>

</div>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">取消报名</a>
</script>

<script>
    layui.use(['jquery', 'form', 'layer', 'laydate', 'table'], function(){
        var table = layui.table;
        table.render({
            elem: '#test'
            ,url:'/enroll/enrolls'
            ,title: '用户数据表'
            ,page:true
            ,limit: 5
            ,limits:[5,10,15]
            ,request: {
                pageName: 'currentPage' //页码的参数名称，默认：page
                ,limitName: 'limit' //每页数据量的参数名，默认：limit
            }
            , response: {
                statusName: 'status' //规定数据状态的字段名称，默认：code
                , statusCode: 'success' //规定成功的状态码，默认：0
            }
            ,parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                console.log(res);
                return {
                    "status": res.status, //解析接口状态
                    "count": res.data.length, //解析数据长度
                    "data": res.data//解析数据列表
                };
            }

            ,cols: [[
                {field:'contestName', title:'赛事名称',   fixed: 'left',sort:true}
                ,{field:'studentId', title:'学号',  fixed: 'left',sort: true,id:'eventId'}
                ,{field:'studentName', title:'姓名',   fixed: 'left'}
                ,{field:'studentFaculty', title:'学院',  fixed: 'left',sort:true}
                ,{field:'studentClass', title:'班级',fixed: 'left',sort:true}
                ,{field:'instructor', title:'指导教师',fixed: 'left',sort:true}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
            ]]
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
                            url:'/enroll/delete',
                            data:{'studentId':data.studentId,
                                'contestId':data.contestId
                            },
                            success:function (data){
                                console.log(data.status);
                                if (data.status=='success'){
                                    alert('取消报名成功');
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
            }
        });
    });
</script>
</body>

</html>