package com.jmgits.sample;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringRunner.class)
@ActiveProfiles("it")
@SqlGroup({
        @Sql(scripts = "/sql/_clean_up.sql", executionPhase = AFTER_TEST_METHOD)
})
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {ITApplication.class})
public abstract class AbstractIT {
}
