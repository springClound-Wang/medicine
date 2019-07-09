/*
package com.aibao;

import com.aibao.service.*;
import com.google.gson.JsonObject;
import com.zhonglai.serialization.GsonConstructor;
import com.zhonglai.task.dto.ScheduledFixedRateType;
import com.zhonglai.task.dto.SysTaskInfo;
import com.zhonglai.task.util.ThreadPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;
import java.util.List;


*/
/**
 * @类名 StartMain
 * @描述 TODO
 * @创建者 wzz
 * @时间 19-6-19 下午2:29
 * @版本 1.0
 **//*

public class StartMain {
    protected static Logger logger = Logger.getLogger(StartMain.class);

    public static void main(String[] args) {
        String path = System.getProperty("user.dir")+"/configs/task-log4j.properties";
        PropertyConfigurator.configure(path);
      */
/*
              ThreadPoolUtil.createTask(createSysTaskInfo("商品信息 每分钟", SynchronizeItemInfoTask.class,120));

        ThreadPoolUtil.createTask(createSysTaskInfo("会员卡支付列表 每分钟", SynchronizeVipCardPayTask.class,180));
        ThreadPoolUtil.createTask(createSysTaskInfo("收银人 每分钟", SynchronizeOperatorTask.class,300));
        ThreadPoolUtil.createTask(createSysTaskInfo("商品品牌 每分钟", SynchronizeBasePPTask.class,180));
        ThreadPoolUtil.createTask(createSysTaskInfo("商品类型 每分钟", SynchronizeItemClsTask.class,180));

        ThreadPoolUtil.createTask(createSysTaskInfo("商品库存 每分钟",SynchronizeItemStockTask.class,180));




        ThreadPoolUtil.createTask(createSysTaskInfo("支付流水 每分钟",SynchronizePayFlowTask.class,120));
        ThreadPoolUtil.createTask(createSysTaskInfo("付款方式 每分钟",SynchronizePayWayTask.class,300));
        ThreadPoolUtil.createTask(createSysTaskInfo("会员充值 每分钟",SynchronizeSavingPlusTask.class,60));
        ThreadPoolUtil.createTask(createSysTaskInfo("销售流水 每分钟",SynchronizeSaleFlowTask.class,120));
        ThreadPoolUtil.createTask(createSysTaskInfo("营业员 每分钟", SynchronizeSaleManTask.class,300));
        ThreadPoolUtil.createTask(createSysTaskInfo("供应商 每分钟",SynchronizeSupcustInfoTask.class,180));
        ThreadPoolUtil.createTask(createSysTaskInfo("会员 每分钟", SynchronizeVipInfoTask.class,120));

       *//*


        guard();
    }


    private static List<SysTaskInfo> sysTaskInfoList = new ArrayList<>();

    */
/**
     * 创建定时器对象
     * @param title 标题
     * @param cls 执行类
     * @param time 间隔时间
     * @return
     *//*

    private static SysTaskInfo createSysTaskInfo(String title,Class cls,Integer
            time)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("period",time);

        SysTaskInfo sysTaskInfo = new SysTaskInfo();
        sysTaskInfo.setState(1);
        sysTaskInfo.setClassPath(cls.getName());
        sysTaskInfo.setConfigure(jsonObject.toString());
        sysTaskInfo.setTitle(title);
        sysTaskInfo.setType(ScheduledFixedRateType.type.value);
        sysTaskInfo.setId((ThreadPoolUtil.threads.size()+1)+"");
        sysTaskInfoList.add(sysTaskInfo);

        return sysTaskInfo;
    }

    private static void guard()
    {
        //启动守护线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("-------------------启动守护线程---------------------");
                while (true)
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(SysTaskInfo sti:sysTaskInfoList)
                    {
                        String serviceKey = sti.getId() + "";
                        if(!ThreadPoolUtil.threads.containsKey(serviceKey) || (ThreadPoolUtil.threads.get(serviceKey).isDone() && (sti.getType()-0)!=0 ))
                        {
                            if(StringUtils.isNoneBlank(sti.getDataValue()))
                            {
                                sti.setData(GsonConstructor.get().fromJson(sti.getDataValue(), JsonObject.class));
                            }
                            sti.setClassPath(StringUtils.trim(sti.getClassPath()));
                            ThreadPoolUtil.createTask(sti);
                            logger.info("守护线程启动丢失线程："+sti.getTitle()+" "+sti.getClassPath());
                        }
                    }
                }
            }
        }).start();
    }
}*/
