package prospects.checker.demo.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import prospects.checker.demo.models.Response;

@Component
public class HttpComponent {

    private final Logger LOG = LoggerFactory.getLogger(HttpComponent.class);

    @Autowired
    private RestTemplate restTemplate;

    public <T> T doGet(String url, ParameterizedTypeReference<Response<T>> typeReference) throws  RestClientException{
        ResponseEntity<Response<T>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                typeReference);
        Response<T> body = response.getBody();
        if(body != null) {
            return body.getData();
        }
        return null;
    }

    public <T> T doPost(String url, Object bodyRequest, ParameterizedTypeReference<Response<T>> typeReference) throws RestClientException {
        ResponseEntity<Response<T>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(bodyRequest),
                typeReference);
        Response<T> body = response.getBody();
        if(body != null) {
            return body.getData();
        }
        return null;
    }
}
