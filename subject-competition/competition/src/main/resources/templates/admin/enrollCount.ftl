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
    <div class="layui-row">
        <@com.nav2/>
    </div>
    <div>
        <table class="layui-hide" id="test" lay-filter="test"></table>
    </div>
</div>

<script>
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#test'
            ,url:'/admin/enroll/count?contestId=${contestId}'
            ,title: '参赛信息数据表'
            ,page:true
            ,limit: 10
            ,limits:[10,20,50,100,500,1000]
            ,toolbar: true //开启头部工具栏，并为其绑定左侧模板

            ,request: {
                pageName: 'currentPage' //页码的参数名称，默认：page
                ,limitName: 'limit' //每页数据量的参数名，默认：limit
            }
            , response: {
                statusName: 'status' //规定数据状态的字段名称，默认：code
                , statusCode: 'success' //规定成功的状态码，默认：0
            }
            ,parseData: function(res){ //res 即为原始返回的数据
                console.log(res);
                return {
                    "status": res.status, //解析接口状态
                    "count": res.total, //解析数据长度
                    "data": res.data.enrolls, //解析数据列表
                    "count":res.data.total,
                    "page":res.data.pages,
                };
            }

            ,cols: [[
                {field:'studentId', title:'学号', width:250, fixed: 'left',sort: true,id:'eventId'}
                ,{field:'studentName', title:'姓名', width:100,  fixed: 'left',sort:true}
                ,{field:'studentFaculty', title:'学院', width:180,  fixed: 'left',sort: true}
                ,{field:'studentClass', title:'班级', width:150,  fixed: 'left',sort: true}
                ,{field:'idCard', title:'身份证号', width:250,  fixed: 'left'}
                ,{field:'instructor', title:'指导教师', width:200,  fixed: 'left',sort: true}
            ]]
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            var checkStatus = table.checkStatus(obj.config.id);
            if(obj.event === 'look'){
                //点击编辑按钮实现跳转编辑页面
                $("a.layui-btn-normal").attr("href","/admin/enroll/"+data.contestId);
            }
        });
    });
</script>
</body>
</html>