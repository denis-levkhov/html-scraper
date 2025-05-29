package mts.parser.config.webdriver;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebDriverFactory {

    private final WebDriverProperties properties;

    public WebDriver createHeadlessDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(properties.getArgs().toArray(new String[0]));
        return new ChromeDriver(options);
    }
}