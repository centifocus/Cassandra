package wangliqiu.quartz;

import java.util.Map.Entry;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

// 会更新JobDataMap
@PersistJobDataAfterExecution
// 不允许当前任务没结束前，下一任务到点执行。
@DisallowConcurrentExecution
public class TestJob implements Job {

	// JobExecutionContext是JobDetail中的JobDataMap和Trigger中的JobDataMap的并集， 但是如果存在相同的数据，则后者会覆盖前者的值。
	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		// JobDetail中的JobDataMap和Trigger中的JobDataMap的并集
		// JobDataMap dataMap = context.getMergedJobDataMap(); // 该datamap为初始的，不能设值。

		for (Entry<String, Object> entry : dataMap.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}

		dataMap.put("city1", "london");

	}
}
