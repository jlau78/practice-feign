package net.bromex.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CoinResponse implements Comparable<CoinResponse> {

    private String [] ids;
    private String [] currencies;
    private Boolean includeMarketCap;
    private Boolean include24HrVol;
    private Boolean include24HrChange;

    @Override
    public int compareTo(CoinResponse o) {
        return 0;
    }
}
