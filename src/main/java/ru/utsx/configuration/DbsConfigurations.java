package ru.utsx.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.utsx.core.TransactionalCachedKeyValueStore;
import ru.utsx.dbs.IMasterDatabase;
import ru.utsx.dbs.MasterDatabase;

@Configuration
public class DbsConfigurations {
    @Bean
    public IMasterDatabase<String, String> masterDatabase() {
        return new MasterDatabase<>(
                new TransactionalCachedKeyValueStore<>()
        );
    }
}
