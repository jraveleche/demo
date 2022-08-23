package prospects.checker.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import prospects.checker.demo.validators.QualificationValidator;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ProspectController {
    private static final Logger LOG = LoggerFactory.getLogger(ProspectController.class);

    @Autowired
    private QualificationValidator validator;

    @Async
    public void initValidation() {
        try {
            CompletableFuture<Integer> completableFuture =  validator.validate("2532903920502");
            completableFuture.thenAccept(r -> {
               LOG.info("SCORE RESULT ---------> {}", r);
               if(r > 60) {
                   LOG.info("Is a prospect");
               }else {
                   LOG.info("Is not a prospect");
               }
            });
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
