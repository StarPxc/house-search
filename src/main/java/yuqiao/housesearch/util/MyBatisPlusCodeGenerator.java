package yuqiao.housesearch.util;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * @author 浦希成
 * 2018/11/1
 */
public class MyBatisPlusCodeGenerator {


    /**
     * 父包名
     */
    private static String PARENT_PACKAGE;
    /**
     * 父包路径
     */
    private static String PARENT_DIR;
    /**
     * 模块名
     */
    private static String MODEL_NAME;
    /**
     * 表名
     */
    private static String TABLE_NAME;
    /**
     * 数据库名
     */
    private static String DATABASE_NAME;
    /**
     * 用户名
     */
    private static String USERNAME;
    /**
     * 密码
     */
    private static String PASSWORD;

    static {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("/Users/pxc/Documents/java/house-search/search/src/main/resources/mybatis-plus.properties"));
            PARENT_PACKAGE = p.getProperty("parent.package");
            PARENT_DIR = p.getProperty("parent.dir");
            MODEL_NAME = p.getProperty("model.name");
            TABLE_NAME = p.getProperty("table.name");
            DATABASE_NAME = p.getProperty("database.name");
            USERNAME = p.getProperty("username");
            PASSWORD = p.getProperty("password");
            System.out.println(PARENT_PACKAGE);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        final String projectPath = System.getProperty("user.dir");
        //生成基础的BaseResultMap
        gc.setBaseResultMap(true);
        // XML columnList
        gc.setBaseColumnList(true);
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("浦希成");
        gc.setOpen(false);
        //配置swagger
        gc.setSwagger2(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        mpg.setDataSource(dsc);

        // 包配置
        final PackageConfig pc = new PackageConfig();
        pc.setModuleName(MODEL_NAME);
        pc.setParent(PARENT_PACKAGE);
        //pc.setService(TABLE_NAME+"Service");接口名设置
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/java/" + PARENT_DIR + pc.getModuleName() + "/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();

        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //LombokModel
        strategy.setEntityLombokModel(true);
        //生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        //数据库名
        strategy.setInclude(TABLE_NAME);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


}
