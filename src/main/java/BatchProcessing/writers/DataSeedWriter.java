package BatchProcessing.writers;

import BatchProcessing.batchConfiguration.DataSeed;
import BatchProcessing.models.Collabotor;
import BatchProcessing.models.PhysicalPerson;
import BatchProcessing.repository.CollaboratorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeedWriter implements ItemWriter<Collabotor> {


    static Logger logger = LoggerFactory.getLogger(DataSeedWriter.class);

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Override
    public void write(List<? extends Collabotor> collaborators) throws Exception {
        for (Collabotor collaborator:collaborators) {
            collaboratorRepository.save(collaborator);
        }
    }
}
