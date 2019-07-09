package com.aibao.service;

import com.aibao.dto.Sysconfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.zhonglai.dao.BaseDao;
import com.zhonglai.serialization.GsonConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import com.zhonglai.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 工厂
 */
public abstract class BaseTask implements Runnable{
    protected final  Logger logger = Logger.getLogger(this.getClass());
    private JsonObject data;
    public JsonObject getData() {
        return data;
    }
    public void setData(JsonObject data) {
        this.data = data;
    }
    protected BaseDao baseDao = new BaseDao();
   
    protected String host = "http://"+ Sysconfig.upRealmName;



    protected abstract String getTaskName();
    /**
     * 提交数据的时间
     * @return
     */
    protected abstract String getSubTimePath();
    /**
     * 提交数据的地址
     * @return
     */
    protected abstract String getSubPath();
    /**
     * 获取要提交的数据
     * @param time 数据起始时间
     * @return
     */
    protected abstract JsonArray getSubData(String time);

    /**
     * 放入提交的数据
     * @param dates 被放入的数据
     * @param putdata 要放入的数据
     */
    protected abstract void putSubPath(JSONArray dates,JsonObject putdata);

    public void run() {
        try {
            logger.info("-----------------任务开始"+getTaskName()+"-----------------");
            execute();
            logger.info("-----------------任务结束"+getTaskName()+"-----------------");
        }catch (Exception e)
        {
            logger.error("定时器《"+getTaskName()+"》执行异常",e);
        }
    }
    public void execute() {
        String rStr = null;
        try {
            System.out.println("==========="+host);
            rStr = get(host + getSubTimePath());
        } catch (UnirestException e) {
            logger.info(getTaskName() + "上传异常");
            logger.error(e);
        }
        if (StringUtils.isNotBlank(rStr)) {
            Message message = GsonConstructor.get().fromJson(rStr, Message.class);
            if (message.getCode() == 1) {
                logger.info("返回时间为：" + message.getData());
                JsonArray jsonArray = getSubData(message.getData() + "");
                if (null != jsonArray && jsonArray.size() != 0) {
                    logger.info("有值：" + jsonArray);
                    JSONArray dates = new JSONArray();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        putSubPath(dates, jsonArray.get(i).getAsJsonObject());
                    }
                    String subUrl = host + getSubPath();
                    logger.info("开始发送数据到:" + subUrl + ",\n数据为:" + dates.toString());
                    try {
                        subData(subUrl, dates);
                    } catch (UnirestException e) {
                        logger.info("会员卡支付上传异常");
                        logger.error(e);
                    }
                } else {
                    logger.info("*********** "+getTaskName()+"==无值 ***********");
                }

            }
        } else {
            logger.warn("获取更新时间异常，数据取消同步！");
        }
    }


    /**
     * 传输数据
     * @param url
     * @param jsonArray
     * @throws UnirestException
     */
    protected void subData(String url, JSONArray jsonArray) throws UnirestException {
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("accept","application/json;charset=UTF-8");
        headers.put("Content-Type","application/json;charset=UTF-8");
        headers.put(Sysconfig.loginTokenKey,Sysconfig.actionToken);
        HttpResponse<JsonNode> httpResponse = Unirest.post(url)
                .headers(headers)
                .body(jsonArray)
                .asJson();
        logger.info(getTaskName()+"同步返回消息为："+GsonConstructor.get().toJson(httpResponse));
    }

    /**
     * 获取上次发送数据时间
     * @param url
     * @return
     * @throws UnirestException
     */
    protected String get(String url) throws UnirestException {
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("accept","application/json;charset=UTF-8");
        headers.put("Content-Type","application/json;charset=UTF-8");
        headers.put(Sysconfig.loginTokenKey,Sysconfig.actionToken);
        //post 请求
        HttpResponse<JsonNode> httpResponse = Unirest.get(url)
                .headers(headers)
                .asJson();
        if(httpResponse.getStatus()==200)
        {
            return httpResponse.getBody().toString();
        }
        logger.info("获取上次发送数据时间返回数据失败，返回结果为："+GsonConstructor.get().toJson(httpResponse));
        return null;
    }
}

