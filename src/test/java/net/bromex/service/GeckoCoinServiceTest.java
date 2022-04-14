package net.bromex.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bromex.client.GeckoClient;
import net.bromex.model.dto.Coin;
import net.bromex.repository.CoinDocumentRepository;
import net.bromex.service.handler.CoinDetailsHandler;
import net.bromex.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;

import static net.bromex.model.dto.CoinId.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GeckoCoinServiceTest {

    @InjectMocks
    GeckoCoinService testObj;

    CoinDetailsHandler mockHandler = new CoinDetailsHandler();
    @Mock
    GeckoClient mockGeckoClient;
    @Mock
    CoinDocumentRepository mockRepository;

    private static final String COIN_PRICE_JSON_RESOURCE = "/data/coin-price-1.json";

    @BeforeEach
    public void setUp() {
       testObj = new GeckoCoinService(mockHandler, mockGeckoClient, mockRepository);
    }

    @Test
    public void test_getAndSave() throws IOException {
        when(mockGeckoClient.getSimplePrice(any(String[].class), any(String[].class), anyBoolean(), anyBoolean(), anyBoolean(), anyBoolean())).thenReturn(readCoinResource());

        testObj.saveCoin(new String[]{BITCOIN.getGeckoId(), SOLANA.getGeckoId(), ETHEREUM.getGeckoId()}, readCoinResource());

        Mockito.verify(mockRepository, atLeast(3)).insert(Mockito.any(Coin.class));
    }


    Object readCoinResource() throws IOException {
        return Utils.readJsonFromResource(COIN_PRICE_JSON_RESOURCE);
    }
}
