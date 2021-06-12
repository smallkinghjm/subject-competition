<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${contest.contestName}</title>
    <#import "common.ftl" as com>
    <@com.depend/>
    <@com.style/>
</head>
<body >
<div>
    <@com.nav/>
</div>
<div class="layui-container">
            <span class="layui-breadcrumb" lay-separator=">>">
            <p style="margin-top: 10px;">当前位置：<a href="">首页</a>
            <a href="">赛事项目</a>
            <a><cite>赛事详情</cite></a>
            </p>
        </span>
    <div class="layui-btn-container" style="float: right;padding-top: 20px;">
        <a href="<@com.path/>/enroll/create/${contest.contestId}" class="layui-btn layui-btn-primary layui-border-orange" target="_blank" style="font-weight: bolder;">报名参赛</a>
    </div>
    <div style="padding-top: 40px">
        ${contest.contestContent};
    </div>
</div>
</body>
</html>