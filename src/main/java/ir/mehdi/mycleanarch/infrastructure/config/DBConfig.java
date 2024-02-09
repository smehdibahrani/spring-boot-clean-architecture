package ir.mehdi.mycleanarch.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"ir.mehdi.mycleanarch.infrastructure.entities"})
@EnableJpaRepositories(basePackages = {"ir.mehdi.mycleanarch.infrastructure.repositories"})
public class DBConfig {
}
