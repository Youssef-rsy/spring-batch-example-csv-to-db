package BatchProcessing.writers;

import BatchProcessing.springbatchpurge.model.BatchHistoryCleanerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

@Component
public class PurgeWriterConfiguration implements ItemWriter<Long> {

    private final Logger logger = LoggerFactory.getLogger(PurgeWriterConfiguration.class);

    private JdbcTemplate jdbcTemplate;

    @Value("${retentionMonth:2}")
    private Long retentionMonth;

    public PurgeWriterConfiguration(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void write(List<? extends Long> items) throws EmptyResultDataAccessException {
        items.forEach(batchJobInstance -> {

            logger.info("Begin Spring Batch Meta data tables Purging for job_instance :  {}", batchJobInstance);

            //  Purge BATCH_STEP_EXECUTION_CONTEXT
            purgeBatchMetadataTable(batchJobInstance, BatchHistoryCleanerEntity.BATCH_STEP_EXECUTION_CONTEXT);
            //  Purge BATCH_STEP_EXECUTION
            purgeBatchMetadataTable(batchJobInstance, BatchHistoryCleanerEntity.BATCH_STEP_EXECUTION);
            //  Purge BATCH_JOB_EXECUTION_CONTEXT
            purgeBatchMetadataTable(batchJobInstance, BatchHistoryCleanerEntity.BATCH_JOB_EXECUTION_CONTEXT);
            //  Purge BATCH_JOB_EXECUTION_PARAMS
            purgeBatchMetadataTable(batchJobInstance, BatchHistoryCleanerEntity.BATCH_JOB_EXECUTION_PARAMS);
            //  Purge BATCH_JOB_EXECUTION
            purgeBatchMetadataTable(batchJobInstance, BatchHistoryCleanerEntity.BATCH_JOB_EXECUTION);
            //  Purge BATCH_JOB_INSTANCE
            purgeBatchMetadataTable(batchJobInstance, BatchHistoryCleanerEntity.BATCH_JOB_INSTANCE);

            logger.info("End Spring Batch Meta data tables Purging for the JOB_INSTANCE_ID" +
                    " : {} , job CREATE_TIME before : {}", batchJobInstance, LocalDate.now().minusDays(retentionMonth));
        });
    }


    private void purgeBatchMetadataTable(long jobInstanceId, BatchHistoryCleanerEntity batchHistoryCleanerEntity) {
        // TODO : use minusMonth instead of minusDay
        int rowDeleted  = jdbcTemplate.update(batchHistoryCleanerEntity.getPurgeQuery(), jobInstanceId);
        if (rowDeleted > 0) {
            logger.info(" {} row Deleted from {} for the JOB_INSTANCE_ID : {} , job CREATE_TIME before : {}", rowDeleted, batchHistoryCleanerEntity.name(), jobInstanceId, LocalDate.now().minusDays(retentionMonth));
        }
    }


}
