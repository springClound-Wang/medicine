package com.aibao.scheduler;

import com.aibao.service.*;
import com.google.gson.JsonObject;
import com.zhonglai.serialization.GsonConstructor;
import com.zhonglai.task.dto.ScheduledFixedRateType;
import com.zhonglai.task.dto.SysTaskInfo;
import com.zhonglai.task.util.ThreadPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @PAGENAME com.aibao.scheduler
 * @PROJECTNAME kemai
 * @DESCRIPTION
 * @AOUTH Jeff
 * @createtime 2019/6/3021:30
 * @QQ 1142013568
 * @URL http://www.itzooedu.xyz
 */
@Component
public class Scheduler {
    protected static Logger logger = Logger.getLogger(Scheduler.class);


        @Value("${SynchronizeVipCardPayTaskTime}")
        int SynchronizeVipCardPayTaskTime;
        @Value("${SynchronizeOperatorTaskTime}")
        int SynchronizeOperatorTaskTime;

        @Value("${SynchronizeBasePPTaskTime}")
        int SynchronizeBasePPTaskTime;

        @Value("${SynchronizeItemClsTaskTime}")
        int SynchronizeItemClsTaskTime;

        @Value("${SynchronizeItemInfoTaskTime}")
        int SynchronizeItemInfoTaskTime;

        @Value("${SynchronizeItemStockTaskTime}")
        int SynchronizeItemStockTaskTime;

        @Value("${SynchronizePayFlowTaskTime}")
        int SynchronizePayFlowTaskTime;

        @Value("${SynchronizePayWayTaskTime}")
        int SynchronizePayWayTaskTime;

        @Value("${SynchronizeSavingPlusTaskTime}")
        int SynchronizeSavingPlusTaskTime;

        @Value("${SynchronizeSaleFlowTaskTime}")
        int SynchronizeSaleFlowTaskTime;

        @Value("${SynchronizeSaleManTaskTime}")
        int SynchronizeSaleManTaskTime;

        @Value("${SynchronizeSupcustInfoTaskTime}")
        int SynchronizeSupcustInfoTaskTime;

        @Value("${SynchronizeVipInfoTaskTime}")
        int SynchronizeVipInfoTaskTime;
        @Value("${SynchronizeBranchStore}")
        int SynchronizeBranchStore;
    public void test(){
        System.out.println("====");
    }
    public void mainInfo(){

        ThreadPoolUtil.createTask(createSysTaskInfo("商品类型 每分钟", SynchronizeItemClsTask.class,SynchronizeItemClsTaskTime));

          ThreadPoolUtil.createTask(createSysTaskInfo("付款方式 每分钟", SynchronizePayWayTask.class,SynchronizePayWayTaskTime));
       ThreadPoolUtil.createTask(createSysTaskInfo("营业员 每分钟", SynchronizeSaleManTask.class,SynchronizeSaleManTaskTime));
              ThreadPoolUtil.createTask(createSysTaskInfo("供应商 每分钟", SynchronizeSupcustInfoTask.class,SynchronizeSupcustInfoTaskTime));



        ThreadPoolUtil.createTask(createSysTaskInfo("门店 每分钟", SynchronizeBranchStoreTask.class,SynchronizeBranchStore));
        ThreadPoolUtil.createTask(createSysTaskInfo("会员卡支付列表 每分钟", SynchronizeVipCardPayTask.class,SynchronizeVipCardPayTaskTime));
        ThreadPoolUtil.createTask(createSysTaskInfo("收银人 每分钟", SynchronizeOperatorTask.class,SynchronizeOperatorTaskTime));

        ThreadPoolUtil.createTask(createSysTaskInfo("商品品牌 每分钟", SynchronizeBasePPTask.class,SynchronizeBasePPTaskTime));

        ThreadPoolUtil.createTask(createSysTaskInfo("商品信息 每分钟", SynchronizeItemInfoTask.class,SynchronizeItemInfoTaskTime));

        ThreadPoolUtil.createTask(createSysTaskInfo("支付流水 每分钟", SynchronizePayFlowTask.class,SynchronizePayFlowTaskTime));
        ThreadPoolUtil.createTask(createSysTaskInfo("销售流水 每分钟",SynchronizeSaleFlowTask.class,SynchronizeSaleFlowTaskTime));

 ThreadPoolUtil.createTask(createSysTaskInfo("商品库存 每分钟", SynchronizeItemStockTask.class,SynchronizeItemStockTaskTime));

        ThreadPoolUtil.createTask(createSysTaskInfo("会员 每分钟", SynchronizeVipInfoTask.class,SynchronizeVipInfoTaskTime));

        ThreadPoolUtil.createTask(createSysTaskInfo("会员充值 每分钟", SynchronizeSavingPlusTask.class,SynchronizeSavingPlusTaskTime));

        guard();
    }
    private static List<SysTaskInfo> sysTaskInfoList = new ArrayList<>();

    /**
     * 创建定时器对象
     * @param title 标题
     * @param cls 执行类
     * @param time 间隔时间
     * @return
     */
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
}
