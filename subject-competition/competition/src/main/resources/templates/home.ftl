<!DOCTYPE html>
<html>
<head>
    <title>主页</title>
    <#import "common.ftl" as com>
    <link rel="stylesheet" href="<@com.path/>/layui/css/layui.css"  type="text/css" >
    <link rel="stylesheet" media="screen" href="<@com.path/>/css/style.css">
    <script type="text/javascript" src="<@com.path/>/layui/layui.js"></script>
    <script type="text/javascript" src="<@com.path/>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<@com.path/>/js/wangEditor.min.js"></script>
    <@com.style/>
    <style>
        canvas {
            display: block;
            position: relative;
            zindex:1;
            pointer-events: none;
            position: fixed;top: 0;
            background-color: #ffffff;
        }
        #img{
            width: 700px;
            height: 300px;
        }
    </style>
</head>
<body>
<canvas id="canvas" width="1920" height="950"></canvas>
    <div>
        <@com.nav/>
    </div>
    <div class="layui-container" style="background-color: #ffffff;">
        <div class="layui-row">
            <div class="layui-col-sm5">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
                    <legend style="font-weight: bold;">通知公告</legend>
                </fieldset>
                <ul >
                    <#list notices as notice>
                        <li style="height: 30px;">
                            <div class="layui-row">
                                <div class="layui-col-sm9">
                                    <span class="layui-badge-dot"></span>
                                    <a href="/event/${notice.eventId}" >${notice.title}</a>
                                </div>
                                <div class="layui-col-sm3">
                                    <p><@com.dateFormat notice.updateTime/></p>
                                </div>
                            </div>
                        </li>
                    </#list>
                </ul>
            </div>
            <div class="layui-col-sm7">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;margin-left: 20px;">
                    <legend style="font-weight: bold; padding-left: 80px;">赛事风采</legend>
                </fieldset>
                <div class="layui-carousel" id="carousel">
                    <div carousel-item>
                        <#if (wonderfuls)??>
                            <#if (wonderfuls?size>0)>
                                <#list wonderfuls as wonderful>
                                    <a href="<@com.path/>/wonderful/${wonderful.eventId}"><img id="img" src="${wonderful.enclosureContent}"></a>
                                </#list>
                            </#if>
                        </#if>
                    </div>
                </div>
            </div>

            <hr class="layui-border-red">
            <div class="layui-row">
                <div class="layui-col-sm6">
                    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px; width: 80%;">
                        <legend style="font-weight: bold;">最新赛事</legend>
                    </fieldset>
                    <ul>
                        <#list contests as contest>
                            <li style="height: 30px;">
                                <div class="layui-row">
                                    <div class="layui-col-sm9">
                                        <a href="/contest/view/${contest.contestId}" >${contest.contestName}</a>
                                    </div>
                                    <div class="layui-col-sm3">
                                        <p>${contest.state}</p>
                                    </div>
                                </div>
                            </li>
                        </#list>
                    </ul>
                </div>
                <div class="layui-col-sm6">
                    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
                        <legend style="font-weight: bold;">最新赛果</legend>
                    </fieldset>
                    <ul >
                        <#list achievements as achievement>
                            <li style="height: 30px;">
                                <div class="layui-row">
                                    <div class="layui-col-sm9">
                                        <a href="/event/${achievement.eventId}" >${achievement.title}</a>
                                    </div>
                                    <div class="layui-col-sm3">
                                        <p><@com.dateFormat achievement.updateTime/></p>
                                    </div>
                                </div>
                            </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div style="bottom:5px;">
        <@com.footer/>
    </div>

<!--纸屑插件-->
<script src="../js/confetti.js"></script>

    <script>
        layui.use('carousel', function(){
            var carousel = layui.carousel;
            //建造实例
            carousel.render({
                elem: '#carousel'
                ,width: '665px' //设置容器宽度
                ,arrow: 'always' //始终显示箭头
                ,anim: 'fade' //切换动画方式,default左右
                ,interval:3000 //自动切换的时间间隔，单位：ms（毫秒），不能低于800
            });

        });
    </script>
</body>
</html>