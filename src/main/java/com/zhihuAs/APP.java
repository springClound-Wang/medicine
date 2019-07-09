package com.zhihuAs;

import com.zhihuAs.dto.Sysconfig;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.Properties;

/**
 * @PAGENAME com.zhihuAs
 * @PROJECTNAME demo
 * @DESCRIPTION
 * @AOUTH Jeff
 * @createtime 2019/6/2823:05
 * @QQ 1142013568
 */
@SpringBootApplication
public class APP {
    protected static Logger logger = Logger.getLogger(APP.class);


    public static void main(String[] args) {

        if(wrongParam(args)){
            return;
        }
        SpringApplication.run(APP.class,args);

    }

    /**
     * 校验启动参数是否有误
     * @param args
     * @return*/
    static boolean  wrongParam(String [] args){
        String applicationPro = System.getProperty("user.dir")+"/configs/param.properties";//先读取config目录的，没有再加载classpath的
        logger.info("************加载applicationPro文件："+ JSON.toJSONString(applicationPro));
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(new File(applicationPro));
            properties.load(in);
        } catch (FileNotFoundException e) {
            logger.error("************ 【application.properties配置文件不存在】 ************");
            logger.error("************ 【application.properties配置文件不存在】 ************");
            logger.error("************ 【application.properties配置文件不存在】 ************");
            return true;
        } catch (IOException e) {
            logger.error("************ 【application.properties配置文件加载失败】 ************");
            logger.error("************ 【application.properties配置文件加载失败】 ************");
            logger.error("************ 【application.properties配置文件加载失败】 ************");
            return true;
        }
        Sysconfig.upRealmName=properties.getProperty("upRealmName");
        logger.info("************配置文件upRealmName请求地址为："+properties.getProperty("upRealmName"));
        logger.info("************Sysconfig文件upRealmName请求地址为："+ Sysconfig.upRealmName);

        if(StringUtils.isBlank(Sysconfig.upRealmName)){
            logger.error("************ 【upRealmName值为空】 ************");
            logger.error("************ 【upRealmName值为空】 ************");
            logger.error("************ 【upRealmName值为空】 ************");
            return true;
        }

        if(args==null || args.length !=2){
            logger.error("************ 【请输入数据库名与token】 ************");
            logger.error("************ 【请输入数据库名与token】 ************");
            logger.error("************ 【请输入数据库名与token】 ************");
            return true;
        }
        logger.info("************########### 【开始初始化配置信息】 ###########************");
        logger.info("*** ### 数据库名为:【"+args[0]+"】 ### ***");
        logger.info("*** ### token为:【"+args[1]+"】 ### ***");
        Sysconfig.dbName=args[0];
        Sysconfig.actionToken=args[1];
        logger.info("*** ### Sysconfig.dbName为:【"+Sysconfig.dbName+"】 ### ***");
        logger.info("*** ### Sysconfig.actionToken:【"+Sysconfig.actionToken+"】 ### ***");
        logger.info("************########### 【初始化配置信息完毕】 ###########************");
        return false;
    }

}
