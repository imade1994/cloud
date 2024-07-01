
package com.tj.cloud.util;

import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.tj.cloud.constant.CommonConstant;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/19
 * @Description:
 * @version:1.0
 */
public class FreemarkerTemplateEngine extends AbstractTemplateEngine {

	private File basePath;

	private Configuration configuration;

	public FreemarkerTemplateEngine() {
	}

	public FreemarkerTemplateEngine(File basePath) {
		this.basePath = basePath;
	}

	@Override
	public FreemarkerTemplateEngine init(ConfigBuilder configBuilder) {
		super.setConfigBuilder(configBuilder);
		this.configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		this.configuration.setDefaultEncoding(ConstVal.UTF8);
		// 自定义路径
		try {
			this.configuration.setDirectoryForTemplateLoading(basePath);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		// this.configuration.setClassForTemplateLoading(FreemarkerTemplateEngine.class,
		// "/"); // 默认为Resources目录下
		return this;
	}

	@Override
	public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
		Template template = this.configuration.getTemplate(templatePath);
		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
		Throwable var6 = null;

		try {
			template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
		}
		catch (Throwable var15) {
			var6 = var15;
			throw var15;
		}
		finally {
			if (var6 != null) {
				try {
					fileOutputStream.close();
				}
				catch (Throwable var14) {
					var6.addSuppressed(var14);
				}
			}
			else {
				fileOutputStream.close();
			}

		}

		logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
	}

	@Override
	public String templateFilePath(String filePath) {
		return filePath + CommonConstant.SETTING_FTL_FILE_EXTENSION;
	}

}
