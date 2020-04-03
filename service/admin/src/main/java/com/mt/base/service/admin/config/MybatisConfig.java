package com.mt.base.service.admin.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;


@Configuration
@MapperScan({"com.mt.base.service.admin.mapper"})
public class MybatisConfig {
}