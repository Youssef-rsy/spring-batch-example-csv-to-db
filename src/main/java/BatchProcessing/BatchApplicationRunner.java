package BatchProcessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@EnableBatchProcessing
public class BatchApplicationRunner implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(BatchApplicationRunner.class);
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info(" --------------------------------------   Starting job   -------------------------------------- ");
        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put("time",new JobParameter(String.valueOf(LocalDateTime.now())));
        JobParameters jobParameters = new JobParameters(parameters);
        jobLauncher.run(job,jobParameters);
        logger.info(" --------------------------------------   Completing job -------------------------------------- ");

    }
}
