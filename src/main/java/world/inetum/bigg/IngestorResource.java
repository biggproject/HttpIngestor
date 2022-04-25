package world.inetum.bigg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/ingest")
public class IngestorResource {

    private final Logger log = LogManager.getLogger();

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    private final long TIMEOUT = 10;

    public IngestorResource(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public String ingestData(@RequestBody String data)
            throws ExecutionException, InterruptedException, TimeoutException {
        String result = kafkaTemplate.sendDefault("key", data)
                .get(TIMEOUT, TimeUnit.SECONDS)
                .toString();
        log.info("ingested data: \n{}", data);
        return result;
    }

    @PostMapping(path = "/harmonize")
    public String ingestData(@RequestBody HarmonizerInput harmonizerInput)
            throws ExecutionException, InterruptedException, TimeoutException {
        String result = kafkaTemplate.sendDefault("key", harmonizerInput)
                .get(TIMEOUT, TimeUnit.SECONDS)
                .toString();
        log.info("ingested data: \n{}", harmonizerInput);
        return result;
    }
}
