package mts.parser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mts.parser.entity.Book;
import mts.parser.repository.BookRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodreadsScraperService {

    private final BookRepository bookRepository;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void scrapeGoodreadsAsync(int pages) {
        List<CompletableFuture<Void>> futures = IntStream.rangeClosed(1, pages)
                .mapToObj(page ->
                        CompletableFuture.runAsync(() -> parsePage(page), threadPoolTaskExecutor)
                ).toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        log.info("Парсинг завершён");
    }

    private void parsePage(int page) {
        WebDriver driver = createHeadlessDriver();

        try {
            String url = "https://www.goodreads.com/list/show/1.Best_Books_Ever?page=" + page;
            driver.get(url);

            List<WebElement> books = driver.findElements(By.cssSelector("tr[itemtype='http://schema.org/Book']"));

            List<Book> parsedBooks = books.stream()
                    .map(this::tryParseBook)
                    .filter(Objects::nonNull)
                    .toList();

            bookRepository.saveAll(parsedBooks);

            long successCount = parsedBooks.size();
            long failCount = books.size() - successCount;

            log.info("Страница {}: успешно = {}, ошибки = {}", page, successCount, failCount);

        } catch (Exception e) {
            log.error("Ошибка парсинга страницы {}: {}", page, e.getMessage(), e);
        } finally {
            driver.quit();
        }
    }

    private WebDriver createHeadlessDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90 Safari/537.36");
        return new ChromeDriver(options);
    }

    private Book tryParseBook(WebElement book) {
        try {
            return parseBook(book);
        } catch (Exception e) {
            log.warn("Ошибка парсинга книги: {}", e.getMessage(), e);
            return null;
        }
    }

    private Book parseBook(WebElement book) {
        String title = getTextOrEmpty(book, ".bookTitle");
        String author = getTextOrEmpty(book, ".authorName");
        String ratingText = getTextOrEmpty(book, ".minirating");
        String rating = ratingText.split(" ")[0];
        String votes = ratingText.contains("—") ? ratingText.split("—")[1].replace("ratings", "").trim() : "";
        String pageUrl = book.findElement(By.cssSelector(".bookTitle")).getAttribute("href");

        return Book.builder()
                .title(title)
                .author(author)
                .rating(rating)
                .votes(votes)
                .pageUrl(pageUrl)
                .build();
    }

    private String getTextOrEmpty(WebElement parent, String cssSelector) {
        try {
            return parent.findElement(By.cssSelector(cssSelector)).getText();
        } catch (Exception e) {
            return "";
        }
    }
}