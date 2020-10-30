package BatchProcessing.springbatchpurge;

import BatchProcessing.springbatchpurge.model.BatchStepExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurgeWriter implements ItemWriter <BatchStepExecutionContext>{

    private final Logger logger = LoggerFactory.getLogger(PurgeWriter.class) ;


    @Override
    public void write(List<? extends BatchStepExecutionContext> items) throws Exception {
        logger.info("============================");
        logger.info(String.valueOf(items));
        logger.info(String.valueOf(items.get(0)));
    }
}
