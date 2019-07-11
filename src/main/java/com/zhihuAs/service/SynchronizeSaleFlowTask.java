package com.zhihuAs.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zhihuAs.dto.Sysconfig;
import org.apache.log4j.Logger;
import org.json.JSONArray;

/*
*
 * 销售流水

*/


public class SynchronizeSaleFlowTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizeSaleFlowTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdateTime/modeSaleFlow";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModeSaleFlow";
    }

    @Override
    protected String getTaskName() {
        return "销售流水";
    }


    @Override
    protected JsonArray getSubData(String time) {
        String sql = "SELECT  p.branch_no ,p.com_no,p.remote_flag,DATE_FORMAT(p.oper_date, '%Y-%m-%d %H:%i:%s') as oper_date,p.sale_money,p.sale_qnty,p.flow_no,p.item_no,p.sale_man,p.counter_no,d.giv_amt,p.oper_id,p.sell_way FROM  "+ Sysconfig.dbName+".t_rm_saleflow p left join  (select s.giv_amt,p.flow_no from "+Sysconfig.dbName+".t_rm_daysum s,"+Sysconfig.dbName+".t_rm_saleflow p where s.item_no=p.item_no and p.sell_way='C') d on  d.flow_no=p.flow_no where  p.oper_date>'"+time+"'";

        logger.info("销售流水执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[13];
        str[0] = data.get("com_no")==null?"0":data.get("com_no").getAsString();
        str[1] = data.get("remote_flag")==null?"":data.get("remote_flag").getAsString();
        str[2] = data.get("oper_date")==null?"":data.get("oper_date").getAsString();
        str[3] = data.get("sale_money")==null?"0":data.get("sale_money").getAsString();
        str[4] = data.get("sale_qnty")==null?"0":data.get("sale_qnty").getAsString();
        str[5] = data.get("flow_no")==null?"":data.get("flow_no").getAsString();
        str[6] = data.get("item_no")==null?"":data.get("item_no").getAsString();
        str[7] = data.get("sale_man")==null?"":data.get("sale_man").getAsString();
        str[8] = data.get("counter_no")==null?"0":data.get("counter_no").getAsString();
        str[9] = data.get("giv_amt")==null?"0":data.get("giv_amt").getAsString();
        str[10] = data.get("oper_id")==null?"":data.get("oper_id").getAsString();
        str[11] = data.get("sell_way")==null?"":data.get("sell_way").getAsString();
        str[12] = data.get("branch_no")==null?"All":data.get("branch_no").getAsString();

        dates.put(str);
    }
}
