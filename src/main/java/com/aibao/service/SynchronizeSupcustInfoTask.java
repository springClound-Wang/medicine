package com.aibao.service;

import com.aibao.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
*
 * 供应商

*/


public class SynchronizeSupcustInfoTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizeSupcustInfoTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdateTime/modeSupcustInfo";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModeSupcustInfo";
    }

    @Override
    protected String getTaskName() {
        return "供应商";
    }


    @Override
    protected JsonArray getSubData(String time) {
        String sql = "select  supcust_no,sup_name,sup_man,sup_addr from "+ Sysconfig.dbName+".bi_t_supcust_info";
        logger.info("供应商执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[4];
        str[0] = data.get("supcust_no").getAsString();
        str[1] = data.get("sup_name")==null?"":data.get("sup_name").getAsString();
        str[2] = data.get("sup_man")==null?"":data.get("sup_man").getAsString();
        str[3] = data.get("sup_addr")==null?"":data.get("sup_addr").getAsString();
        dates.put(str);
    }
}
