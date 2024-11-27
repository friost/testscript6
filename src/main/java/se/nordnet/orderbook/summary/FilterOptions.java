package se.nordnet.orderbook.summary;

import org.springframework.lang.Nullable;
import se.nordnet.orderbook.order.OrderSide;

import java.time.LocalDate;

public record FilterOptions(
        @Nullable LocalDate date,
        @Nullable String ticker,
        @Nullable OrderSide orderSide) {
}
