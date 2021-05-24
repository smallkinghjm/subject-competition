<!DOCTYPE html>
<html>
<head>
    <title>${eventVO.title}</title>
    <#import "common.ftl" as com>
    <@com.depend/>
    <@com.style/>
</head>
<body >
<div><@com.nav/></div>
<div class="layui-container">
        <span class="layui-breadcrumb" lay-separator=">>">
            <p style="margin-top: 10px;">当前位置：<a href="<@com.path/>/home">首页</a>
        <#if eventVO.type==0>
            <a href="<@com.path/>/notice">通知公告</a>
            <#elseif eventVO.type==1>
            <a href="<@com.path/>/achievement">比赛结果</a>
        <#else >
            <a href="<@com.path/>/summary">赛事总结</a>
        </#if>
          <a><cite>内容</cite></a>
            </p>
        </span>
    <div style="padding-top: 40px">
        ${eventVO.content};
    </div>
    <div>
        <#if enclosure!="无附件">
            <p>
                附件：
                <a href="${enclosure.enclosureContent}">${enclosure.enclosureName}</a>
            </p>
        </#if>
    </div>
</div>
<div>
    <@com.footer/>
</div>
</body>
</html>


