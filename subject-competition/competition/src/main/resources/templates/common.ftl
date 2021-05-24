<!DOCTYPE html>
<html>
<head>
    <!--项目路径-->
    <#macro path>${springMacroRequestContext.contextPath}</#macro>
    <!--依赖-->
    <!--纸屑插件-->
    <script src="<@path/>/js/confetti.js"></script>

    <!--登录页面动态背景插件-->
    <script src="<@path/>/js/particles.min.js"></script>
    <script src="<@path/>/js/app.js"></script>

    <#macro depend>
        <meta charset="utf-8">
        <!--layui-->
        <link rel="stylesheet" href="<@path/>/layui/css/layui.css"  type="text/css" >
        <link rel="stylesheet" media="screen" href="<@path/>/css/style.css">
        <script type="text/javascript" src="<@path/>/layui/layui.js"></script>
        <script type="text/javascript" src="<@path/>/js/jquery.min.js"></script>
        <script type="text/javascript" src="<@path/>/js/wangEditor.min.js"></script>
        <!--axui-->
        <link href="http://at.alicdn.com/t/font_1551254_rxrrzgz2kjc.css" rel="stylesheet" type="text/css" /><!--地址不定时更新，引用时请注意-->
        <link href="http://src.axui.cn/src/css/ax.css" rel="stylesheet" type="text/css" >
        <script src="http://src.axui.cn/src/js/jquery-1.10.2.min.js" type="text/javascript"></script>
        <script src="http://src.axui.cn/src/js/ax.min.js" type="text/javascript"></script>
        <link href="<@path/>/axui/css/ax-admin01.css" rel="stylesheet" type="text/css" />
        <script src="<@path/>/axui/js/ax-admin01.min.js" type="text/javascript"></script>
    </#macro>

    <#macro style>
        <style>
            .footer{
                height:30px;
                width: max-content;
                position:relative;
                bottom:5px;
                margin: 10px 40%;
                text-align: center;
            }

            .layui-laypage .layui-laypage-curr .layui-laypage-em{
                background-color: #fa6a01;
            }
        </style>
    </#macro>


</head>
<body>
    <#macro nav>
    <div class="layui-container">

        <img src="<@path/>/images/g.jpg" style="width: 100%;height: 100%; vertical-align: middle;"/>
        <ul class="layui-nav layui-bg-red">
            <div class="layui-row">
                <div class="layui-col-md-offset5">
                    <li class="layui-nav-item"><a href="<@path/>/notice">通知公告</a></li>
                    <li class="layui-nav-item"><a href="<@path/>/contest">赛事项目</a></li>
                    <li class="layui-nav-item"><a href="<@path/>/achievement">比赛结果</a></li>
                    <li class="layui-nav-item"><a href="<@path/>/wonderfuls?type=3">赛事风采</a></li>
                    <li class="layui-nav-item"><a href="<@path/>/summary">赛事总结</a></li>
                    <li class="layui-nav-item">
                        <a href="">个人中心</a>
                        <dl class="layui-nav-child" style="z-index: 10002!important;">
                            <dd><a href="<@path/>/enroll/view">已报赛事</a></dd>
                            <dd><a href="<@path/>/user/information">个人信息</a></dd>
                            <dd><a href="<@path/>/user/exit">退出登录</a></dd>
                        </dl>
                    </li>
                </div>
            </div>
        </ul>

    </div>
    </#macro>

    <div>
        <#macro nav2>
            <img src="<@path/>/images/g.jpg" style="width: 100%;vertical-align: middle;"/>
            <ul class="layui-nav layui-bg-red">
                <div class="layui-col-md-offset9" style="padding-left: 30px;">
                    <li class="layui-nav-item">
                        <a href="">个人中心</a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="<@path/>/user/exit">退出登录</a>
                    </li>

                </div>
            </ul>
        </#macro>
    </div>
    <#macro editorContent>
        <div id="editor">内&nbsp&nbsp&nbsp&nbsp容</div>
    </#macro>
    <div>
        <#macro admin>
            <ul class="layui-nav layui-nav-tree ax-menu ax-menu-dark" id="menu-basic" >
                <li class="layui-nav-item">
                    <a href="<@path/>/admin/event?type=0">公告管理</a>
                </li>
                <li class="layui-nav-item">
                    <a href="<@path/>/admin/contest">项目管理</a>
                </li>
                <li class="layui-nav-item">
                    <a href="<@path/>/admin/event?type=1">赛果管理</a>
                </li>
                <li class="layui-nav-item">
                    <a href="<@path/>/admin/event?type=2">总结管理</a>
                </li>
                <li class="layui-nav-item">
                    <a href="<@path/>/admin/wonderful">风采管理</a>
                </li>
                <li class="layui-nav-item">
                    <a href="<@path/>/admin/enroll">参赛管理</a>
                </li>
                <li class="layui-nav-item">
                    <a href="<@path/>/admin/user">用户管理</a>
                </li>
            </ul>
        </#macro>
    </div>

    <div>
        <#macro tree>
            <div class="ax-flex-row ax-admin">
                <div>
                    <nav class="ax-flex-col">
                        <a  class="ax-close-nav-all"><span class="ax-iconfont ax-icon-left"></span></a>
                        <div class="ax-nav-header">
                            <a  class="ax-close-nav ax-iconfont ax-icon-menu-fold"></a>
                        </div>
                        <div class="ax-flex-block ax-nav-main">
                            <@admin/>
                        </div>
                    </nav>
                </div>
                <div class="ax-flex-block ax-body">
                    <div class="ax-flex-col" style="padding-left: 20px;">
                        <table class="layui-hide" id="test" lay-filter="test"></table>
                    </div>
                </div>
            </div>
        </#macro>
    </div>

    <div>
        <#macro footer>
            <div class="footer">
        <span class="layui-breadcrumb" lay-separator="|">
          <a href="">关于我们</a>
          <a href="">问题反馈</a>
          <a href="">帮助中心</a>
        </span>
            </div>
        </#macro>
    </div>
<#macro dateFormat dateTime>
    ${dateTime.year?c}-${dateTime.monthValue}-${dateTime.dayOfMonth}
</#macro>
</body>

<#macro wangEditor>
    <script type="text/javascript">
        $(function () {
            var E = window.wangEditor
            var editor = new E('#editor')

            editor.config.uploadImgServer = '/file/upload'
            editor.config.uploadFileName = 'file'
            editor.config.uploadImgHooks = {
                customInsert: function (insertImg, result, editor) {
                    var url =result.data;//获取后台返回的url
                    insertImg(url);
                }
            };
            //将编辑器内容绑定
            var $editor= $('#editor')
            editor.config.onchange = function (html) {
                // 监控变化，同步更新到 textarea
                $editor.val(html)
            };
            editor.create();
/*            let eds = document.getElementsByClassName('w-e-text-container');
            eds[0].style = eds[0].style.cssText + 'height: 450px';*/
            $editor.val(editor.txt.html());
        })
    </script>
</#macro>


</html>