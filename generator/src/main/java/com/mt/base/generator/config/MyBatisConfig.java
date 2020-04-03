package com.mt.base.generator.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.mt.base.generator.mapper")
public class MyBatisConfig {

}
