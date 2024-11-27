package se.nordnet.orderbook.summary.api;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.nordnet.orderbook.common.api.OrderSideRequest;
import se.nordnet.orderbook.order.OrderSide;
import se.nordnet.orderbook.summary.FilterOptions;
import se.nordnet.orderbook.summary.SummaryService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/v1/summary")
class SummaryController {

    private final SummaryService summaryService;

    SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/")
    ResponseEntity<SummaryResponse> getOrderSummary(
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(value = "ticker", required = false) String ticker,
            @RequestParam(value = "orderSide", required = false) OrderSideRequest orderSideRequest) {

        var filterOptions = new FilterOptions(date, ticker, getOrderSide(orderSideRequest));
        var summary = summaryService.getSummary(filterOptions);
        return ResponseEntity.ok(SummaryResponse.of(summary));
    }

    private OrderSide getOrderSide(OrderSideRequest orderSideRequest) {
        return Optional.ofNullable(orderSideRequest)
                .map(OrderSideRequest::convert)
                .orElse(null);
    }
}
