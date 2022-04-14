package net.bromex.model.dto;

public enum CoinId {

    BITCOIN("bitcoin"), SOLANA("solana"), ETHEREUM("ethereum");

    private String geckoId;

    CoinId(final String id) {
        this.geckoId = id;
    }

    public String getGeckoId() {
        return geckoId;
    }

}
