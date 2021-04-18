系统基本流程及主要信息
1.创建项目，导入依赖
2.创建数据库及表
3.编写CodeGeneratorUtil（Mybatis Plus代码生成器），生成对应的controller,entity,mapper,service层，实现MVC架构
4.定义通用的返回信息，CommonReturnType.java用于处理基本的





问题
1.layui无法实现表单失去焦点检验用户名是否存在，只能通过提交按钮触发
2.附件上传，前端搞个选择按钮，1是，0否，0传递个初始值，后端判定值相等则放弃插入附件