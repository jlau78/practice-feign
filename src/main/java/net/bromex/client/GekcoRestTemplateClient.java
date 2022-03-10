package net.bromex.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.bromex.config.ApplicationConfig;
import net.bromex.model.dto.CoinResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@Component
public class GekcoRestTemplateClient {

    private final RestTemplateClient restTemplateClient;

    private final ApplicationConfig applicationConfig;

    public ResponseEntity getCoins(Map<String, Object> requestParams) {
        String url = applicationConfig.getGeckoApiUrl() + "/simple/price";
        return getRestTemplateClient().httpGet(url, requestParams, Object.class);
    }


}
