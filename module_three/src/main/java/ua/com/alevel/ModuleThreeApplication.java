package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ua.com.alevel.config.jpa.JpaConfig;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class ModuleThreeApplication {

    private final JpaConfig jpaConfig;

    public ModuleThreeApplication(JpaConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    public static void main(String[] args) {
        System.out.println("/*\n" +
                "* Refactor of original project https://github.com/AnastasiaMaksimovskaya/nix_10/tree/master/module3\n" +
                "* By AnastasiaMaksimovskaya\n" +
                "* */");
        SpringApplication.run(ModuleThreeApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDB() {
        jpaConfig.connect();
    }
}
