package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import ua.com.alevel.config.jpa.DatasourceProperties;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.service.FileTransferService;

@SpringBootApplication
@EnableConfigurationProperties({DatasourceProperties.class})
public class WebJdbcApplication {

    private final JpaConfig jpaConfig;
    private final FileTransferService fileTransferService;

    public WebJdbcApplication(JpaConfig jpaConfig, FileTransferService fileTransferService) {
        this.jpaConfig = jpaConfig;
        this.fileTransferService = fileTransferService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebJdbcApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDB() {
        jpaConfig.connect();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDownloadsDir() {
        fileTransferService.init();
    }
}
