package se.nordnet.orderbook.instrument.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.nordnet.orderbook.common.repository.InstrumentRepository;

import java.util.List;

@RestController
@RequestMapping("/v1/instrumentList")
class InstrumentController {

    private final InstrumentRepository instrumentRepository;

    InstrumentController(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    @GetMapping("")
    ResponseEntity<List<InstrumentResponse>> getInstrumentList() {

        var mappedList = instrumentRepository.getInstrumentList().stream()
                .map(InstrumentResponse::of)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(mappedList);
    }
}
