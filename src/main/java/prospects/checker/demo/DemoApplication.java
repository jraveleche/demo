package prospects.checker.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import prospects.checker.demo.controllers.ProspectController;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class DemoApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

	@Autowired
	private ProspectController prospectController;

	@Value("${person.pin}")
	private String pin;

	public static void main(String[] args) {
		LOG.info("STATING APPLICATION");
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		LOG.info("Init process validation {}", pin);
		if(pin != null && !pin.isBlank()) {
			prospectController.initValidation(pin);
		}
		LOG.info("Finish validation proccess");
	}
}
