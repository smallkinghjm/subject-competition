<!DOCTYPE html>
<html>
<head>
    <#if type==0>
        <title>赛事公告</title>
        <#elseif type==1>
            <title>赛果发布</title>
    </#if>
    <#import "../common.ftl" as com>
    <@com.depend/>
</head>
<body>
<div class="layui-container">
    <@com.nav2/>
    <@com.tree/>
</div>

<script type="text/html" id="barDemo">
    <#if type==2>
        <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="look">查看</a>
        <#else>
        <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
    </#if>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script>
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#test'
            ,url:'/admin/events?type=${type}'
            <#if type==0>
            ,title: '通知公告数据表'
                <#elseif type==1>
            ,title: '比赛结果数据表'
                <#else >
            ,title: '赛事总结数据表'
            </#if>
            <#if type!=2>
            ,toolbar: `<div><button type="button" onclick="window.location.href='<@com.path/>/admin/event/create'"  class="layui-btn layui-btn-sm layui-btn-primary layui-border-orange" style="width: 90px;">新建</button></div>`
            ,defaultToolbar: ['filter', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                id:'refresh',
                title: '刷新'
                ,layEvent: 'REFRESH'
                ,icon: 'layui-icon-refresh'
            }]
            </#if>

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
                    "data": res.data.events, //解析数据列表
                    "count":res.data.total,
                    "page":res.data.pages,
                };
            }
            ,done: function (res, curr, count) {
                $("table").width("100%");
            }
            ,cols: [[
                {field:'eventId', title:'编号',width:"15%", sort: true,id:'eventId'}
                ,{field:'title', title:'标题',width:"20%" }
                ,{field:'createTime', title:'创建时间',width:"19%",
                    templet: function(res){
                        return layui.util.toDateString(res.createTime, "yyyy-MM-dd HH:mm:ss");}}
                ,{field:'updateTime', title:'修改时间',width:"19%",
                    templet: function(res){
                        return layui.util.toDateString(res.updateTime, "yyyy-MM-dd HH:mm:ss");}}
                ,{field:'enclosureName', title:'附件名称',width:"12%"}
                ,{fixed:'right',title:'操作',width:"15%", toolbar: '#barDemo'}
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
                            url:'/admin/event/delete',
                            data:{'eventId':data.eventId},
                            success:function (data){
                                if (data.status=='success'){
                                    alert('删除成功');
                                    window.location.reload();
                                }else {
                                    alert('失败，原因:'+data.data);
                                }
                            },
                            error:function (data){
                                alert(data.data);
                            }
                        }
                    );
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                //点击编辑按钮实现跳转编辑页面
                $("a.layui-btn-normal").attr("href","/admin/event/update/"+data.eventId);
            }else{
                layer.msg('敬请期待', {icon: 6});
            }
        });
    });
</script>
</body>

</html>