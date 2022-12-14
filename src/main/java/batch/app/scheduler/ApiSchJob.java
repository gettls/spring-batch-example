package batch.app.scheduler;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class ApiSchJob extends QuartzJobBean{

	@Autowired
	private Job apiJob;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Override
	@SneakyThrows
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("id", new Date().getTime())
				.toJobParameters();
		
		jobLauncher.run(apiJob, jobParameters);
	}
}
