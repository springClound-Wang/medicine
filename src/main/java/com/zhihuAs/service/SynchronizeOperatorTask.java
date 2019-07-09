package com.zhihuAs.service;

import com.zhihuAs.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;

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

         String sql = "select cashier_id, cashier_name,branch_no  from "+ Sysconfig.dbName+".t_rm_cashier where cashier_status='正常'";
        logger.info("收银人执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[3];
        str[0] = data.get("cashier_id").getAsString();
        str[1] = data.get("cashier_name")==null?"":data.get("cashier_name").getAsString();
        str[2] = data.get("branch_no")==null?"All":data.get("branch_no").getAsString();

        dates.put(str);
    }
}
