package com.zhihuAs.service;//package com.store.syf.service;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.store.syf.dto.Sysconfig;
//import org.json.JSONArray;
//
///**
// * 测试定时任务
// */
//public class TestTask  extends BaseTask{
//    @Override
//    protected String getSubTimePath() {
//        return "lastUpdateTime/modeCardPay";
//    }
//
//    @Override
//    protected String getSubPath() {
//        return "update/updateModeCardPaylist";
//    }
//
//
//    @Override
//    protected boolean verifyTime() {
//        return false;
//    }
//
//    @Override
//    protected String getTaskName() {
//        return "测试定时任务";
//    }
//
//    @Override
//    protected JsonArray getSubData(String time) {
//        String sql = "select tax,sheet_po ,IFNULL(sheet_po,\"\") as sheet_po1,flow_id from  "+ Sysconfig.dbName+".`t_pm_sheet_detail` where other2 >'"+time+"'";
//        return this.baseDao.findBysql(sql);
//    }
//
//    @Override
//    protected void putSubPath(JSONArray dates, JsonObject data) {
//        String[] str = new String[4];
//        str[0] = data.get("tax")==null?"99":data.get("tax").getAsString();
//        str[1] = data.get("sheet_po")==null?"空的这个":data.get("sheet_po").getAsString();
//        str[2] = data.get("sheet_po1").getAsString();
//        str[3] = data.get("flow_id")==null?"99":data.get("flow_id").getAsString();
//        dates.put(str);
//    }
//}
