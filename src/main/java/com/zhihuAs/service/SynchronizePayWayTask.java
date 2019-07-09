package com.zhihuAs.service;

import com.zhihuAs.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;

/*
*
 * 付款方式

*/


public class SynchronizePayWayTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizePayWayTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdate/getModePaymentInfoPayWay";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModePaymentInfo";
    }

    @Override
    protected String getTaskName() {
        return "付款方式";
    }


    @Override
    protected JsonArray getSubData(String time) {
        String sql = "select pay_way,pay_name from "+ Sysconfig.dbName+".t_bd_payment_info";
        logger.info("付款方式执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[2];
        str[0] = data.get("pay_way").getAsString();
        str[1] = data.get("pay_name")==null?"":data.get("pay_name").getAsString();
        dates.put(str);
    }
}
