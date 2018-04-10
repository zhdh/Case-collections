package com.zhdh.job;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobStart {
	
	public static void main(String[] args) throws SchedulerException {
		// define the job and tie it to our MyJob class
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();
		JobDetail job = JobBuilder.newJob(MyJob.class).build();
		Trigger trigger = TriggerBuilder.newTrigger().startAt(new Date(3000L))
				.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ? ")).build();
		scheduler.scheduleJob(job, trigger);
		scheduler.start();
	}

}
