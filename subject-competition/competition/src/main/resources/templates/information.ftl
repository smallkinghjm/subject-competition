<!DOCTYPE html>
<html>
<head>
    <meta charset= "utf-8" >
    <title>个人信息</title>
    <#import "common.ftl" as com>
    <@com.depend/>
    <@com.style/>
    <style>
        .layui-form-label{
            width: unset;
        }
        .ax-admin nav {
            width: 350px;
        }
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
                <a><cite>个人信息</cite></a>
            </p>
        </span>
        </div>
    </div>
    <div class="layui-container">
        <div class="ax-flex-row ax-admin">
            <div>
                <nav class="ax-flex-col">
                    <a  class="ax-close-nav-all"><span class="ax-iconfont ax-icon-left"></span></a>
                    <div class="ax-flex-block ax-nav-main" style="background-color: #ffffff;">
                        <div class="layui-collapse">
                            <div class="layui-colla-item">
                                <h2 class="layui-colla-title" style="text-align: center;">个人信息</h2>
                                <div class="layui-colla-content layui-show">

                                    <label class="layui-form-label">学号:</label>
                                    <p>${user.userId}</p>

                                    <label class="layui-form-label">姓名:</label>
                                    <p>${user.userName}</p>

                                    <label class="layui-form-label">密码:</label>
                                    <p>*************</p>

                                    <label class="layui-form-label">学院:</label>
                                    <p>${user.faculty}</p>

                                    <label class="layui-form-label">班级:</label>
                                    <p>${user.classA}</p>

                                    <label class="layui-form-label">性别:</label>
                                    <#if user.gender==1>
                                        <p>男</p>
                                    <#else >
                                        <p>女</p>
                                    </#if>

                                    <label class="layui-form-label">邮箱:</label>
                                    <p>${user.email}</p>

                                    <div style="padding-left: 15px;">
                                        <a href="<@com.path/>/user/changePassword" >修改密码</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
            <div class="ax-flex-block ax-body">
                <div  style="padding-left: 20px;">
                    <h2 class="layui-colla-title" style="text-align: center;">赛事总结</h2>
                    <table class="layui-hide" id="test" lay-filter="test"></table>
                </div>
            </div>
        </div>

    </div>
    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>

    <script>
        layui.use('table', function(){
            var table = layui.table;
            table.render({
                elem: '#test'
                ,url:'/user/summary?userId=${user.userId}'
                ,title: '赛事总结数据表'
                ,toolbar: `<div><button type="button" onclick="window.location.href='<@com.path/>/user/summary/create'"  class="layui-btn layui-btn-sm layui-btn-primary layui-border-orange" style="width: 90px;">新建</button></div>`
                ,defaultToolbar: ['filter', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                    id:'refresh',
                    title: '刷新'
                    ,layEvent: 'REFRESH'
                    ,icon: 'layui-icon-refresh'
                }]
                ,limits:[5,10]
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
                        "data": res.data.summary, //解析数据列表
                        "count":res.data.total,
                        "page":res.data.pages,
                    };
                }
                ,done: function (res, curr, count) {
                    $("table").width("100%");
                }
                ,cols: [[
                    {field:'title', title:'标题', width:"30%",templet: '<div><a href="/event/{{d.eventId}}" class="layui-table-link">{{d.title}}</a></div>'}
                    ,{field:'updateTime', title:'时间', width:"20%", templet: function(res){
                            return layui.util.toDateString(res.updateTime, "yyyy-MM-dd HH:mm:ss");}}
                    ,{field:'enclosureName', width:"30%", title:'附件名称'}
                    ,{fixed:'right',title:'操作', width:"20%",toolbar: '#barDemo'}
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
                    layer.msg('敬请期待', {icon: 6});
                    /*$("a.layui-btn-normal").attr("href","/admin/event/update/"+data.eventId);*/
                }
            });
        });
    </script>


</body>

<script>
    //注意：折叠面板 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function(){
        var element = layui.element;

    });
</script>

</html>