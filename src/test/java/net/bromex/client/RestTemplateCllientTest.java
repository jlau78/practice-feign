package net.bromex.client;

import net.bromex.model.dto.CoinResponse;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class RestTemplateCllientTest {

    @Mock
    RestTemplate mockRestTemplate;

    @InjectMocks
    RestTemplateClient testObj;

    @Mock
    CoinResponse mockCoinReposne;
    @Mock
    ResponseEntity<CoinResponse> mockResponse;

    private static final String SIMPLE_PRICE_URI = "https;//api.gecko.com/api/v3/simple/price";

    @BeforeEach
    public void setUp() {
        HttpEntity<?> mockPayload = Mockito.mock(HttpEntity.class);
        testObj = new RestTemplateClient(mockRestTemplate);
        when(mockRestTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(CoinResponse.class))).thenReturn(mockResponse);
        when(mockResponse.getBody()).thenReturn(mockCoinReposne);
    }

    @Test
    public void testHttpGet() {
        Map<String, Object> params = new HashMap<>();
        Object response = testObj.httpGet(SIMPLE_PRICE_URI, params, CoinResponse.class);

        assertNotNull(testObj);
        assertNotNull(response);
        verify(mockRestTemplate).exchange(SIMPLE_PRICE_URI, HttpMethod.GET, new HttpEntity(params), CoinResponse.class);

    }

}
