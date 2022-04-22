package world.inetum.bigg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingest")
public class IngestorResource {

    private final Logger log = LogManager.getLogger();

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public IngestorResource(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public String ingestData(@RequestBody String data) {
        kafkaTemplate.sendDefault("key", data);
        log.info("ingested data: \n{}", data);
        return data;
    }

    @PostMapping(path = "/harmonize")
    public HarmonizerInput ingestData(@RequestBody HarmonizerInput harmonizerInput) {
        kafkaTemplate.sendDefault("key", harmonizerInput);
        log.info("ingested data: \n{}", harmonizerInput);
        return harmonizerInput;
    }
}
