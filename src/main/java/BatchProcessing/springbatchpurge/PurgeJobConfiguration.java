package BatchProcessing.springbatchpurge;

import BatchProcessing.models.Collabotor;
import BatchProcessing.models.PhysicalPerson;
import BatchProcessing.processors.DataSeedProcessor;
import BatchProcessing.springbatchpurge.model.BatchStepExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class PurgeJobConfiguration {


    private String JOB_NAME = "purgeJob";
    private String STEP_NAME = "purgeJobStep";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private static final String QUERY = "Select * from batch_step_execution_context";

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("purgeReader")
    private ItemReader purgeReader;

    @Autowired
    @Qualifier("purgeWriter")
    private ItemWriter purgeWriter;

    public ItemReader<BatchStepExecutionContext> purgeReader() {
        return new JdbcCursorItemReaderBuilder<>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql(QUERY)
                .rowMapper(new BeanPropertyRowMapper(BatchStepExecutionContext.class))
                .build();
    }

    @Bean
    public Step purgeJobStep() {
        return stepBuilderFactory.get(STEP_NAME)
                .<BatchStepExecutionContext , BatchStepExecutionContext>chunk(1)
//                .reader(purgeReader)
                .reader(purgeReader())
                .writer(purgeWriter)
                .build();
    }


    @Bean
    public Job purgeJob (){
        return jobBuilderFactory.get(JOB_NAME)
                .start(purgeJobStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

}

