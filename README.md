# Mos Design Backend

- 本项目是基于Spring Boot 2.2.7开发的后台脚手架
- This project is a boilerplate for Spring Boot 2.2.7
---

## associations

project|frameworks|github address|demo address
:-:|:-:|:-:|:-:
后台|Spring Boot 2.2.5, MyBatis, Spring Security, JWT Token, MySQL5.7, Redis, etc.|[GitHub](https://github.com/hugo8680/base)|暂无
前台|React, Ant Design V4, Umi, etc.|[GitHub](https://github.com/hugo8680/base-admin)|暂无

---

## Core Frameworks
- Spring Boot 2.2.7.RELEASE
- Spring Security 2.2.7.RELEASE
- MyBatis 2.1.2
- MySQL 5.7
- Redis
- JWT Token

## Modules

- common `工具包`
- generator `代码生成应用`
- system `系统框架`
- service `服务套件`
  - authentication `授权应用`
  - admin `管理后台应用`
  - xxx `xxx应用`


## Deployment
每个应用包独立部署,可以使用docker或者其他方法减少耦合