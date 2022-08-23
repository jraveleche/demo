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
public class HttpController<T> {

    @Autowired
    private RestTemplate restTemplate;

    public T doGet(String url) {
        ResponseEntity<Response<T>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        Response<T> body = response.getBody();
        if(body != null) {
            return body.getData();
        }
        return null;
    }

    public T doPost(String url, Object bodyRequest){
        ResponseEntity<Response<T>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(bodyRequest),
                new ParameterizedTypeReference<>() {
                });
        Response<T> body = response.getBody();
        if(body != null) {
            return body.getData();
        }
        return null;
    }
}
