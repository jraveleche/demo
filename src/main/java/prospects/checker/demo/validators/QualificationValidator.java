package prospects.checker.demo.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import prospects.checker.demo.components.HttpComponent;
import prospects.checker.demo.models.Response;
import prospects.checker.demo.validators.interfaces.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class QualificationValidator implements Validator<CompletableFuture<Integer>> {

    @Autowired
    private JudicialValidator judicialValidator;

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private HttpComponent httpComponent;

    @Value("${internal.host}")
    private String  host;


    @Override
    @Async
    public CompletableFuture<Integer> validate(String pin) {
        try {
            CompletableFuture<Boolean> judicialCompletableFutre = judicialValidator.validate(pin);
            CompletableFuture<Boolean> personCompetableFuture = personValidator.validate(pin);
            CompletableFuture.allOf(judicialCompletableFutre, personCompetableFuture).join();
            Boolean judicialResult = judicialCompletableFutre.get();
            Boolean personalResult = personCompetableFuture.get();
            Map<String, Boolean> bodyRequest = new HashMap<>();
            String url = host +
                    "score/prospect/" +
                    pin;
            bodyRequest.put("personValidation", personalResult);
            bodyRequest.put("judicialvalidation", judicialResult);
            ParameterizedTypeReference<Response<Map<String, Object>>> typeReference = new ParameterizedTypeReference<>(){};
            Map<String, Object> result = httpComponent.doPost(url, bodyRequest, typeReference);
            int score = 0;
            if(result.containsKey("score")) {
                Object scoreObject = result.get("score");
                String scoreString = String.valueOf(scoreObject);
                score = Integer.parseInt("null".equals(scoreString) ? "0" : scoreString);
            }

            score = score / 10;
            if(judicialResult) {
                score = score - 10;
            }

            if(!personalResult) {
                score = score - 10;
            }
            return CompletableFuture.completedFuture(score);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
