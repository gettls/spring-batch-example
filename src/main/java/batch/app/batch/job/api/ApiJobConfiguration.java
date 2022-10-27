package batch.app.batch.job.api;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import batch.app.batch.listener.JobListener;
import batch.app.batch.tasklet.ApiEndTasklet;
import batch.app.batch.tasklet.ApiStartTasklet;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApiJobConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final ApiStartTasklet apiStartTasklet;
	private final ApiEndTasklet apiEndTasklet;
	private final Step jobStep;

	@Bean
	public Job apiJob() {
		return jobBuilderFactory.get("apiJob")
				.listener(new JobListener())
				.start(apiStep1()) // 단순 시작 로그
				.next(jobStep) // 실질적인 배치 로직 담당
				.next(apiStep2()) // 단순 종료 로그
				.build();
	}
	
	@Bean
	public Step apiStep1() {
		return stepBuilderFactory.get("apiStep1")
				.tasklet(apiStartTasklet)
				.build();
	}

	@Bean
	public Step apiStep2() {
		return stepBuilderFactory.get("apiStep2")
				.tasklet(apiEndTasklet)
				.build();
	}
	
}
