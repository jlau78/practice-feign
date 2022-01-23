package net.bromex.play;

import net.bromex.model.dto.CoinResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UtilsTest {

    Map<String, Double> coinMap;

    private static final String COIN_ID_BTC = "bitcoin";
    private static final String COIN_ID_ETH = "ethereum";
    private static final String COIN_ID_SOL = "solana";
    private static final String CUR_USD = "usd";
    private static final String CUR_GBP = "gbp";


    @BeforeEach
    public void setUp() {
        coinMap = new HashMap<>();

        coinMap.put(COIN_ID_BTC, 35000.00D);
        coinMap.put(COIN_ID_ETH, 2500.00D);
        coinMap.put(COIN_ID_SOL, 100D);
    }

    @Test
    public void testJava8Map() {

        coinMap.entrySet()
                .stream()
                .forEach( (k) -> System.out.println("key:"+k.getKey()+", value:"+ coinMap.get(k.getKey())))
        ;

        String coin = COIN_ID_ETH;
        List<Double> ethValue = coinMap.entrySet().stream()
                .filter(e -> e.getKey() == coin)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        assertTrue(ethValue.contains(2500D));

    }

    @Test
    public void testJavaMapFindFirst() {
        Optional<Double> val = coinMap.entrySet().stream()
                .filter(e -> e.getKey() == COIN_ID_SOL)
                .map(Map.Entry::getValue)
                .findFirst();

        assertEquals(100D, val.get());
    }

    @Test
    public void testConcurrency() {

    }

    @Test
    public void testComparableSort() {

    }

    @Test
    public void testCompletableFuture() {
        CompletableFuture<CoinResponse> future = new CompletableFuture().thenRunAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        });

        try {
            CoinResponse coin = future.get(1000L, TimeUnit.MILLISECONDS);
            System.out.println(coin);
            assertNotNull(coin);

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            fail("Fail running testCompletableFuture:"+e.getCause());
        }

    }
}
