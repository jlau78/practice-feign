package net.bromex.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.bromex.client.GeckoClient;
import net.bromex.model.dto.Coin;
import net.bromex.repository.CoinDocumentRepository;
import net.bromex.service.handler.CoinDetailsHandler;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Log4j2
@Getter
@Setter
@RequiredArgsConstructor
@Service
public class GeckoCoinService {

    public static final String NODE_USD_MARKET_CAP = "usd_market_cap";
    public static final String NODE_USD_24h_change = "usd_24h_change";
    public static final String NODE_USD_24h_VOL = "usd_24h_vol";
    public static final String NODE_LAST_UPDATED_AT = "last_updated_at";

    private final ObjectMapper mapper = new ObjectMapper();
    private final CoinDetailsHandler handler;
    private final GeckoClient geckoClient;
    private final CoinDocumentRepository coinDocumentRepository;

    public Object getAndSaveCoin(String[] ids, String[] currencies, Boolean includeMarketCap
            , Boolean include24hrVol, Boolean include24hrChange, Boolean includeLastUpdatedAt) {
        log.info("Get and save coin: {}:{}", ids, currencies);
        Object coin = getCoin(ids, currencies, includeMarketCap, include24hrVol, include24hrChange, includeLastUpdatedAt);
        saveCoin(ids, coin);

        return coin;
    }

    public Object getCoin(String[] ids, String[] currencies, Boolean includeMarketCap
            , Boolean include24hrVol, Boolean include24hrChange, Boolean includeLastUpdatedAt) {
        log.info("Get coin: {}:{}", ids, currencies);
        return getGeckoClient().getSimplePrice(ids, currencies, includeMarketCap
                , include24hrVol, include24hrChange, includeLastUpdatedAt);
    }

    /**
     * Save the coin
     *
     * @param oCoin Coin object returned
     */
    public void saveCoin(final String[] ids, Object oCoin) {
        log.info("Starting save coins {}: {}", ids, oCoin);
        Arrays.stream(ids)
                .forEach((coinid) -> {
                    try {
                        Coin coin = handler.transform(coinid, oCoin);
                        if (Objects.nonNull(coin)) {
                            log.debug("Saving coin {}: {}", coin.getId(), coin.getPrice());
                            // TODO: check lastupdated is different before saving
                            log.debug("Do not save if coin.lastUpdated is same a previous: {}", coin.getLastUpdated());
                            getCoinDocumentRepository().insert(coin);
                        } else {
                            log.warn("Coin cannot be created:{}", coin);
                        }
                    } catch (Exception e) {
                        log.error("Fail to get the coin details for: " + coinid + ", " + e.getMessage(), e);
                    }
                });

    }

}
