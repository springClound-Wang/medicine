package com.zhihuAs.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zhihuAs.dto.Sysconfig;
import org.apache.log4j.Logger;
import org.json.JSONArray;

/*
*
 * 商品类型

*/

public class SynchronizeItemClsTask extends BaseTask{
    protected static Logger logger = Logger.getLogger(SynchronizeItemClsTask.class);
    @Override
    protected String getSubTimePath() {
        return "lastUpdate/getModeItemClsLastIdItemClsno";
    }

    @Override
    protected String getSubPath() {
        return "update/updateModeItemCls";
    }

    @Override
    protected String getTaskName() {
        return "商品类型";
    }


    @Override
    protected JsonArray getSubData(String time) {
        String sql = "select DISTINCT item_clsno, item_clsname,cls_parent  from "+ Sysconfig.dbName+".t_bd_item_cls where item_flag=0 ";
        logger.info("商品类型执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[3];
        str[0] = data.get("item_clsno")==null?"":data.get("item_clsno").getAsString();
        str[1] = data.get("item_clsname")==null?"":data.get("item_clsname").getAsString();
        str[2] =data.get("cls_parent")==null?"":data.get("cls_parent").getAsString();;

        dates.put(str);
    }
}
