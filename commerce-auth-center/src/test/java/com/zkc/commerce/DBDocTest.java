package com.zkc.commerce;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 数据库表文档生成
 */
@Slf4j
@SpringBootTest
public class DBDocTest {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void buildDBDoc() {
		DataSource dataSource = applicationContext.getBean(DataSource.class);
		//文档配置
		EngineConfig engineConfig = EngineConfig.builder()
				//生成文件的路径
				.fileOutputDir("D:\\Java\\ideaProjects\\commerce-ms")
				//不自动打开
				.openOutputDir(false)
				//文件类型
				.fileType(EngineFileType.HTML)
				.produceType(EngineTemplateType.freemarker)
				.build();
		//screw配置 生成文档配置 包含自定义版本号 描述等
		Configuration configuration = Configuration.builder()
				.version("1.0.0")
				.description("数据库文档")
				.dataSource(dataSource)
				.engineConfig(engineConfig)
				.produceConfig(getProuceConfig())
				.build();
		
		//执行得到 数据库名_des_version.html
		new DocumentationExecute(configuration).execute();
	}
	
	/**
	 * 配置想要生成文档的数据表 或 忽略某些表
	 */
	private ProcessConfig getProuceConfig() {
		List<String> ignoreTableName = Collections.singletonList(
				"undo_log"
		);
		List<String> ignorePrefix = Arrays.asList("a", "b");
		List<String> ignoreSuffix = Arrays.asList("_test", "_Test");
		
		return ProcessConfig.builder()
				//根据名称指定要生成的表
				.designatedTableName(Collections.emptyList())
				//根据前缀指定要生成的表
				.designatedTablePrefix(Collections.emptyList())
				//根据后缀指定要生成的表
				.designatedTableSuffix(Collections.emptyList())
				//根据名称忽略表
				.ignoreTableName(ignoreTableName)
				//根据前缀忽略表
				.ignoreTablePrefix(ignorePrefix)
				//根据后缀忽略表
				.ignoreTableSuffix(ignoreSuffix)
				.build();
	}
}
