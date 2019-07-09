package com.zhihuAs.dto;


/**
 * @类名 Sysconfig
 * @描述 TODO
 * @创建者 wzz
 * @时间 19-06-23 下午19:34
 * @版本 1.0
 **/
public class Sysconfig {

    public static String loginTokenKey = "loginToken";
    // TODO 域名待确认
   // @Value("${upRealmName}")
    public static String upRealmName; //域名

    //public static String upRealmName = "39.96.78.91/gd-data-up/"; //域名
    // TODO 数据库名
   // @Value("${dbName}")
    public static String dbName ;

    //public static String dbName = "gd_subdb_1ehgcsg";
   // @Value("${actionToken}")
    public static String actionToken ;

    //public static String actionToken = "2157BC59A64170526AB0742A4FF053EC08E941F2453FEDBFCF9D4097F1BDBF4A17FEE8450E0F825949E2A5B9ACA90FF4E77338EE48F22B45B14CC6BDD5721EDF";

}
