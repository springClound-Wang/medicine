package com.aibao.scheduler;

import com.aibao.dto.Sysconfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @PAGENAME com.aibao.scheduler
 * @PROJECTNAME kemai
 * @DESCRIPTION
 * @AOUTH Jeff
 * @createtime 2019/6/3021:35
 * @QQ 1142013568
 */
@Component
public class MyQueueListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    Scheduler scheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        scheduler.mainInfo();
    }
}

