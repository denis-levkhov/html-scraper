package mts.parser.controller;

import lombok.RequiredArgsConstructor;
import mts.parser.service.GoodreadsScraperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goodreads")
@RequiredArgsConstructor
public class GoodreadsScraperController {

    private final GoodreadsScraperService goodreadsScraperService;

    @PostMapping("/scrape")
    public ResponseEntity<String> scrapeBooks(@RequestParam(defaultValue = "1") int pages) {
        if (pages < 1) {
            return ResponseEntity.badRequest().body("Количество страниц должно быть больше 0");
        }
        goodreadsScraperService.scrapeGoodreadsAsync(pages);
        return ResponseEntity.ok("Парсинг запущен асинхронно для " + pages + " страниц.");
    }
}