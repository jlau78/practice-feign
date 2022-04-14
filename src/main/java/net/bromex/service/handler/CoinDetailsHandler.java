package net.bromex.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import net.bromex.model.dto.Coin;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static net.bromex.service.GeckoCoinService.*;

import static java.util.Optional.ofNullable;

@Log4j2
@Component
public class CoinDetailsHandler implements Handler<Coin> {

    public static final ObjectMapper mapper = new ObjectMapper();

    public Coin transform(final String coinId, final Object oCoin) throws ParseException {

        Coin coin = null;
        String json = null;
        try {
            json = mapper.writeValueAsString(oCoin);

            JsonNode rootNode = mapper.readTree(json);

            JsonNode coinNode = rootNode.get(coinId);
            if (Objects.nonNull(coinNode)) {
                coin = Coin.builder()
                        .id(UUID.randomUUID().toString())
                        .name(coinId)
                        .price(ofNullable(coinNode.get("usd")).map(n -> n.asDouble()).orElse(0D))
                        .marketCap(BigDecimal.valueOf(ofNullable(coinNode.get(NODE_USD_MARKET_CAP)).map(n -> n.asLong()).orElse(0L)))
                        .change(BigDecimal.valueOf(ofNullable(coinNode.get(NODE_USD_24h_change)).map(n -> n.asLong()).orElse(0L)))
                        .volume(BigDecimal.valueOf(ofNullable(coinNode.get(NODE_USD_24h_VOL)).map(n -> n.asLong()).orElse(0L)))
                        .lastUpdated(Long.valueOf(ofNullable(coinNode.get(NODE_LAST_UPDATED_AT)).map(n -> n.asLong()).orElse(0L)))
                        .build();
            }

        } catch (JsonProcessingException e) {
            log.error(e);
            throw new ParseException(e.getMessage());
        }

        return coin;
    }
}
