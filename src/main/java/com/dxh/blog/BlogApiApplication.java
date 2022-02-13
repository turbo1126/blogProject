package com.dxh.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BlogApiApplication {

    public static void main(String[] args) {
//其他优化
//        文章可以放入es当中，便于后续中文分词搜索。springboot教程有和es的整合
//        评论数据，可以考虑放入mongodb当中 电商系统当中 评论数据放入mongo中
//        阅读数和评论数 ，考虑把阅读数和评论数 增加的时候 放入redis incr自增，使用定时任务 定时把数据固话到数据库当中
//        为了加快访问速度，部署的时候，可以把图片，js，css等放入七牛云存储中，加快网站访问速度
//        做一个后台 用springsecurity 做一个权限系统，对工作帮助比较大
//        将域名注册，备案，部署相关
        SpringApplication.run(BlogApiApplication.class, args);
    }

}
