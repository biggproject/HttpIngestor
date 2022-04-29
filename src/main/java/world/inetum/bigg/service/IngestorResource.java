package world.inetum.bigg.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import world.inetum.bigg.model.HarmonizerInput;
import world.inetum.bigg.model.KafkaMessage;

@RestController
@RequestMapping("/ingest")
public class IngestorResource {

    private final Logger log = LogManager.getLogger();
    private final IngestorService ingestorService;

    public IngestorResource(IngestorService ingestorService) {
        this.ingestorService = ingestorService;
    }

    @PostMapping
    public SendResult<String, Object> ingestData(
            @RequestParam("topic") String topic,
            @RequestParam("key") String key,
            @RequestBody String data
    ) {
        KafkaMessage message = new KafkaMessage(topic, key, data);
        return ingestorService.sendMessageAndHandleErrors(message);
    }

    @PostMapping(path = "/harmonize")
    public SendResult<String, Object> ingestData(
            @RequestParam("topic") String topic,
            @RequestParam("key") String key,
            @RequestBody HarmonizerInput harmonizerInput
    ) {
        KafkaMessage message = new KafkaMessage(topic, key, harmonizerInput);
        return ingestorService.sendMessageAndHandleErrors(message);
    }
}
