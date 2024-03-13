package com.example;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

public class Generator {
    public static void main(String[] args) {
//        创建代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
//        配置数据库
        DataSourceConfig dataSource = new DataSourceConfig();
        dataSource.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///catering_sharing_system?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        autoGenerator.setDataSource(dataSource);

//        设置全局配置
        GlobalConfig globalConfig = new GlobalConfig();
//        设置文件生成目录
        System.out.println("1111" + System.getProperty("user.dir"));
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
//        设置完毕后是否打开生成代码所在的目录
        globalConfig.setOpen(false);
//        设置作者
        globalConfig.setAuthor("尹洪运");
//        设置是否覆盖原始生成的文件
        globalConfig.setFileOverride(false);
//        设置数据层接口名，%为占位符，指代模块名称
        globalConfig.setMapperName("%sDao");
//        设置Id生成策略
        globalConfig.setIdType(IdType.ASSIGN_ID);
        autoGenerator.setGlobalConfig(globalConfig);

//        设置包名相关配置
        PackageConfig packageConfig = new PackageConfig();
//        设置生成的包名，与代码所在位置不冲突，二者叠加组成完整路径
        packageConfig.setParent("com.example");
//        设置实体类包名
        packageConfig.setEntity("domain");
//        设置数据层包名
        packageConfig.setMapper("dao");
        autoGenerator.setPackageInfo(packageConfig);

//       策略设置
        StrategyConfig strategyConfig = new StrategyConfig();
//        strategyConfig.setInclude("user");//设置当前参与生成的表名，参数为可变参数
//        strategyConfig.setTablePrefix("");//设置数据库表的前缀名称，模块名=数据库表名-前缀名
        strategyConfig.setRestControllerStyle(true);//设置是否启用rest风格
//        strategyConfig.setVersionFieldName("version");//设置乐观锁字段名
        strategyConfig.setLogicDeleteFieldName("isdel");//设置逻辑删除字段名
        strategyConfig.setEntityLombokModel(true);//设置是否启用lombok
        autoGenerator.setStrategy(strategyConfig);

//        执行代码生成器
        autoGenerator.execute();
    }
}
