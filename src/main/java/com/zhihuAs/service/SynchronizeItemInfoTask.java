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

    String sql="select i.item_no,i.price,i.item_name,i.item_brand,i.item_clsno,i.item_subno,i.item_size,i.unit_no,i.modify_date, i.sale_price,i.vip_price,i.main_supcust,i.branch_no,i.build_date,ROUND(s.real_qty,0) as real_qty from "+Sysconfig.dbName+".t_bd_item_info i, (select f.item_no,sum(f.real_qty) as real_qty from "+Sysconfig.dbName+".t_im_flow f where f.db_no='+' group by f.item_no) s where s.item_no=i.item_no";
        logger.info("商品执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[15];
        str[0] = data.get("item_no")==null?"":data.get("item_no").getAsString();
        str[1] = data.get("price")==null?"0":data.get("price").getAsString();
        str[2] = data.get("real_qty")==null?"0":data.get("real_qty").getAsString();
        str[3] = data.get("item_name")==null?"":data.get("item_name").getAsString();
        str[4] = data.get("item_brand")==null?"":data.get("item_brand").getAsString();
        str[5] = data.get("item_clsno")==null?"":data.get("item_clsno").getAsString();
        str[6] = data.get("item_subno")==null?"":data.get("item_subno").getAsString();
        str[7] = data.get("item_size")==null?"":data.get("item_size").getAsString();
        str[8] = data.get("unit_no")==null?"":data.get("unit_no").getAsString();
        str[9] = data.get("modify_date")==null?data.get("build_date").getAsString():data.get("modify_date").getAsString();
        str[10] = data.get("sale_price")==null?"0":data.get("sale_price").getAsString();
        str[11] = data.get("vip_price")==null?"0":data.get("vip_price").getAsString();
        str[12] = data.get("main_supcust")==null?"0":data.get("main_supcust").getAsString();
        str[13] = data.get("branch_no")==null?"All":data.get("branch_no").getAsString();
        str[14] = data.get("build_date")==null?"0":data.get("build_date").getAsString();

        dates.put(str);
    }
}
