package prospects.checker.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import prospects.checker.demo.models.Response;

@Component
public class HttpController {

    @Autowired
    private RestTemplate restTemplate;

    public <T> T doGet(String url, ParameterizedTypeReference<Response<T>> typeReference) {
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

    public <T> T doPost(String url, Object bodyRequest, ParameterizedTypeReference<Response<T>> typeReference){
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
