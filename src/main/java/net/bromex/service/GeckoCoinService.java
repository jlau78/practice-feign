package net.bromex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.bromex.client.GeckoClient;
import net.bromex.model.dto.Coin;
import net.bromex.repository.CoinDocumentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Optional.ofNullable;

@Log4j2
@Getter
@RequiredArgsConstructor
@Service
public class GeckoCoinService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final GeckoClient geckoClient;
    private final CoinDocumentRepository coinDocumentRepository;

    public Object getAndSaveCoin(String[] ids, String[] currencies, Boolean includeMarketCap
            , Boolean include24hrVol, Boolean include24hrChange, Boolean includeLastUpdatedAt) {
        log.info("Get and save coin: {}:{}", ids, currencies);
        Object coin = getCoin(ids, currencies, includeMarketCap, include24hrVol, include24hrChange, includeLastUpdatedAt);
        saveCoin(coin);

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
    public void saveCoin(Object oCoin) {
        log.info("Save coin: {}", oCoin);
        try {
            Coin coin = toCoin(oCoin);
            if (Objects.nonNull(coin)) {
                getCoinDocumentRepository().insert(coin);
            } else {
                log.warn("Coin cannot be created:{}", mapper.writeValueAsString(oCoin));
            }
        } catch (Exception e) {
            log.error("Fail to save the coin: " + e.getMessage(), e);
        }
    }

    private Coin toCoin(final Object oCoin) throws JsonProcessingException {

        String json = mapper.writeValueAsString(oCoin);
        JsonNode rootNode = mapper.readTree(json);

        JsonNode btcNode = rootNode.get("bitcoin");
        Coin coin = null;
        if (Objects.nonNull(btcNode)) {
            coin = Coin.builder()
                    .id(btcNode.asText())
                    .price(ofNullable(btcNode.get("usd")).map(n -> n.asDouble()).orElse(0D))
                    .marketCap(BigDecimal.valueOf(ofNullable(btcNode.get("usd_market_cap")).map(n -> n.asLong()).orElse(0L)))
                    .change(BigDecimal.valueOf(ofNullable(btcNode.get("usd_24h_change")).map(n -> n.asLong()).orElse(0L)))
                    .volume(BigDecimal.valueOf(ofNullable(btcNode.get("usd_24h_vol")).map(n -> n.asLong()).orElse(0L)))
                    .lastUpdated(Long.valueOf(ofNullable(btcNode.get("last_updated_at")).map(n -> n.asLong()).orElse(0L)))
                    .build();
        }
        return coin;
    }
}
