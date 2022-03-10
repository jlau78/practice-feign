package net.bromex.client;

import net.bromex.config.ApplicationConfig;
import net.bromex.model.dto.CoinResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "geckoClient", value = "geckoClient"
        , url = "${gecko.api.url}"
        , configuration = ApplicationConfig.class)
public interface GeckoClient {

    @RequestMapping(method = RequestMethod.GET, value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getPing();

    @RequestMapping(method = RequestMethod.GET, value = "/simple/price"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    Object getSimplePrice(@RequestParam("ids") String[] ids
            , @RequestParam( value = "vs_currencies", required = true) String[] currencies
            , @RequestParam(value = "include_market_cap", required = false) Boolean includeMarketCap
            , @RequestParam(value = "include_24hr_vol", required = false) Boolean include24hrVol
            , @RequestParam(value = "include_24hr_change", required = false) Boolean include24hrChange
            , @RequestParam(value = "include_last_updated_at", required = false) Boolean includeLastUpdatedAt

    );


}
