# 个人博客系统

#### 介绍
后端springboot，mybatis-plus
前端使用vue，element-ui

技术特点:
jwt + redis
1.token令牌的登陆方式，访问认证速度快，session共享，安全性
2.redis做了令牌和用户信息的对应管理，进一步增加了安全性，登录用户做了缓存，灵活控制用户的过期等

3.使用了treadLocal保存用户信息，请求的线程之内，可以随时获取登录的用户，做了线程隔离
且使用完之后做了value的删除，防止了内存泄漏
4.线程安全-update table set value = newValue where id = 1 and value = oldValue
5.线程池 对当前主业务流程无影响的操作，放入线程池执行（登录日志）
6.统一日志处理，统一缓存处理

