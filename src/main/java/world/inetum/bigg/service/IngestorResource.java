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

import java.util.Comparator;
import java.util.function.Function;

@RestController
@RequestMapping("/ingest")
public class IngestorResource {

    private final Logger log = LogManager.getLogger();
    private final IngestorService ingestorService;
    private final CustomService customService;

    public IngestorResource(IngestorService ingestorService, CustomService customService) {
        this.ingestorService = ingestorService;
        this.customService = customService;
    }

    @PostMapping
    public SendResult<String, Object> ingestData(
            @RequestParam("topic") String topic,
            @RequestParam("key") String key,
            @RequestBody String data
    ) {
        String newData = customService.modifyData(data);

        KafkaMessage message = new KafkaMessage(topic, key, newData);
        return ingestorService.sendMessageAndHandleErrors(message);
    }

    @PostMapping(path = "/harmonize")
    public SendResult<String, Object> ingestData(
            @RequestParam("topic") String topic,
            @RequestParam("key") String key,
            @RequestBody HarmonizerInput harmonizerInput
    ) {
        HarmonizerInput newHarmonizerInput = customService.modifyData(harmonizerInput);

        KafkaMessage message = new KafkaMessage(topic, key, newHarmonizerInput);
        return ingestorService.sendMessageAndHandleErrors(message);
    }
}
