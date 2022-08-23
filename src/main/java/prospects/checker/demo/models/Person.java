package prospects.checker.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person implements Serializable {
    private String pin;
    private String firstName;
    private String lastname;
    private Date dateOfBirth;
    private String location;
    private String gender;
    private String jobTitle;

    @Override
    public String toString() {
        return "Person{" +
                "pin='" + pin + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", location='" + location + '\'' +
                ", gender='" + gender + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}
