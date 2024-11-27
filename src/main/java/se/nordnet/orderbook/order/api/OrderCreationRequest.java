package se.nordnet.orderbook.order.api;

import org.hibernate.validator.constraints.Length;
import se.nordnet.orderbook.common.api.OrderSideRequest;

import jakarta.validation.constraints.Min;
import org.springframework.lang.NonNull;
import java.math.BigDecimal;
import java.util.UUID;

record OrderCreationRequest(
        @NonNull UUID customerId,
        @Length(min = 1) String ticker,
        @NonNull OrderSideRequest orderSide,
        @NonNull @Min(0) long volume,
        @NonNull PriceRequest price) {

    record PriceRequest(
            @NonNull String currency,
            @NonNull @Min(0) BigDecimal price) {
    }
}
