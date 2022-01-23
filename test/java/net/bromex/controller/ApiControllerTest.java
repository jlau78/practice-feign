package net.bromex.controller;

import net.bromex.client.GeckoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import net.bromex.model.dto.CoinResponse;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;

@ExtendWith(MockitoExtension.class)
public class ApiControllerTest {

    @Mock
    GeckoClient geckoClient;

    @InjectMocks
    ApiController testObj;

    private static final String COIN_ID_BTC = "bitcoin";
    private static final String COIN_ID_ETH = "ethereum";
    private static final String COIN_ID_SOL = "solana";
    private static final String CUR_USD = "usd";
    private static final String CUR_GBP = "gbp";

    @Mock
    ResponseEntity<CoinResponse> responseCoinSuccess;

    @BeforeEach
    public void setUp() {
        testObj = new ApiController(geckoClient);

        when(geckoClient.getSimplePrice(any(String[].class), any(String[].class), anyBoolean())).thenReturn(buildCoinResponse());
    }

    @Test
    public void testGetCoinSuccess() {
        ResponseEntity<CoinResponse> response = testObj.getCoins(new String[]{COIN_ID_BTC, COIN_ID_ETH, COIN_ID_SOL}
                , new String[]{CUR_GBP, CUR_USD}
                , Boolean.TRUE);

        CoinResponse coinResponse = buildCoinResponse();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Arrays.asList(response.getBody().getIds()).contains(COIN_ID_ETH));
//        assertEquals(coinResponse.getIds(), response.getBody().getIds());
    }

    private CoinResponse buildCoinResponse() {
        return CoinResponse.builder()
                .ids(new String[]{COIN_ID_BTC, COIN_ID_ETH, COIN_ID_SOL})
                .currencies(new String[]{CUR_USD, CUR_GBP})
                .include24HrChange(true)
                .include24HrVol(true)
                .includeMarketCap(true)
                .build();

    }
}
