package com.aibao.service;

import com.aibao.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
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
        return "lastUpdateTime/modeItemCls";
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
        String sql = "select DISTINCT item_clsno, item_clsname  from "+ Sysconfig.dbName+".bi_t_item_cls where item_flag=0 ";
        logger.info("会员执行的sql为："+sql);
        return this.baseDao.findBysql(sql);
    }

    @Override
    protected void putSubPath(JSONArray dates, JsonObject data) {
        String[] str = new String[3];
        str[0] = data.get("item_clsno")==null?"":data.get("item_clsno").getAsString();
        str[1] = data.get("item_clsname")==null?"":data.get("item_clsname").getAsString();
        if(StringUtils.isNotEmpty(str[0])&&str[0].length()==2){
            str[2] ="";
        }else if(StringUtils.isNotEmpty(str[0])&&str[0].length()==4){
            str[2] =str[0].substring(0,2);
        }else if(StringUtils.isNotEmpty(str[0])&&str[0].length()==6){
            str[2] =str[0].substring(0,4);
        }

        dates.put(str);
    }
}
