package net.bromex.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
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

@Getter
@AllArgsConstructor
@Controller
public class ApiController {

    private final GeckoClient geckoClient;
    private final GekcoRestTemplateClient restTemplateClient;


    @GetMapping(path = "/api/v1/coin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoinResponse> getCoins(@RequestParam(required = true) String[] ids
            , @RequestParam(required = true) String[] currencies
            , @RequestParam Boolean includeMarketCap) {

        CoinResponse coin = getGeckoClient().getSimplePrice(ids, currencies, includeMarketCap);

        return ResponseEntity.status(HttpStatus.OK).body(coin);
    }

    @GetMapping(path = "/api/v1/rest/coin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoinResponse> getCoinsWithRestTemplate(@RequestParam(required = true) String[] ids
            , @RequestParam(required = true) String[] currencies
            , @RequestParam Boolean includeMarketCap) {

        ResponseEntity<CoinResponse> response = getRestTemplateClient().getCoins();

        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

}
