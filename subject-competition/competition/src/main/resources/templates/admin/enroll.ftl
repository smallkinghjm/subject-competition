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
    <@com.tree/>
</div>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="look">查看参赛详情</a>
</script>

<script >
    layui.use(['jquery', 'form', 'layer', 'laydate', 'table'], function(){
        var table = layui.table;
        table.render({
            elem: '#test'
            ,url:'/admin/enroll/page'
            ,title: '参赛信息数据表'
            ,page:true
            ,limits:[5,10,20]
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
                    "data": res.data.contests, //解析数据列表
                    "count":res.data.total,
                    "page":res.data.pages,
                };
            }
            ,done: function (res, curr, count) {
                $("table").width("100%");
            }
            ,cols: [[
                {field:'contestId', title:'编号', width:200,sort: true}
                ,{field:'contestName', title:'标题', width:260,sort:true}
                ,{field:'contestStart', title:'开始时间', width:150,sort: true,
                    templet: function(res){
                        return layui.util.toDateString(res.contestStart, "yyyy-MM-dd");}}
                ,{field:'state', title:'状态', width:150,sort: true}
                ,{title:'操作', toolbar: '#barDemo', width:150}
            ]]
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){
            var data = obj.data;
            if(obj.event === 'look'){
                //点击编辑按钮实现跳转编辑页面
                $("a.layui-btn-normal").attr("href","/admin/enroll/"+data.contestId);
            }
        });
    });
</script>
</body>
</html>