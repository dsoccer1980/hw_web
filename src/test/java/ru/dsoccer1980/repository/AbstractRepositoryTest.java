package ru.dsoccer1980.repository;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@DataMongoTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ComponentScan({"ru.dsoccer1980.repository", "ru.dsoccer1980.util.events"})
public class AbstractRepositoryTest {
}
