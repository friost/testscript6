package se.nordnet.orderbook.instrument.api;

import se.nordnet.orderbook.instrument.Instrument;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

record InstrumentResponse(
        UUID id,
        String name,
        String ticker,
        BigDecimal lastPrice,
        String currency) {

    static InstrumentResponse of(Instrument instrument) {
        return new InstrumentResponse(
                instrument.id(),
                instrument.name(),
                instrument.ticker(),
                instrument.lastPrice(),
                instrument.currency()
        );
    }
}
