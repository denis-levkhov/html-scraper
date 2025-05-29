package mts.parser.config.scraper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "scraper.goodreads")
public class ScraperProperties {
    private String baseUrl;
    private String bookSelector;
}