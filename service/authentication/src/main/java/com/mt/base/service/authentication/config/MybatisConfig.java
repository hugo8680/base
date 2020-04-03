package com.mt.base.service.authentication.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;


@Configuration
@MapperScan({"com.mt.base.service.authentication.mapper"})
public class MybatisConfig {
}