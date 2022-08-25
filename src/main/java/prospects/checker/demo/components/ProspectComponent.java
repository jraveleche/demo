package prospects.checker.demo.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import prospects.checker.demo.models.Response;
import prospects.checker.demo.validators.QualificationValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class ProspectComponent {
    private static final Logger LOG = LoggerFactory.getLogger(ProspectComponent.class);

    @Autowired
    private QualificationValidator validator;

    @Autowired
    private HttpComponent httpComponent;

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${internal.host}")
    private String  host;

    @Async
    public void initValidation(String pin) {
        CompletableFuture<Integer> completableFuture =  validator.validate(pin);
        completableFuture.thenAccept(r -> {
            LOG.info("SCORE RESULT ---------> {}", r);

            if( r == -1) {
                LOG.error("Error in the prospect validation process");
                initAppShutdown(-1);
                return;
            }

            if(r > 60) {
                String url = host +
                        "sales/pipeline/" +
                        pin;
                LOG.info("Is a prospect");
                Map<String, Object> request = new HashMap<>();
                request.put("score", r);
                request.put("convertToProspect", true);
                ParameterizedTypeReference<Response<Map<String, Object>>> typeReference = new ParameterizedTypeReference<>() {};
                httpComponent.doPost(url, request, typeReference);
            }else {
                LOG.info("Is not a prospect, sorry :(");
            }
            initAppShutdown(0);
        });
    }

    private void initAppShutdown(int returnCode) {
        int exitCode = SpringApplication.exit(applicationContext , () -> returnCode);
        System.exit(exitCode);
    }
}
