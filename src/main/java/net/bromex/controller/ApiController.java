package net.bromex.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bromex.service.GeckoCoinService;
import net.bromex.client.GeckoClient;
import net.bromex.client.GekcoRestTemplateClient;
import net.bromex.client.RestTemplateClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.bromex.model.dto.CoinResponse;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@Controller
public class ApiController {

    private final GeckoCoinService geckoCoinService;
    private final GeckoClient geckoClient;
    private final GekcoRestTemplateClient restTemplateClient;

    @GetMapping(path = "/api/v1/coin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCoins(@RequestParam(required = true) String[] ids
            , @RequestParam(value = "currencies", required = true) String[] currencies
            , @RequestParam(value = "includeMarketCap", required = false) Boolean includeMarketCap
            , @RequestParam(value = "include24hrVol", required = false) Boolean include24hrVol
            , @RequestParam(value = "include24hrChange", required = false) Boolean include24hrChange
            , @RequestParam(value = "includeLastUpdated_at", required = false) Boolean includeLastUpdatedAt
    ) {

//        Object coin = getGeckoClient().getSimplePrice(ids, currencies, includeMarketCap
//                , include24hrVol, include24hrChange, includeLastUpdatedAt);
        Object coin = getGeckoCoinService().getAndSaveCoin(ids, currencies, includeMarketCap
                , include24hrVol, include24hrChange, includeLastUpdatedAt);

        return ResponseEntity.status(HttpStatus.OK).body(coin);
    }

    @GetMapping(path = "/api/v1/rest/coin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCoinsWithRestTemplate(@RequestParam(required = true) String[] ids
            , @RequestParam(required = true) String[] currencies
            , @RequestParam Boolean includeMarketCap) {

        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("ids", ids);
        requestParams.put("vs_currencies", currencies);
        requestParams.put("include-market-cap", includeMarketCap);
        ResponseEntity<Object> response = getRestTemplateClient().getCoins(requestParams);

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

}
