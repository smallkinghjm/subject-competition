<!DOCTYPE html>
<html>
<head>
    <title>${wonderful.title}</title>
    <#import "common.ftl" as com>
    <@com.depend/>
    <@com.style/>
</head>
<body >
<div><@com.nav/></div>
<div class="layui-container">
        <span class="layui-breadcrumb" lay-separator=">>">
            <p style="margin-top: 10px;">当前位置：<a href="<@com.path/>/home">首页</a>
            <a href="<@com.path/>/wonderfuls?type=3">赛事风采</a>
          <a><cite>内容</cite></a>
            </p>
        </span>
    <div style="padding-top: 40px">
        ${wonderful.content};
    </div>
</div>
<div>
    <@com.footer/>
</div>
</body>
</html>


