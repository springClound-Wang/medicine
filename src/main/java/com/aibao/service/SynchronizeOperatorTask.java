package com.aibao.service;

import com.aibao.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 收银人
 */

public class SynchronizeOperatorTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizeOperatorTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdateTime/modeCashier";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModeCashier";
    }

    @Override
    protected String getTaskName() {
        return "收银人";
    }


    @Override
    protected JsonArray getSubData(String time) {

         String sql = "select cashier_id, cashier_name,com_branch  from "+ Sysconfig.dbName+".pos_t_cashier where cashier_status='正常'";
        logger.info("收银人执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[3];
        str[0] = data.get("cashier_id").getAsString();
        str[1] = data.get("cashier_name")==null?"":data.get("cashier_name").getAsString();
        str[2] = data.get("com_branch")==null?"All":data.get("com_branch").getAsString();

        dates.put(str);
    }
}
