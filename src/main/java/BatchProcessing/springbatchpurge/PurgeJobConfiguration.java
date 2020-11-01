package BatchProcessing.springbatchpurge;

import BatchProcessing.springbatchpurge.model.BatchJobInstance;
import BatchProcessing.springbatchpurge.model.BatchJobInstanceRowMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;

@Component
public class PurgeJobConfiguration {


    private String JOB_NAME = "purgeJob";
    private String STEP_NAME = "purgeJobStep";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("purgeReader")
    private ItemReader purgeReader;

    @Autowired
    @Qualifier("purgeWriter")
    private ItemWriter purgeWriter;


    @Bean
    public Step purgeJobStep() {
        return stepBuilderFactory.get(STEP_NAME)
                .<BatchJobInstance, BatchJobInstance>chunk(2)
                .reader(purgeReader)
                .writer(purgeWriter)
                .build();
    }

    @Bean
    public Job purgeJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(purgeJobStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

}

