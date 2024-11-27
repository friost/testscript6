package se.nordnet.orderbook.common.repository;

import se.nordnet.orderbook.instrument.Instrument;

import java.util.List;

public interface InstrumentRepository {
    List<Instrument> getInstrumentList();
}
