<!DOCTYPE html>
<html>
<head>
    <title>比赛结果</title>
    <#import "common.ftl" as com>
    <@com.depend/>
    <@com.style/>
    <script src="<@com.path/>/js/confetti.js"></script>
    <style>
        canvas {
            display: block;
            position: relative;
            zindex:1;
            pointer-events: none;
            position: fixed;top: 0;
        }
    </style>
</head>
<body>
<canvas id="canvas" width="1920" height="950"></canvas>
<div><@com.nav/></div>
<div class="layui-container" style="background-color: #ffffff;">
    <div style="height: 480px;">
        <span class="layui-breadcrumb" lay-separator=">>">
            <p style="height: 20px; padding-top: 10px;">当前位置：<a href="<@com.path/>/home">首页</a>
            <a><cite>比赛结果</cite></a>
            </p>
        </span>
        <table class="layui-hide" id="test"></table>
    </div>

    <script>
        layui.use(['jquery', 'form', 'layer', 'laydate', 'table'], function(){
            var table = layui.table;
            table.render({
                elem: '#test'
                ,url:'/events?type=1'
                ,title: '赛事项目表'
                ,page:true
                ,limit: 10
                ,limits:[10,20]
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
                        "data": res.data.events, //解析数据列表
                        "count":res.data.total,
                        "page":res.data.pages,
                    };
                }

                ,cols: [[
                    {field:'eventId', title:'编号',fixed: 'left',sort: true}
                    ,{field:'title', title:'标题', fixed: 'left',templet: '<div><a href="/event/{{d.eventId}}" class="layui-table-link">{{d.title}}</a></div>'}
                    ,{field:'updateTime', title:'发布时间', fixed: 'left',sort: true,
                        templet: function(res){
                            return layui.util.toDateString(res.updateTime, "yyyy-MM-dd HH:mm:ss");}}
                ]]
            });
        });
    </script>
</div>
</body>
</html>