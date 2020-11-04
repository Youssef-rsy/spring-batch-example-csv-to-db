package BatchProcessing.springbatchpurge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Configuration
public class PurgeReaderConfiguration {

    private Logger log = LoggerFactory.getLogger(PurgeReaderConfiguration.class);


    private String QUERY = "Select JOB_INSTANCE_ID from batch_job_instance where job_instance_id In (SELECT JOB_INSTANCE_ID FROM BATCH_JOB_EXECUTION WHERE DATE(CREATE_TIME) <= '%s' )";


    @Value("${retentionMonth:2}")
    private Long retentionMonth;

    @Autowired
    private DataSource dataSource;


    @Bean
    @StepScope
    public ItemReader<Long> purgeReader() {
        JdbcPagingItemReader<Long> reader = new JdbcPagingItemReader<Long>();
        final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
        sqlPagingQueryProviderFactoryBean.setSelectClause("Select JOB_INSTANCE_ID");
        sqlPagingQueryProviderFactoryBean.setFromClause("from batch_job_instance");
        sqlPagingQueryProviderFactoryBean.setWhereClause("job_instance_id In (SELECT JOB_INSTANCE_ID FROM BATCH_JOB_EXECUTION WHERE DATE(CREATE_TIME) <= '"+LocalDate.now().minusDays(retentionMonth)+"' )");
        sqlPagingQueryProviderFactoryBean.setSortKey("job_instance_id");
        try {
            reader.setQueryProvider(Objects.requireNonNull(sqlPagingQueryProviderFactoryBean.getObject()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        reader.setDataSource(dataSource);
        reader.setPageSize(10);
        reader.setRowMapper((resultSet, i) -> resultSet.getLong("job_instance_id"));
        return reader;
    }

}
