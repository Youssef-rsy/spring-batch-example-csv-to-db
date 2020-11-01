package BatchProcessing.springbatchpurge;

import BatchProcessing.springbatchpurge.model.BatchJobInstance;
import BatchProcessing.springbatchpurge.model.BatchJobInstanceRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;

@Configuration
public class PurgeReaderConfiguration {


    private String QUERY = "Select * from batch_job_instance where job_instance_id In (SELECT JOB_INSTANCE_ID FROM BATCH_JOB_EXECUTION WHERE DATE(CREATE_TIME) <= '%s' )";


    @Value("${retentionMonth:2}")
    private Long retentionMonth;

    @Autowired
    private DataSource dataSource;


    @Bean
    public ItemReader<BatchJobInstance> purgeReader() {
        return new JdbcCursorItemReaderBuilder<>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql(String.format(QUERY,LocalDate.now().minusDays(retentionMonth)))
                .rowMapper(new BatchJobInstanceRowMapper())
                .build();
    }

}
