package net.bromex.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class Coin {

    private String id;
    private String name;
    private Double price;
    private BigDecimal marketCap;
    private BigDecimal volume;
    private BigDecimal change;
    private Long lastUpdated;

}
