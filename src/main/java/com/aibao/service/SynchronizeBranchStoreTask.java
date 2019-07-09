package com.aibao.service;

import com.aibao.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;

/*
*
 * 供应商

*/


public class SynchronizeBranchStoreTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizeBranchStoreTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdateTime/modeBranchInfo";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModeBranchInfo";
    }

    @Override
    protected String getTaskName() {
        return "门店";
    }


    @Override
    protected JsonArray getSubData(String time) {
        String sql = "select branch_name,branch_man,address,branch_no from "+ Sysconfig.dbName+".bi_t_branch_info";
        logger.info("门店执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[4];
        str[0] = data.get("branch_name")==null?"":data.get("branch_name").getAsString();
        str[1] = data.get("branch_man")==null?"":data.get("branch_man").getAsString();
        str[2] = data.get("address")==null?"":data.get("address").getAsString();
        str[3] = data.get("branch_no")==null?"All":data.get("branch_no").getAsString();

        dates.put(str);
    }
}
