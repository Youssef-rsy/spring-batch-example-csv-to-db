package BatchProcessing.springbatchpurge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class PurgeTestConfiguration {

    Logger logger = LoggerFactory.getLogger(PurgeJobConfiguration.class);

    @Autowired
    protected DataSource dataSource;


    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        // TODO : will be used to set the appropriate rententionMonth
//        registry.add("retentionMonth", LocalDate.now().minus(LocalDate.of(2020,10,01)).lengthOfMonth());
    }

    @Bean
    public void initializePurgeMetadata() throws SQLException {
        logger.info("start configuration");
        // 1. Prepare test dataset
        Resource sqlScript = new ClassPathResource("TestRemoveSpringBatchHistoryTasklet.sql");
        // The JdbcTestUtils is using the deprecated SimpleJdbcTemplate, so we don't have the
        // choice
        ScriptUtils.executeSqlScript(dataSource.getConnection(), sqlScript);

    }


}
