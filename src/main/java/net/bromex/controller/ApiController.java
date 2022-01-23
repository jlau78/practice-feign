package net.bromex.controller;

import lombok.Getter;
import net.bromex.client.GeckoClient;
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
@Controller
public class ApiController {

    private final GeckoClient geckoClient;

    public ApiController(final GeckoClient geckoClient) {
        this.geckoClient = geckoClient;
    }

    @GetMapping(path = "/api/v1/coin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CoinResponse> getCoins(@RequestParam(required = true) String[] ids
            , @RequestParam(required = true) String[] currencies
            , @RequestParam Boolean includeMarketCap) {

        CoinResponse coin = getGeckoClient().getSimplePrice(ids, currencies, includeMarketCap);

        return ResponseEntity.status(HttpStatus.OK).body(coin);
    }
}
