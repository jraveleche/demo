package prospects.checker.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JudicialRecord implements Serializable {
    private String pin;
    private boolean hasJudicialRecords;

    @Override
    public String toString() {
        return "JudicialRecord{" +
                "pin='" + pin + '\'' +
                ", hasJudicialRecords=" + hasJudicialRecords +
                '}';
    }
}
