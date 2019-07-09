package com.zhihuAs.service;

import com.zhihuAs.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

/*
*
 * 商品信息

*/

public class SynchronizeItemInfoTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizeItemInfoTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdateTime/modeItemInfo";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModeItemInfo";
    }

    @Override
    protected String getTaskName() {
        return "商品信息";
    }

    @Autowired
    Sysconfig sysconfig;
    @Override
    protected JsonArray getSubData(String time) {

    String sql="SELECT i.item_no ,DATE_FORMAT(i.oper_date, '%Y-%m-%d %H:%i:%s') as oper_date,ROUND(b.stock_qty) as stock_qty,ROUND(price,2) as price ,item_name,item_brand,item_clsno,item_subno,item_size,unit_no,DATE_FORMAT(modify_date, '%Y-%m-%d %H:%i:%s') as modify_date, ROUND(sale_price,2) as sale_price,ROUND(vip_price,2) as vip_price ,sup_no ,i.branch_no,i.insert_data FROM  "+Sysconfig.dbName+".bi_t_item_info i , (select s.item_no, sum(s.stock_qty) as stock_qty from "+Sysconfig.dbName+".ic_t_branch_stock s group by s.item_no ) b where b.item_no=i.item_no";
        logger.info("商品执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[15];
        str[0] = data.get("item_no")==null?"":data.get("item_no").getAsString();
        str[1] = data.get("price")==null?"0":data.get("price").getAsString();
        str[2] = data.get("stock_qty")==null?"0":data.get("stock_qty").getAsString();
        str[3] = data.get("item_name")==null?"":data.get("item_name").getAsString();
        str[4] = data.get("item_brand")==null?"":data.get("item_brand").getAsString();
        str[5] = data.get("item_clsno")==null?"":data.get("item_clsno").getAsString();
        str[6] = data.get("item_subno")==null?"":data.get("item_subno").getAsString();
        str[7] = data.get("item_size")==null?"":data.get("item_size").getAsString();
        str[8] = data.get("unit_no")==null?"":data.get("unit_no").getAsString();
        str[9] = data.get("modify_date")==null?data.get("oper_date").getAsString():data.get("modify_date").getAsString();
        str[10] = data.get("sale_price")==null?"0":data.get("sale_price").getAsString();
        str[11] = data.get("vip_price")==null?"0":data.get("vip_price").getAsString();
        str[12] = data.get("sup_no")==null?"0":data.get("sup_no").getAsString();
        str[13] = data.get("branch_no")==null?"All":data.get("branch_no").getAsString();
        str[14] = data.get("insert_data")==null?"0":data.get("insert_data").getAsString();

        dates.put(str);
    }
}
