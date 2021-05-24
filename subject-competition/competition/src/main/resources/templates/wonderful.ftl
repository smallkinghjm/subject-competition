<!DOCTYPE html>
<html>
<head>
    <title>赛事总结</title>
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
        img{
            width: 300px;
            height: 200px;
        }

        #box1{
            width: 300px;
            height: 250px;
        }
    </style>
</head>
<body>
<div><@com.nav/></div>
<div class="layui-container" style="background-color: #ffffff;">
    <div>
        <span class="layui-breadcrumb" lay-separator=">>">
            <p style="height: 20px; padding-top: 10px;">当前位置：<a href="<@com.path/>/home">首页</a>
            <a><cite>赛事风采</cite></a>
            </p>
        </span>
    </div>
    <div class="layui-container" style="background-color: #ffffff;">
        <#if (wonderfuls)??>
            <#if (wonderfuls?size>0)>
                <#list wonderfuls as wonderful>
                    <div class="layui-col-md4">
                        <div id="box1" style="margin-top: 30px; margin-left-left: 30px;">
                            <a href="<@com.path/>/wonderful/${wonderful.eventId}"><img  src="${wonderful.enclosureContent}"></a>
                            ${wonderful.title}
                        </div>
                    </div>
                </#list>
            </#if>
        </#if>
    </div>

</div>
</body>
</html>