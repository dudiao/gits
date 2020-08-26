# Gits 单体与微服务快速开发平台

从入行到现在，一直受益于开源项目，Spring、SpringBoot、SpringCloud、MyBatis-plus、Swagger、Hutool等等，是开源项目让技术更好的服务于业务，也让技术人更好的生活。

我们也想为开源贡献自己的力量，所以有了`Gits`：

- `git`代表记录；
- `s`既是单体的首写字母，也是复数，服务（service）的含义。

`Gits`想要做到单体与微服务架构的统一，让各位大大专注业务，少点Bug，多陪陪家人。

## 项目依赖关系

![gits-dependency.png](doc/images/gits-dependency.png)

## 技术栈

- Java 1.8
- Maven 3.5.3
- SpringBoot 2.3.1.RELEASE

## 项目介绍

- 通过Maven打包的方式，兼容单体和微服务项目；
- 深度融合SpringSecurity，通过`@EnableGitsResourceServer`注解，灵活控制服务是否被保护（后续考虑使用OAuth2）；
- 核心业务包通过springboot自动装配的特性，结合@ConditionalOnMissingBean（存在指定bean就不装配），使得客户化开发和后续升级变的简单高效。

## 项目规划

### 业务功能模块 TODO

1. 前端工程`Vue`：https://gitee.com/smileg/gits-ui
2. ~~登录：认证与授权~~
3. ~~用户模块~~
4. ~~机构管理~~
5. ~~角色权限~~
6. 数据字典
7. 参数管理
8. 数据权限

## 协议说明

`LGPL`是`GPL`的一个为主要为类库使用设计的开源协议。和GPL要求任何使用/修改/衍生之GPL类库的的软件必须采用GPL协议不同。LGPL允许商业软件通过类库引用(link)方式使用LGPL类库而不需要开源商业软件的代码。这使得采用LGPL协议的开源代码可以被商业软件作为类库引用并发布和销售。

但是如果修改LGPL协议的代码或者衍生，则所有修改的代码，涉及修改部分的额外代码和衍生的代码都必须采用LGPL协议。因此LGPL协议的开源代码很适合作为第三方类库被商业软件引用，但不适合希望以LGPL协议代码为基础，通过修改和衍生的方式做二次开发的商业软件采用。

## 用户权益

允许以引入不改源码的形式免费用于学习、毕设、公司项目、私活等。

但未经过授权和不遵循`LGPL`协议二次开源或者商业化我们将追究到底。

## 构建Dokcer镜像

以腾讯云镜像仓库为例，实现 Maven 打包 - 构建镜像 - 推送镜像到仓库。

1. 构建项目：需要在电脑上安装maven（3.5.3），在项目根目录执行
```shell script
mvn clean package
```

2. 构建gits-admin镜像
```shell script
# 登录腾讯云镜像仓库
docker login ccr.ccs.tencentyun.com -u yourusername -p yourpassword

cd gits-admin

docker build -t ccr.ccs.tencentyun.com/dudiao/gits-admin:1.0.0 .

docker push ccr.ccs.tencentyun.com/dudiao/gits-admin:1.0.0
```

3. 构建gits-single镜像
```shell script
# 登录腾讯云镜像仓库
docker login ccr.ccs.tencentyun.com -u yourusername -p yourpassword

cd gits-server/gits-single

docker build -t ccr.ccs.tencentyun.com/dudiao/gits-single:1.0.0 .

docker push ccr.ccs.tencentyun.com/dudiao/gits-single:1.0.0
```

## 关于

可以关注我的微信公众号

![读钓的YY](doc/images/weixin-mp.png)