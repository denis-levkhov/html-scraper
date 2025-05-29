package mts.parser.config.webdriver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "webdriver")
public class WebDriverProperties {
    private List<String> args = new ArrayList<>();
}