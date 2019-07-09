package com.aibao.service;

import com.aibao.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;

/*
*
 * 商品品牌

*/

public class SynchronizeBasePPTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizeBasePPTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdateTime/modeItemBrand";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModeItemBrand";
    }

    @Override
    protected String getTaskName() {
        return "商品品牌";
    }


    @Override
    protected JsonArray getSubData(String time) {
        String sql = "SELECT DISTINCT item_clsno, item_clsname  FROM "+ Sysconfig.dbName+".bi_t_item_cls WHERE  item_flag=1";
        logger.info("商品品牌执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[2];
        str[0] = data.get("item_clsno").getAsString();
        str[1] = data.get("item_clsname")==null?"":data.get("item_clsname").getAsString();
        dates.put(str);
    }
}
