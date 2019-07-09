package com.zhihuAs.service;

import com.zhihuAs.dto.Sysconfig;
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
        String sql = "SELECT DISTINCT code_id, code_name  FROM "+ Sysconfig.dbName+".t_bd_base_code WHERE  type_no='PP'";
        logger.info("商品品牌执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[2];
        str[0] = data.get("code_id").getAsString();
        str[1] = data.get("code_name")==null?"":data.get("code_name").getAsString();
        dates.put(str);
    }
}
