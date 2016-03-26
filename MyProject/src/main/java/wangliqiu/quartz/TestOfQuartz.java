package wangliqiu.quartz;

import java.util.List;

import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Matcher;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.listeners.JobListenerSupport;

public class TestOfQuartz {

	public static Scheduler sch = null;

	@Test
	public void justTest() throws SchedulerException, InterruptedException {
		sch = new StdSchedulerFactory().getScheduler();

		JobBuilder job = JobBuilder.newJob().ofType(TestJob.class).withIdentity("job", "group"); // withIndentify方法将会生成JobKey，它是JobDetail对象的唯一标志。
		JobDetail jobDetail = job.usingJobData("city1", "shanghai").usingJobData("city2", "beijing").build(); // JobDataMap是job的数据。如果数据直接传参给job属性，则job跑完后会gc，多次跑job不行。

		TriggerBuilder<Trigger> trig = TriggerBuilder.newTrigger().withIdentity("trigger", "group");
		Trigger trigger = trig.withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * * * ?")).build();

		sch.scheduleJob(jobDetail, trigger);

		// JobKey jobKey = jobDetail.getKey();
		// sch.pauseJob(jobKey);
		// sch.resumeJob(jobKey);
		// sch.deleteJob(jobKey);
		// TriggerKey triggerKey = trigger.getKey();
		// sch.rescheduleJob(triggerKey, trigger);
		// sch.getTriggerState(triggerKey);

		// 对于能匹配matcher的job都会添加JobListener//也可以List<Matcher>
		Matcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());
		sch.getListenerManager().addJobListener(new JobListenerSupport() {

			@Override
			public String getName() {
				return "myJobListner";
			}

			@Override
			public void jobToBeExecuted(JobExecutionContext inContext) {
				System.out.println("to be executed");
				// 向Job传递数据
				inContext.getJobDetail().getJobDataMap().put("city3", "NewYork");
			}

			@Override
			public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
				System.out.println("jobWasExecuted");

				// 动态增加任务
				JobDetail jobDetail = JobBuilder.newJob().ofType(TestJob1.class).withIdentity("job1", "group1").build();
				Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
						.withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?")).build();
				try {
					context.getScheduler().scheduleJob(jobDetail, trigger);
				} catch (SchedulerException e) {

				}
			}

		}, matcher);

		sch.start();

		Thread.sleep(1 * 60 * 1000);
		List<JobExecutionContext> listJob = sch.getCurrentlyExecutingJobs();// 返回所有正好在执行的job的上下文
		for (JobExecutionContext context : listJob) {
			System.out.println(context.getJobInstance().getClass() + " " + context.getFireTime());
		}

		sch.shutdown(true); // true 为等待正在执行的 job执行完才关闭；false为直接结束

		if (sch.isShutdown()) {
			SchedulerMetaData metaData = sch.getMetaData();
			System.out.println("jobs:" + metaData.getNumberOfJobsExecuted());
		}

	}
}
