package prospects.checker.demo.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import prospects.checker.demo.controllers.HttpController;
import prospects.checker.demo.models.Person;
import prospects.checker.demo.models.Response;
import prospects.checker.demo.repository.DataRepository;
import prospects.checker.demo.validators.interfaces.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

@Component
public class PersonValidator implements Validator<CompletableFuture<Boolean>> {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private HttpController httpController;

    @Value("${external.host}")
    private String  host;

    @Override
    @Async
    public CompletableFuture<Boolean> validate(String pin) {
        String url = host +
                "national-registry-identification/person/" +
                pin;
        ParameterizedTypeReference<Response<Person>> typeReference = new ParameterizedTypeReference<>() {};
        Person person = httpController.doGet(url, typeReference);
        Person personDataBase = dataRepository.findById(pin);
        return CompletableFuture.completedFuture(comparePersonData(person, personDataBase));
    }

    private Boolean comparePersonData(Person person1, Person person2) {
        if (person1 == null || person2 == null) {
            return Boolean.FALSE;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String date1 = dateFormat.format(person1.getDateOfBirth());
        String date2 = dateFormat.format(person2.getDateOfBirth());

        if(!date1.equals(date2)) {
            return Boolean.FALSE;
        }

        if(!person1.getPin().equals(person2.getPin())) {
            return Boolean.FALSE;
        }

        if(!person1.getFirstName().equals(person2.getFirstName())) {
            return Boolean.FALSE;
        }

        if(!person1.getLastname().equals(person2.getLastname())) {
            return Boolean.FALSE;
        }

        if(!person1.getGender().equals(person2.getGender())) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
