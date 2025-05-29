package mts.parser.config.scraper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "scraper.goodreads.selectors")
public class ScraperSelectorsProperties {
    private String book;
    private String title;
    private String author;
    private String rating;
}