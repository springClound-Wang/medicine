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
        String sql = "SELECT com_no,item_no,sale_money,DATE_FORMAT(oper_date, '%Y-%m-%d %H:%i:%s') as oper_date ,branch_no FROM " +
                Sysconfig.dbName+".pos_t_saleflow  WHERE oper_date>'"+time+"'";


        logger.info("会员卡支付列表执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[5];
        str[0] = data.get("com_no")==null?"":data.get("com_no").getAsString();
        str[1] = data.get("item_no")==null?"":data.get("item_no").getAsString();
        str[2] = data.get("sale_money")==null?"":data.get("sale_money").getAsString();
        str[3] = data.get("oper_date")==null?"":data.get("oper_date").getAsString();
        str[4] = data.get("branch_no")==null?"All":data.get("branch_no").getAsString();

        dates.put(str);
    }
}
