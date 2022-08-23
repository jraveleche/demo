package prospects.checker.demo.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import prospects.checker.demo.controllers.HttpController;
import prospects.checker.demo.models.JudicialRecord;
import prospects.checker.demo.validators.interfaces.Validator;

import java.util.concurrent.CompletableFuture;

@Component
public class JudicialValidator implements Validator<CompletableFuture<Boolean>> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpController<JudicialRecord> httpController;

    @Value("${external.host}")
    private String  host;

    @Override
    @Async
    public CompletableFuture<Boolean> validate(String pin) {
        String url = host +
                "judicial-records/person/" +
                pin;
        JudicialRecord record = httpController.doGet(url);
        return CompletableFuture.completedFuture(record != null && record.isHasJudicialRecords());
    }
}
