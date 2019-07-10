package com.zhihuAs.service;

import com.zhihuAs.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;
/*

*
 * 会员卡支付列表


*/

public class SynchronizeVipCardPayTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizeVipCardPayTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdateTime/modeCardPay";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModeCardPaylist";
    }

    @Override
    protected String getTaskName() {
        return "会员卡支付列表";
    }



    @Override
    protected JsonArray getSubData(String time) {
        String sql = "SELECT id,card_id,amount,DATE_FORMAT(pay_time, '%Y-%m-%d %H:%i:%s') as pay_time ,branch_no FROM " +
                Sysconfig.dbName+".t_rm_card_paylist  WHERE pay_time>'"+time+"'";


        logger.info("会员卡支付列表执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[5];
        str[0] = data.get("id")==null?"":data.get("id").getAsString();
        str[1] = data.get("card_id")==null?"":data.get("card_id").getAsString();
        str[2] = data.get("amount")==null?"":data.get("amount").getAsString();
        str[3] = data.get("pay_time")==null?"":data.get("pay_time").getAsString();
        str[4] = data.get("branch_no")==null?"All":data.get("branch_no").getAsString();

        dates.put(str);
    }
}
