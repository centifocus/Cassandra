package wangliqiu.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TestJob1 implements Job {
	private int i = 1;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(i);
		i = 0;

		System.out.println("dddddddddd");

	}
}
