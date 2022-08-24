package prospects.checker.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import prospects.checker.demo.models.Response;
import prospects.checker.demo.validators.QualificationValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ProspectController {
    private static final Logger LOG = LoggerFactory.getLogger(ProspectController.class);

    @Autowired
    private QualificationValidator validator;

    @Autowired
    private HttpController httpController;

    @Value("${internal.host}")
    private String  host;

    @Async
    public void initValidation(String pin) {
        try {
            CompletableFuture<Integer> completableFuture =  validator.validate(pin);
            completableFuture.thenAccept(r -> {
               LOG.info("SCORE RESULT ---------> {}", r);
               if(r > 60) {
                   String url = host +
                           "sales/pipeline/" +
                           pin;
                   LOG.info("Is a prospect");
                   Map<String, Object> request = new HashMap<>();
                   request.put("score", r);
                   request.put("convertToProspect", true);
                   ParameterizedTypeReference<Response<Map<String, Object>>> typeReference = new ParameterizedTypeReference<>() {};
                   httpController.doPost(url, request, typeReference);
               }
            });
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
