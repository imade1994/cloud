/*
 * Copyright (c) 2003-2021 www.hualongxunda.com/ Inc. All rights reserved.
 * 注意：本内容仅限于深圳华龙讯达信息技术股份有限公司内部传阅，禁止外泄以及用于其他商业目的。
 */
package com.tj.cloud.controller;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.tj.cloud.service.IDynamicModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.util.Collections;

/**
 * @AUTHOR:taoJun
 * @Date:2024/6/14
 * @Description:
 * @version:1.0
 */
@RestController
@RequestMapping("/dynamic")
public class DynamicModelByteController {


    private static final String BASE_JAVA_PATH = "/src/main/java";
    private static final String BASE_RESOURCE_PATH = "/src/main/resources/mapper";
    private static final String BASE_PARENT_PATH = "com.hlxd";

    private static final String BASE_MODEL = "dynamic";

    private static final String entity_prefix ="/entity";
    private static final String service_prefix ="/service";
    private static final String serviceImpl_prefix ="/service/impl";
    private static final String controller_prefix ="/controller";
    private static final String mapper_prefix ="/mapper";


    private static final String target_path = "/target";



    private static final Logger log = LoggerFactory.getLogger(DynamicModelByteController.class);



    private static final String ENTITY_JAVAC_OPTIONS = "javac -encoding UTF-8 -cp  .;E:\\repository\\com\\baomidou\\mybatis-plus-annotation\\3.5.2\\mybatis-plus-annotation-3.5.2.jar;E:\\repository\\io\\swagger\\swagger-annotations\\1.6.4\\swagger-annotations-1.6.4.jar $ -d #";


    private static final String MAPPER_JAVAC_OPTIONS = "javac -encoding UTF-8 -cp  .;E:\\repository\\com\\baomidou\\mybatis-plus-annotation\\3.5.2\\mybatis-plus-annotation-3.5.2.jar;E:\\repository\\com\\baomidou\\mybatis-plus-core\\3.5.1\\mybatis-plus-core-3.5.1.jar $ -d #";


    private static final String SERVICE_JAVAC_OPTIONS = "javac -encoding UTF-8 -cp .;E:\\repository\\com\\baomidou\\mybatis-plus-extension\\3.5.2\\mybatis-plus-extension-3.5.2.jar;E:\\repository\\com\\baomidou\\mybatis-plus-core\\3.5.1\\mybatis-plus-core-3.5.1.jar $ -d #";


    private static final String SERVICE_IMPL_JAVAC_OPTIONS = "javac -encoding UTF-8 -cp .;E:\\repository\\com\\baomidou\\mybatis-plus-extension\\3.5.2\\mybatis-plus-extension-3.5.2.jar;E:\\repository\\com\\baomidou\\mybatis-plus-core\\3.5.1\\mybatis-plus-core-3.5.1.jar;E:\\repository\\org\\springframework\\spring-context\\5.3.24\\spring-context-5.3.24.jar;E:\\classes $ -d #";


    private static final String CONTROLLER_JAVAC_OPTIONS = "javac -encoding UTF-8 -cp  .;E:\\repository\\org\\springframework\\spring-context\\5.3.24\\spring-context-5.3.24.jar;E:\\repository\\org\\springframework\\spring-web\\5.3.24\\spring-web-5.3.24.jar $ -d #";


    @Resource
    IDynamicModelService dynamicModelService;




    @GetMapping("/saveByte/{modelName}")
    public void saveByteByModelName(@PathVariable("modelName")String modelName) throws IOException, ClassNotFoundException {
        //codeGenerate(modelName);
        //javac("Test.java","E:\\com\\hlxd\\dynamic\\entity\\",ENTITY_JAVAC_OPTIONS,"E:\\classes");
        //javac("TestMapper.java","E:\\com\\hlxd\\dynamic\\mapper\\",MAPPER_JAVAC_OPTIONS,"E:\\classes");
        //javac("ITestService.java","E:\\com\\hlxd\\dynamic\\service\\",SERVICE_JAVAC_OPTIONS,"E:\\classes\\com\\hlxd\\dynamic\\entity\\");
        //javac("TestServiceImpl.java","E:\\com\\hlxd\\dynamic\\service\\impl\\",SERVICE_IMPL_JAVAC_OPTIONS,"E:\\classes");
        //javac("TestController.java","E:\\com\\hlxd\\dynamic\\controller\\",CONTROLLER_JAVAC_OPTIONS,"E:\\classes");
        //读取文件写入数据库
        /*ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
        //this.getClass().getClassLoader().getResource();
        File entityFile = new File("E:\\classes\\com\\hlxd\\dynamic\\entity\\Test.class");
        *//*URL url = entityFile.toURI().toURL();
        URL[] urls = new URL[]{url};
        ClassLoader classLoader = new URLClassLoader(urls);
        Class<?> cls = classLoader.loadClass("com.hlxd.dynamic.entity.Test");
        log.info("load class {} from mysql ",cls.getName());*//*
        byte[] entityBytes = getByteStream(outputStream,entityFile);
        File mapperFile = new File("E:\\classes\\com\\hlxd\\dynamic\\mapper\\TestMapper.class");
        byte[] mapperBytes = getByteStream(outputStream,mapperFile);
        // 读取 service文件
        File serviceFile = new File("E:\\classes\\com\\hlxd\\dynamic\\service\\ITestService.class");
        byte[] serviceBytes = getByteStream(outputStream,serviceFile);
        // 读取 serviceImpl文件
        File serviceImplFile = new File("E:\\classes\\com\\hlxd\\dynamic\\service\\impl\\TestServiceImpl.class");
        byte[] serviceImplBytes = getByteStream(outputStream,serviceImplFile);
        // 读取 controller文件
        File controllerFile = new File("E:\\classes\\com\\hlxd\\dynamic\\controller\\TestController.class");
        byte[] controllerBytes = getByteStream(outputStream,controllerFile);
        try{
            outputStream.close();
        }catch (Exception e){
            log.error("关闭文件流异常！"+e.getMessage());
        }
        String id = new Snowflake().nextIdStr();
        log.info("创建模板 {} ，新建ID 为{} 的 记录",modelName,id);
        DynamicModel model=DynamicModel.builder()
                .modelName("t_test").id(id)
                .entityByte(entityBytes).mapperByte(mapperBytes)
                .serviceByte(serviceBytes).serviceImplByte(serviceImplBytes).controllerByte(controllerBytes)
                .entityName("Test")
                .serviceName("TestService")
                .controllerName("TestController")
                .mapperName("TestMapper")
                .serviceImplName("TestServiceImpl")
                .build();
        dynamicModelService.save(model);*/
    }

    private byte[] getByteStream(ByteArrayOutputStream outputStream, File mapperFile) throws IOException, ClassNotFoundException {
        InputStream in;
        int length;
        in = Files.newInputStream(mapperFile.toPath());
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        byte[] mapperBuffer = new byte[1024];
        while ((length = in.read(mapperBuffer)) != -1) {
            oos.write(mapperBuffer, 0, length);
        }
        return outputStream.toByteArray();
    }


    public  void codeGenerate(String tableName){
        FastAutoGenerator.create("jdbc:mysql://10.10.103.83:2883/test?serverTimezone=GMT%2b8","root@qrcode#qrcode","Hlxd@123456")
                .globalConfig(builder -> {
                    builder.author("CodingManTJ")
                            .enableSwagger()
                            .fileOverride()
                            .outputDir("E:\\");
                })
                .packageConfig(builder -> {
                    builder.parent(BASE_PARENT_PATH)
                            .moduleName(BASE_MODEL)
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,BASE_RESOURCE_PATH));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName)
                            .addTablePrefix("t_")
                            .entityBuilder()
                            .serviceBuilder()
                            .mapperBuilder()
                            .enableBaseResultMap()
                            .controllerBuilder()
                            .enableRestStyle();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }


    public  void javac(String classPath,String opPath,String javacOpts,String otPath) throws IOException {
        String cmd = javacOpts.replace("$",opPath.concat(classPath)).replace("#",otPath);
        System.out.println("执行JAVAC命令  ".concat(cmd));
        Process process = Runtime.getRuntime().exec(cmd);

        try {
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line=bufferedReader.readLine()) != null){
                System.out.println(line);
            }
            //如果大于0，表明 编译成功，可以手工CMD编译
            int exitVal = process.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
