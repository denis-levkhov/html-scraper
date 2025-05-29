package mts.parser.webmapper;

import lombok.RequiredArgsConstructor;
import mts.parser.config.scraper.ScraperSelectorsProperties;
import mts.parser.entity.Book;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final ScraperSelectorsProperties selectors;

    public Optional<Book> fromElement(WebElement book) {
        try {
            String title = getTextOrEmpty(book, selectors.getTitle());
            String author = getTextOrEmpty(book, selectors.getAuthor());
            String ratingText = getTextOrEmpty(book, selectors.getRating());

            String rating = ratingText.split(" ")[0];
            String votes = ratingText.contains("—")
                    ? ratingText.split("—")[1].replace("ratings", "").trim()
                    : "";

            String pageUrl = book.findElement(By.cssSelector(selectors.getTitle())).getAttribute("href");

            Book result = Book.builder()
                    .title(title)
                    .author(author)
                    .rating(rating)
                    .votes(votes)
                    .pageUrl(pageUrl)
                    .build();

            return Optional.of(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private String getTextOrEmpty(WebElement parent, String cssSelector) {
        try {
            return parent.findElement(By.cssSelector(cssSelector)).getText();
        } catch (Exception e) {
            return "";
        }
    }
}