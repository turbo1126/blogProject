# blogProject
基于SpringBoot+Vue开发的个人博客项目

项目使用的技术：
SpringBoot MyBatisPlus Redis MySQL ThreadLocal JWT

### 总结
1.jwt+redis
  token令牌的登录方式，访问认证速度快
  redis做了令牌和用户信息的共享管理 1.增加了安全性 2.登录用户做了缓存 
2.threadLocal保存用户信息，请求的线程内随时获取登录的用户，线程隔离
3.SpringSecurity实现 管理员用户权限管理
4.使用了线程池 实现文章浏览量的异步更新，提升用户体验
5.一些常用功能加上了缓存处理 重复访问直接从redis中获取数据，避免频繁查询数据库
6.用Aspect实现了 缓存和记录日志
7.线程安全 update table set value =newValue where id=1 and value=oldValue
