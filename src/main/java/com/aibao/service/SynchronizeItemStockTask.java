package com.aibao.service;

import com.aibao.dto.Sysconfig;
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
        String sql = "SELECT pinfo.branch_no, pinfo.price,shkc.*\n" +
                "    FROM "+Sysconfig.dbName+".bi_t_item_info pinfo, (select a.item_no,sum(stock_total) as stock_total\n" +
                "from (SELECT bi_t_item_info.item_no AS item_no,   IFNULL   ((SELECT \n" +
                "stock_qty                 FROM "+Sysconfig.dbName+".ic_t_branch_stock    WHERE (bi_t_item_info.item_no = item_no)                  AND \n" +
                "(bi_t_branch_info.branch_no = branch_no)), 0) -     IFNULL   ((SELECT SUM(sale_qnty + giv_qnty - ret_qnty - \n" +
                "pre_qnty) AS Expr1                 FROM "+Sysconfig.dbName+".pos_t_daysum      WHERE (bi_t_item_info.item_no = item_no)                  \n" +
                "AND (bi_t_branch_info.branch_no = branch_no)   AND (pre_qnty <> sale_qnty + giv_qnty - ret_qnty)                 \n" +
                "GROUP BY item_no, branch_no), 0) AS stock_total       FROM "+Sysconfig.dbName+".bi_t_item_info  \n" +
                "JOIN "+Sysconfig.dbName+".bi_t_branch_info ) a group by a.item_no  ) shkc where shkc.item_no=pinfo.item_no";
        logger.info("商品库存执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[4];
        str[0] = data.get("item_no")==null?"":data.get("item_no").getAsString();
        str[1] = data.get("stock_qty")==null?"0":data.get("stock_qty").getAsString();
        str[2] = data.get("cost_price")==null?"0":data.get("cost_price").getAsString();
        str[2] = data.get("branch_no")==null?"All":data.get("branch_no").getAsString();


        dates.put(str);
    }
}
