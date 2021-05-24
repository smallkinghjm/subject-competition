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
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script>
    layui.use(['jquery', 'form', 'layer', 'laydate', 'table'], function(){
        var table = layui.table;
        table.render({
            elem: '#test'
            ,format: 'yyyy-MM-dd HH:mm:ss' //可任意组合
            ,url:'/admin/contests'
            ,title: '用户数据表'
            ,page:true
            ,toolbar: `<div><button type="button" onclick="window.location.href='<@com.path/>/admin/contest/create'"  class="layui-btn layui-btn-sm" style="width: 90px;">新建</button></div>`
            ,defaultToolbar: ['filter', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                id:'refresh',
                title: '刷新'
                ,layEvent: 'REFRESH'
                ,icon: 'layui-icon-refresh'
            }]
            ,limits:[5,10,20]
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
                    "count": res.data.total, //解析数据长度
                    "data": res.data.records //解析数据列表
                };
            }
            ,done: function (res, curr, count) {
                $("table").width("100%");
            }
            ,cols: [[
                {field:'contestId', title:'编号', width:100, sort: true}
                ,{field:'contestName', title:'标题', width:210}
                ,{field:'contestStart', title:'开始时间', width:110,sort: true,
                    templet: function(res){
                        return layui.util.toDateString(res.contestStart, "yyyy-MM-dd");}}
                ,{field:'contestEnd', title:'结束时间', width:110,sort: true,
                    templet: function(res){
                        return layui.util.toDateString(res.contestEnd, "yyyy-MM-dd");}}
                ,{field:'enclosureName', title:'附件名称', width:130}
                ,{field:'state', title:'状态', width:100}
                ,{title:'操作', toolbar: '#barDemo', width:150}
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
                        url:'/admin/contest/delete',
                        data:{'contestId':data.contestId},
                        success:function (data){
                            console.log(data.status);
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
                $("a.layui-btn-normal").attr("href","/admin/contest/update/"+data.contestId);
            }
        });
    });
</script>
</body>

</html>