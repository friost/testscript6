package se.nordnet.orderbook.instrument;

import java.util.UUID;
import java.math.BigDecimal;

public record Instrument(
        UUID id,
        String name,
        String ticker,
        BigDecimal lastPrice,
        String currency) {
}
