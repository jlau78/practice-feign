package net.bromex.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bromex.model.dto.CoinResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class GekcoRestTemplateClient {

    @Value("${gecko.api.url}")
    private String apiUrl;

    private final RestTemplateClient restTemplateClient;

    public ResponseEntity getCoins() {
        return getRestTemplateClient().httpGet(apiUrl, CoinResponse.class);
    }


}
