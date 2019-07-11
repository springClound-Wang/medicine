package com.zhihuAs.service;

import com.zhihuAs.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;

/*
*
 * 会员充值


*/

public class SynchronizeSavingPlusTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizeSavingPlusTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdateTime/modeRechargeRecord";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModeRechargeRecord";
    }

    @Override
    protected String getTaskName() {
        return "会员充值";
    }


    @Override
    protected JsonArray getSubData(String time) {
        String sql = "SELECT flow_id,plus_count,oper_date ,branch_no FROM "+Sysconfig.dbName+".t_rm_saving_plus_record WHERE oper_date>'"+time+"'";

        logger.info("会员充值执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[4];
        str[0] = data.get("flow_id")==null?"":data.get("flow_id").getAsString();
        str[1] = data.get("plus_count")==null?"0":data.get("plus_count").getAsString();
        str[2] = data.get("oper_date")==null?"":data.get("oper_date").getAsString();
        str[3] = data.get("branch_no")==null?"All":data.get("branch_no").getAsString();

        dates.put(str);
    }
}
