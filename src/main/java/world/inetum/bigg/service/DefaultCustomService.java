package world.inetum.bigg.service;

import org.springframework.stereotype.Component;
import world.inetum.bigg.model.HarmonizerInput;

@Component
public class DefaultCustomService implements CustomService {

    @Override
    public String modifyData(String data) {
        return data;
    }

    @Override
    public HarmonizerInput modifyData(HarmonizerInput data) {
        return data;
    }
}
