package net.bromex.play;

import lombok.extern.log4j.Log4j2;
import net.bromex.model.dto.CoinResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
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

        coinMap.put(COIN_ID_ETH, 2500.00D);
        coinMap.put(COIN_ID_SOL, 100D);
        coinMap.put(COIN_ID_BTC, 35000.00D);
    }

    @Test
    public void testJava8Map() {

        coinMap.entrySet()
                .stream()
                .forEach( (k) -> log.info("key:{}, value:{}", k.getKey(), coinMap.get(k.getKey())))
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

        log.info("List CoinMap values....");
        for (Map.Entry<String, Double> entry: coinMap.entrySet()) {
            log.info("{}:{}", entry.getKey(), entry.getValue());
        }

        log.info("Sort coinMap by key descending....");
        coinMap.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByKey().reversed())
                .forEachOrdered(c -> log.info("{}:{}", c.getKey(), c.getValue()));


        log.info("Sort value descending....");
        coinMap.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEachOrdered(c -> log.info("{}:{}", c.getKey(), c.getValue()));

        log.info("Sort using Comparator....");
        coinMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEachOrdered(c -> log.info("{}:{}", c.getKey(), c.getValue()));
    }

    @Test
    public void testCompletableFuture() {
        CompletableFuture<CoinResponse> future = new CompletableFuture().thenRunAsync(new Runnable() {
            @Override
            public void run() {
                log.info("hello");
            }
        });

        try {
            CoinResponse coin = future.get(500L, TimeUnit.MILLISECONDS);
            log.info(coin);

            assertNotNull(coin);

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
//            fail("Fail running testCompletableFuture:"+e.getCause());
        }
    }


    @Test
    public void testArrayChunk() {
        int[] intArr = {1, 2, 3, 4, 5, 6, 7};
        int chunk = 2;
        List chunkedList = new ArrayList();

        List<Integer> lInt = IntStream.of(intArr).boxed().collect(Collectors.toList());

    }


}
