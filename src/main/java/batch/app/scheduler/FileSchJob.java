package batch.app.scheduler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class FileSchJob extends QuartzJobBean{

	@Autowired
	private Job fileJob;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobExplorer jobExplorer;
	
	@Override
	@SneakyThrows
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		String requestDate = (String)context.getJobDetail().getJobDataMap().get("requestDate");
		
		/*
		 * 중복된 Job을 처리하지 않게 하기 위한 작업
		 */
		int jobInstanceCount = jobExplorer.getJobInstanceCount(fileJob.getName());
		List<JobInstance> jobInstances = jobExplorer.getJobInstances(fileJob.getName(), 0, jobInstanceCount);
		
		if(jobInstances.size() > 0) {
			for(JobInstance jobInstance : jobInstances) {
				List<JobExecution> jobExecution = jobExplorer.getJobExecutions(jobInstance);
				
				List<JobExecution> jobExecutionList = jobExecution.stream().filter(execution ->  
					execution.getJobParameters().getString("requestDate").equals(requestDate)).collect(Collectors.toList());
				
				// 이미 같은 날짜의 csv 파일이 읽힌 적이 있다면 예외 발생
				if(jobExecutionList.size() > 0) {
					throw new JobExecutionException(requestDate + " already exists");
				}
			}
		}
		
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("id", new Date().getTime())
				.addString("requestDate", requestDate)
				.toJobParameters();
		
		jobLauncher.run(fileJob, jobParameters);
	}

	
	
}
