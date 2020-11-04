package BatchProcessing.springbatchpurge;

import BatchProcessing.writers.PurgeWriterConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes=PurgeTestConfiguration.class)
public class PurgeWriterTest {


    @Autowired
    private PurgeWriterConfiguration purgeWriter;

    @Autowired
    protected JobExplorer jobExplorer;

    @Test
    public void test(){
        assertTrue(true);
    }

    @Ignore
    @Test
    public void execute() throws Exception {


        // 2. Check the dataset before removing history
        List<JobInstance> jobInstances = jobExplorer.getJobInstances("purgeJobTest", 0, 5);
        assertEquals("2 job instances before the purge", 2, jobInstances.size());

        // 3. Execute the tested method
        final ChunkContext chunkContext = new ChunkContext(null);
        StepExecution stepExecution = new StepExecution("step1", null);
        StepContribution stepContribution = new StepContribution(stepExecution);
//        purgeWriter.write();

        // 4. Assertions
        assertEquals("6 lines should be deleted from the history", 6,
                stepContribution.getWriteCount());
        jobInstances = jobExplorer.getJobInstances("jobTest", 0, 5);
        assertEquals("Just a single job instance after the delete", 1, jobInstances.size());
        JobInstance jobInstance = jobInstances.get(0);
        assertEquals("Only the job instance number 2 should remain into the history",
                Long.valueOf(102), jobInstance.getId());
    }
}