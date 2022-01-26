package ua.com.alevel;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import ua.com.alevel.config.jpa.DatasourceProperties;
import ua.com.alevel.config.jpa.JpaConfig;

import java.sql.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties({DatasourceProperties.class})
public class WebJdbcApplication {

    private final JpaConfig jpaConfig;

    public WebJdbcApplication(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebJdbcApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDB() {
        jpaConfig.connect();
    }
}
