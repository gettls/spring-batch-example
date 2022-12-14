package batch.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import batch.app.batch.domain.ApiInfo;
import batch.app.batch.domain.ApiResponseVO;

@Service
public class ApiService3 extends AbstractApiService{

	@Override
	protected ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo) {
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8081/api/product/3", apiInfo, String.class);
		int statusCodeValue = responseEntity.getStatusCodeValue();
		ApiResponseVO apiResponseVO = ApiResponseVO.builder().status(statusCodeValue).msg(responseEntity.getBody()).build();

		return apiResponseVO;
	}
}
