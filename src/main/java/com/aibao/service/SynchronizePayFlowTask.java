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
 * 支付流水


*/

public class SynchronizePayFlowTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizePayFlowTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdateTime/modePayFlow";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModePayFlow";
    }

    @Override
    protected String getTaskName() {
        return "支付流水";
    }


    @Override
    protected JsonArray getSubData(String time) {
        String sql = "select com_no,flow_id,flow_no,sale_amount,pay_way,sell_way,DATE_FORMAT(oper_date, '%Y-%m-%d %H:%i:%s') as oper_date,pay_amount,vip_no,card_no,branch_no from "+ Sysconfig.dbName+".pos_t_payflow WHERE oper_date>'"+time+"'";
        logger.info("支付流水执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[11];
        str[0] = data.get("com_no")==null?"0":data.get("com_no").getAsString();
        str[1] = data.get("flow_id")==null?"0":data.get("flow_id").getAsString();
        str[2] = data.get("flow_no")==null?"":data.get("flow_no").getAsString().trim();
        str[3] = data.get("sale_amount")==null?"0":data.get("sale_amount").getAsString();
        str[4] = data.get("pay_way")==null?"":data.get("pay_way").getAsString();
        str[5] = data.get("sell_way")==null?"":data.get("sell_way").getAsString();
        str[6] = data.get("oper_date")==null?"":data.get("oper_date").getAsString();
        str[7] = data.get("pay_amount")==null?"0":data.get("pay_amount").getAsString();
        str[8] = data.get("vip_no")==null?"":data.get("vip_no").getAsString();
        str[9] = data.get("card_no")==null?"":data.get("card_no").getAsString();
        str[10] = data.get("branch_no")==null?"All":data.get("branch_no").getAsString();

        dates.put(str);
    }
}
