package prospects.checker.demo.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import prospects.checker.demo.controllers.HttpComponent;
import prospects.checker.demo.models.JudicialRecord;
import prospects.checker.demo.models.Response;
import prospects.checker.demo.validators.interfaces.Validator;

import java.util.concurrent.CompletableFuture;

@Component
public class JudicialValidator implements Validator<CompletableFuture<Boolean>> {

    @Autowired
    private HttpComponent httpComponent;

    @Value("${external.host}")
    private String  host;

    @Override
    @Async
    public CompletableFuture<Boolean> validate(String pin) {
        String url = host +
                "judicial-records/person/" +
                pin;
        ParameterizedTypeReference<Response<JudicialRecord>> typeReference = new ParameterizedTypeReference<>() {};
        JudicialRecord record = httpComponent.doGet(url, typeReference);
        return CompletableFuture.completedFuture(record != null && record.isHasJudicialRecords());
    }
}
