package nil.ed.test.job;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;

/**
 * @author lidelin.
 */
public class QuartzDemo {
    public static void main(String[] args) throws ParseException {
        JobDetailImpl detail = new JobDetailImpl();
        detail.setJobClass(TestJob.class);
        detail.setKey(new JobKey("lidelin"));
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setName("lidelin-Trigger");
        try {
            CronExpression cronExpression = new CronExpression("0/1 * * * * ?");
            cronTrigger.setCronExpression(cronExpression);
            SchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler;
            try {
                scheduler = factory.getScheduler();
                try {
                    scheduler.scheduleJob(detail, cronTrigger);
                    scheduler.start();
                } catch (SchedulerException e) {
                    scheduler.shutdown();
                }
            } catch (SchedulerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
