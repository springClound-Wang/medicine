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

        String sql = "select * from (SELECT card.card_id,\n" +
                "            branch_no ,     card.vip_name,\n" +
                "       card.vip_tel ,(select  DATE_FORMAT(oper_date, '%Y-%m-%d %H:%i:%s') as oper_date from "+Sysconfig.dbName+".sa_t_alloper_log a where log_flag = 'H' and a.vip_no=card.card_id ORDER BY a.oper_date DESC limit 1) optime\n" +
                "            FROM "+Sysconfig.dbName+".pos_t_vip_info card ) b where b.optime>'"+time+"'";

        logger.info("会员执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[5];
        str[0] = data.get("card_id").getAsString();
        str[1] = data.get("vip_name")==null?"":data.get("vip_name").getAsString();
        str[2] = data.get("vip_tel")==null?"":data.get("vip_tel").getAsString();
        str[3] = data.get("optime")==null?"":data.get("optime").getAsString();
        str[4] = data.get("branch_no")==null?"All":data.get("branch_no").getAsString();

        dates.put(str);
    }
}
