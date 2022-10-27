package batch.app.batch.chunk.writer;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

import batch.app.batch.domain.ApiRequestVO;
import batch.app.batch.domain.ApiResponseVO;
import batch.app.service.AbstractApiService;

public class ApiItemWriter2 extends FlatFileItemWriter<ApiRequestVO>{

	private final AbstractApiService apiService;
	
	public ApiItemWriter2(AbstractApiService apiService) {
		this.apiService = apiService;
	}
	
	@Override
	public void write(List<? extends ApiRequestVO> items) throws Exception {
		ApiResponseVO responseVO = apiService.service(items);
		System.out.println("responseVO = " + responseVO);
		
		items.forEach(item -> item.setApiResponseVO(responseVO));
		
		super.setResource(new FileSystemResource("/app/src/main/resources/product2.txt"));
		super.open(new ExecutionContext());
		super.setLineAggregator(new DelimitedLineAggregator<>());
		super.setAppendAllowed(true);
		super.write(items);
	}
	
}
