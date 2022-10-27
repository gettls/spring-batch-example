package batch.app.scheduler;

import java.util.HashMap;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/*
 * Spring boot 가 구동되고,
 * Application Runner 가 호출되는데
 * Scheduler 를 Application Runner 구현체 안에 넣어서
 * 실행될 수 있도록 구현
 */
@Component
public abstract class JobRunner implements ApplicationRunner{

	@Autowired
	private Scheduler scheduler;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		doRun(args);
	}

	protected abstract void doRun(ApplicationArguments args);
	
	public Trigger buildJobTrigger(String scheduleExp) {
		return TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
	}
	
	public JobDetail buildJobDetail(Class job, String name, String group, Map params) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.putAll(params);
		
		return JobBuilder.newJob(job).withIdentity(name, group)
				.usingJobData(jobDataMap)
				.build();
	}
}
