package batch.app.batch.classifier;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

import batch.app.batch.domain.ApiRequestVO;
import batch.app.batch.domain.ProductVO;

public class WriterClassifier<C,T> implements Classifier<C, T> {

	private Map<String, ItemWriter<ApiRequestVO>> writerMap = new HashMap<>();

	public void setwriterMap(Map<String, ItemWriter<ApiRequestVO>> writerMap) {
		this.writerMap = writerMap;
	}

	@Override
	public T classify(C classifiable) {
		return (T)writerMap.get(((ApiRequestVO)classifiable).getProductVO().getType());
	}
	
}
