package com.mt.base.generator.controller;

import com.mt.base.common.response.JsonResponse;
import com.mt.base.generator.entity.GenerateParam;
import com.mt.base.generator.service.GeneratorService;
import org.apache.commons.configuration.ConfigurationException;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;


/**
 * Created by HUGO on 2019/02/28.
 */
@RestController
@RequestMapping("/api")
public class GeneratorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorController.class);

    private GeneratorService generatorService;

    public GeneratorController(GeneratorService service) {
        this.generatorService = service;
    }

    /**
     * 数据库表列表
     */
    @GetMapping(value = "/tables")
    public JsonResponse tables(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false) String tableName) {
        return JsonResponse.success("获取成功", this.generatorService.selectTables(tableName, page, pageSize));
    }

    /**
     * 获取所有生成记录
     *
     * @return 所有生成代码记录
     */
    @GetMapping(value = "/records")
    public JsonResponse selectRecords(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false) String tableName) {
        return JsonResponse.success("获取成功", this.generatorService.selectRecords(tableName, page, pageSize));
    }

    /**
     * 生成代码
     */
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public JsonResponse code(@RequestBody @Valid GenerateParam param) {
        try {
            this.generatorService.generateCode(param);
            return JsonResponse.success("生成代码成功");
        } catch (IOException | NullPointerException | XMLParserException | InvalidConfigurationException | InterruptedException | SQLException | ConfigurationException e) {
            LOGGER.error(e.getCause() + "\n" + e.getMessage());
            return JsonResponse.error(e.getMessage());
        }
    }
}
