package dev.drugowick.recipes.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class Bootstrap implements CommandLineRunner {

    private DataLoader dataLoader;

    public Bootstrap(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    public void run(String... args) throws Exception {

        dataLoader.saveAll();
    }
}
