package se.nordnet.orderbook.common.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import se.nordnet.orderbook.instrument.Instrument;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Repository
//@Profile("database")
public class InstrumentDatabaseRepository implements InstrumentRepository {

    private static final Logger log = LoggerFactory.getLogger(InstrumentDatabaseRepository.class);

    private static final List<String> INSTRUMENT_FIELDS_LIST = List.of(
            "instrument_id",
            "instrument_name",
            "ticker",
            "last_price",
            "currency");

    private static final RowMapper<Instrument> INSTRUMENT_ROW_MAPPER = (rs, i) ->
            new Instrument(
                    UUID.fromString(requireNonNull(rs.getString("instrument_id"))),
                    requireNonNull(rs.getString("instrument_name")),
                    requireNonNull(rs.getString("ticker")),
                    requireNonNull(rs.getBigDecimal("last_price")),
                    requireNonNull(rs.getString("currency"))
            );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public InstrumentDatabaseRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = jdbcTemplate;
    }

    public List<Instrument> getInstrumentList() {
        var list = namedParameterJdbcTemplate.query("select * from instruments",
                INSTRUMENT_ROW_MAPPER);
        return list;
    }
}
