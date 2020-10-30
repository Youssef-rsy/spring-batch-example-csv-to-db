package BatchProcessing.batchConfiguration;

import BatchProcessing.models.Collabotor;
import BatchProcessing.models.PhysicalPerson;
import BatchProcessing.processors.DataSeedProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
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
import org.springframework.stereotype.Component;

@Component
public class DataSeed {


    private String JOB_NAME = "datasSeedJob";
    private String STEP_NAME = "datasSeedJobStep";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("dataSeedWriter")
    private ItemWriter itemWriter;

    @Bean
    public FlatFileItemReader<PhysicalPerson> reader(){
        return new FlatFileItemReaderBuilder<PhysicalPerson>()
                .name("personItemReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("input_data_v0.csv"))
                .lineMapper(lineMapper())
                .build();
    }

    @Bean
    public LineMapper<PhysicalPerson> lineMapper() {
        DefaultLineMapper<PhysicalPerson> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer =new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        // Date parsing logic has been added
        lineTokenizer.setNames("firstName", "lastName",  "birthDate", "job",  "classStatus",  "status", "integrationDate");
        lineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper<PhysicalPerson> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        HashMap<Class, PropertyEditor> customEditors = new HashMap<>();
//        customEditors.put(LocalDate.class, new PropertyEditorSupport() {
//            @Override
//            public void setAsText(String text) throws IllegalArgumentException {
//                setValue(LocalDate.parse(text, DateTimeFormatter.ISO_DATE_TIME));
//            }
//        });
//
//        // Bean Wrapper Field SetMapper
//        beanWrapperFieldSetMapper.setCustomEditors(customEditors);
        beanWrapperFieldSetMapper.setTargetType(PhysicalPerson.class);
        lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return lineMapper;
    }

    @Bean
    public Step dataSeedStep(){
        return stepBuilderFactory.get(STEP_NAME)
                .<PhysicalPerson , Collabotor>chunk(2)
                .reader(reader())
                .processor(new DataSeedProcessor())
                .writer(itemWriter)
                .build();
    }


    @Bean
    public Job dataSeedJob(){
        return jobBuilderFactory.get(JOB_NAME)
                .start(dataSeedStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

}

