package mts.parser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mts.parser.config.scraper.ScraperProperties;
import mts.parser.config.webdriver.WebDriverFactory;
import mts.parser.entity.Book;
import mts.parser.repository.BookRepository;
import mts.parser.webmapper.BookMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Slf4j
@Service
public class GoodreadsScraperService {

    private final BookRepository bookRepository;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final WebDriverFactory webDriverFactory;
    private final BookMapper bookMapper;
    private final ScraperProperties scraperProperties;

    public void scrapeGoodreadsAsync(int pages) {
        List<CompletableFuture<Void>> futures = IntStream.rangeClosed(1, pages)
                .mapToObj(page -> CompletableFuture.runAsync(() -> parsePage(page), threadPoolTaskExecutor))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        log.info("Парсинг завершён");
    }

    private void parsePage(int page) {
        WebDriver driver = webDriverFactory.createHeadlessDriver();

        try {
            String url = buildUrlForPage(page);
            driver.get(url);

            List<WebElement> bookElements = driver.findElements(By.cssSelector(scraperProperties.getBookSelector()));

            List<Book> parsedBooks = bookElements.stream()
                    .map(this::mapBookSafely)
                    .flatMap(Optional::stream)
                    .toList();

            bookRepository.saveAll(parsedBooks);

            long successCount = parsedBooks.size();
            long failCount = bookElements.size() - successCount;

            log.info("Страница {}: успешно = {}, ошибки = {}", page, successCount, failCount);

        } catch (Exception e) {
            log.error("Ошибка парсинга страницы {}: {}", page, e.getMessage(), e);
        } finally {
            driver.quit();
        }
    }

    private String buildUrlForPage(int page) {
        return scraperProperties.getBaseUrl() + page;
    }

    private Optional<Book> mapBookSafely(WebElement element) {
        return bookMapper.fromElement(element);
    }
}