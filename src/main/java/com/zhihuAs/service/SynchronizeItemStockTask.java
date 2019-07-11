package com.zhihuAs.service;

import com.zhihuAs.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商品库存
 */

public class SynchronizeItemStockTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizeItemStockTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdateTime/modeItemStock";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModeItemStock";
    }

    @Override
    protected String getTaskName() {
        return "商品库存";
    }

    @Autowired
    Sysconfig sysconfig;
    @Override
    protected JsonArray getSubData(String time) {
        String sql = "select item_no,SUM(stock_qty) as stock_qty,avg_cost,branch_no from "+Sysconfig.dbName+".t_im_branch_stock group by item_no ";
        logger.info("商品库存执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[4];
        str[0] = data.get("item_no")==null?"":data.get("item_no").getAsString();
        str[1] = data.get("stock_qty")==null?"0":data.get("stock_qty").getAsString();
        str[2] = data.get("avg_cost")==null?"0":data.get("avg_cost").getAsString();
        str[3] = data.get("branch_no")==null?"All":data.get("branch_no").getAsString();


        dates.put(str);
    }
}
