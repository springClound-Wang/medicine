package com.aibao.service;

import com.aibao.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;

/*
*
 * 营业员

*/

public class SynchronizeSaleManTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizeSaleManTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdate/getModeSaleManLastSaleId";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModeSaleMan";
    }

    @Override
    protected String getTaskName() {
        return "营业员";
    }


    @Override
    protected JsonArray getSubData(String time) {
        String sql = "SELECT  cler_no,cler_name    FROM "+ Sysconfig.dbName+".pos_t_cler_info";
        logger.info("营业员执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[2];
        str[0] = data.get("cler_no").getAsString();
        str[1] = data.get("cler_name")==null?"":data.get("cler_name").getAsString();
        dates.put(str);
    }
}
