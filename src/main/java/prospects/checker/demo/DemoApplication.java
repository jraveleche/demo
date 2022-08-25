package prospects.checker.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import prospects.checker.demo.services.ProspectService;

import java.util.List;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class DemoApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

	@Autowired
	private ProspectService prospectService;

	@Value("${person.pin}")
	private String pin;

	@Value("${test.run}")
	private String runTest;

	public static void main(String[] args) {
		LOG.info("STATING APPLICATION");
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		LOG.info("Init process validation {}", pin);
		String checkRunTest = String.valueOf(runTest);
		if(pin != null && !pin.isBlank() && "null".equals(checkRunTest)) {
			prospectService.initValidation(pin);
		}else if ("true".equals(checkRunTest)) {
			LOG.info("Running test scenarios ...");
			List<String> values = List.of("error", "001", "2532903920502");
			for(String value : values) {
				prospectService.initValidation(value);
			}
		}
		LOG.info("Finish validation proccess");
	}
}
