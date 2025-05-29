package mts.parser.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import mts.parser.service.GoodreadsScraperService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goodreads")
@RequiredArgsConstructor
@Validated
public class GoodreadsScraperController {

    private final GoodreadsScraperService goodreadsScraperService;

    @PostMapping("/scrape")
    public ResponseEntity<String> scrapeBooks(@RequestParam(defaultValue = "1")
                                              @Min(value = 1, message = "Количество страниц должно быть не меньше 1")
                                              int pages
    ) {
        goodreadsScraperService.scrapeGoodreadsAsync(pages);
        return ResponseEntity.ok(String.format("Парсинг для %s страниц - завершён.", pages));
    }
}