package BatchProcessing.springbatchpurge;

import BatchProcessing.springbatchpurge.model.BatchJobInstance;
import BatchProcessing.springbatchpurge.model.SpringDataPurgeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class PurgeWriter implements ItemWriter<BatchJobInstance> {

    private final Logger logger = LoggerFactory.getLogger(PurgeWriter.class);


    private JdbcTemplate jdbcTemplate;


    @Value("${retentionMonth:2}")
    private Long retentionMonth;

    public PurgeWriter(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void write(List<? extends BatchJobInstance> items) throws EmptyResultDataAccessException {
        for (BatchJobInstance batchJobInstance : items) {
            logger.info("Begin Spring Batch Meta data tables Purging for job_instance :  {}", batchJobInstance.getJobInstanceId());

            //  Purge BATCH_STEP_EXECUTION_CONTEXT
            purgeBatchMetadataTable(batchJobInstance, SpringDataPurgeEntity.BATCH_STEP_EXECUTION_CONTEXT.getPurgeQuery(), SpringDataPurgeEntity.BATCH_STEP_EXECUTION_CONTEXT.name());
            //  Purge BATCH_STEP_EXECUTION
            purgeBatchMetadataTable(batchJobInstance, SpringDataPurgeEntity.BATCH_STEP_EXECUTION.getPurgeQuery(), SpringDataPurgeEntity.BATCH_STEP_EXECUTION.name());
            //  Purge BATCH_JOB_EXECUTION_CONTEXT
            purgeBatchMetadataTable(batchJobInstance, SpringDataPurgeEntity.BATCH_JOB_EXECUTION_CONTEXT.getPurgeQuery(), SpringDataPurgeEntity.BATCH_JOB_EXECUTION_CONTEXT.name());
            //  Purge BATCH_JOB_EXECUTION_PARAMS
            purgeBatchMetadataTable(batchJobInstance, SpringDataPurgeEntity.BATCH_JOB_EXECUTION_PARAMS.getPurgeQuery(), SpringDataPurgeEntity.BATCH_JOB_EXECUTION_PARAMS.name());
            //  Purge BATCH_JOB_EXECUTION
            purgeBatchMetadataTable(batchJobInstance, SpringDataPurgeEntity.BATCH_JOB_EXECUTION.getPurgeQuery(), SpringDataPurgeEntity.BATCH_JOB_EXECUTION.name());
            //  Purge BATCH_JOB_INSTANCE
            purgeBatchMetadataTable(batchJobInstance, SpringDataPurgeEntity.BATCH_JOB_INSTANCE.getPurgeQuery(), SpringDataPurgeEntity.BATCH_JOB_INSTANCE.name());

            logger.info("End Spring Batch Meta data tables Purging for the JOB_INSTANCE_ID" +
                    " : {} , job CREATE_TIME before : {}", batchJobInstance.getJobInstanceId(), LocalDate.now().minusDays(retentionMonth));
        }
    }

    private void purgeBatchMetadataTable(BatchJobInstance purgeItem, String purgeQuery, String purgeEntity) {
        // TODO : use minusMonth instead of minusDay
        int rowDeleted = 0;
        if (purgeEntity.equals(SpringDataPurgeEntity.BATCH_JOB_INSTANCE.name()))
            rowDeleted = jdbcTemplate.update(purgeQuery);
        else
            rowDeleted = jdbcTemplate.update(purgeQuery, purgeItem.getJobInstanceId());

        if (rowDeleted > 0) {
            logger.info(" {} row Deleted from {} for the JOB_INSTANCE_ID : {} , job CREATE_TIME before : {}", rowDeleted, purgeEntity, purgeItem.getJobInstanceId(), LocalDate.now().minusDays(retentionMonth));
        }
    }


}
