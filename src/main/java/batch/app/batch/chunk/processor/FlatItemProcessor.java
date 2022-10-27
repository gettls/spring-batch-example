package batch.app.batch.chunk.processor;

import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

import batch.app.batch.domain.Product;
import batch.app.batch.domain.ProductVO;

public class FlatItemProcessor implements ItemProcessor<ProductVO, Product> {

	@Override
	public Product process(ProductVO item) throws Exception {
		
		ModelMapper modelMapper = new ModelMapper();
		Product product = modelMapper.map(item, Product.class);
		
		return product;
	}

}
