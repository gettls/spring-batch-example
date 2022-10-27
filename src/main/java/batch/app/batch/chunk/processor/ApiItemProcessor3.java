package batch.app.batch.chunk.processor;

import org.springframework.batch.item.ItemProcessor;

import batch.app.batch.domain.ApiRequestVO;
import batch.app.batch.domain.ProductVO;

public class ApiItemProcessor3 implements ItemProcessor<ProductVO, ApiRequestVO>{

	@Override
	public ApiRequestVO process(ProductVO item) throws Exception {
		return ApiRequestVO.builder()
				.id(item.getId())
				.productVO(item)
				.build();
	}
	
}
