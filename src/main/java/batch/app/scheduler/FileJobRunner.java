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
public class FileJobRunner extends JobRunner{

	@Autowired
	private Scheduler scheduler;
	
	@Override
	protected void doRun(ApplicationArguments args) {
		
		String[] sourceArgs = args.getSourceArgs();
		
		JobDetail jobDetail = buildJobDetail(FileSchJob.class, "fileJob", "batch", new HashMap());
		Trigger trigger = buildJobTrigger("0/50 * * * * ?");
		jobDetail.getJobDataMap().put("requestDate", sourceArgs[0]);
		
		try {
			scheduler.scheduleJob(trigger);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
