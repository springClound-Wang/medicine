package com.zhihuAs.service;

import com.zhihuAs.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;

/*
*
 * 会员


*/

public class SynchronizeVipInfoTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizeVipInfoTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdateTime/modeVipInfo";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModeVipInfo";
    }

    @Override
    protected String getTaskName() {
        return "会员";
    }


    @Override
    protected JsonArray getSubData(String time) {

        String sql = "select card_id,vip_name,mobile,DATE_FORMAT(oper_date, '%Y-%m-%d %H:%i:%s') as oper_date,vip_branch_no  FROM "+Sysconfig.dbName+".t_rm_vip_info  where oper_date>'"+time+"'";

        logger.info("会员执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[5];
        str[0] = data.get("card_id").getAsString();
        str[1] = data.get("vip_name")==null?"":data.get("vip_name").getAsString();
        str[2] = data.get("mobile")==null?"":data.get("mobile").getAsString();
        str[3] = data.get("oper_date")==null?"":data.get("oper_date").getAsString();
        str[4] = data.get("vip_branch_no")==null?"All":data.get("vip_branch_no").getAsString();

        dates.put(str);
    }
}
