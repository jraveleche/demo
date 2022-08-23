package prospects.checker.demo.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import prospects.checker.demo.models.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DataRepository {

    private final Logger LOG = LoggerFactory.getLogger(DataRepository.class);

    public Person findById(String id){
        if("2532903920502".equals(id)) {
            Person person = new Person();
            person.setPin("2532903920502");
            person.setGender("Male");
            person.setFirstName("Juan");
            person.setLastname("Veleche");
            person.setJobTitle("Software Engineer");
            person.setLocation("Guatemala");
            try {
                person.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1994-08-26"));
            } catch (ParseException e) {
                LOG.error("CASTA DATE ERROR ", e);
            }
        }
        return null;
    }
}
