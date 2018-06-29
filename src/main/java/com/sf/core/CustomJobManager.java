package com.sf.core;

import com.sf.scheduler.core.context.AbstractJobManager;
import com.sf.scheduler.core.entity.CronJob;
import com.sf.scheduler.core.entity.OneShotJob;
import com.sf.scheduler.core.timer.Constants;
import com.sf.scheduler.core.util.TimeUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: lijie.zh
 */
@Component
@Slf4j
@Getter
public class CustomJobManager extends AbstractJobManager {

    /**
     * 必须在运行的项目里注入才能在IDE中运行（原因待查）
     */
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    /**
     * 添加单次任务
     *
     * @param job
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public void addOneShotJob(OneShotJob job, String groupName) throws Exception {
        Scheduler scheduler = getSchedulerFactoryBean().getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobId(), groupName);
        SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(job.getJobClass().trim());
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getJobId(), groupName).build();
            jobDetail.getJobDataMap().put(Constants.JOBMAP_KEY, job);
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();

            Date triggerStartTime = TimeUtils.strToDate(job.getExecTime(), "yyyyMMddHHmmss");
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobId(), groupName).withSchedule(scheduleBuilder).startAt(triggerStartTime).build();
            scheduler.scheduleJob(jobDetail, trigger);

        } else {
            // Trigger已存在，那么更新相应的定时设置
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();

            Date triggerStartTime = TimeUtils.strToDate(job.getExecTime(), "yyyyMMddHHmmss");
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobId(), groupName).withSchedule(scheduleBuilder).startAt(triggerStartTime).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 添加定时任务
     *
     * @param job
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public void addCronJob(CronJob job, String groupName) throws Exception {
        Scheduler scheduler = getSchedulerFactoryBean().getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobId(), groupName);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(job.getJobClass().trim());
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getJobId(), groupName).build();
            jobDetail.getJobDataMap().put(Constants.JOBMAP_KEY, job);

            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            // 按新的cronExpression表达式构建一个新的trigger
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobId(), groupName).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }

        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
        job.setJobStatus(triggerState.name());
    }


}
