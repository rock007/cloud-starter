
**cloud-discovery**  端口：20000  服务注册、监控管理中心（eureka）

**cloud-config** 端口：20001 服务配置管理，可以本地/svn/git方式更新（暂时没有启用）；

**cloud-rest-api** 端口：20002  通一接口平台，提供接口服务（未完成）

**cloud-gateway** 端口：20003  提供外网访问入口，url转发限制等操作

**cloud-backend-unified** 端口：20010 统一后台管理平台入口 访问数据库，提供服务方

**cloud-backend-system**  端口：20011 web方式后台功能、权限、菜单管理子系统

**cloud-front** 端口：20012 整体网站展示,对外提供访问

**cloud-cms** 端口：20013 内容管理子系统（文章、评论、接口服务）

**cloud-shop** 端口：20014 网上商城子系统（产品、分类、订单、地址），和生意记帐打通

**cloud-unified-service** 端口：20015 feign服务

**_共用组件_**
cloud-core  共用类，可引用
cloud-db    数据层

==20180522 项目名称改为  统一后台管理平台，功能包含帐号、CMS、商城、IM
==20181012 用户认证加入token,单个项目分为backend/api/front/mobile,BaseController修改
==20181017 pom.xml项目名修改


==20190316 去掉spring cloud,以完成基础功能
