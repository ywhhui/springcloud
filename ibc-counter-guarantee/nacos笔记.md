#nacos 配置使用注意点

##1.不能使用原来的application.yml作为配置文件，而是新建一个bootstrap.yml作为拉取nacos的配置文件；然而，在SpringBoot中默认是不支持bootstrap.properties属性文件的。我们需要依赖spring-cloud-starter-bootstrap才可以 或者其他子包 有这个依赖

##2.spring-cloud-starter-alibaba-nacos-config nacos当配置用时候 需要引入项目中

##3.将命名空间id设置成跟命名空间名字一致（dev）

##4.多环境记得配置spring.cloud.nacos.config.namespace= dev

##5.@RefreshScope 实时自动刷新配置的注解验证