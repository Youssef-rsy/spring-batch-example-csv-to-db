package BatchProcessing.processors;

import BatchProcessing.models.Collabotor;
import BatchProcessing.models.PhysicalPerson;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
public class DataSeedProcessor implements ItemProcessor<PhysicalPerson, Collabotor> {
    @Override
    public Collabotor process(PhysicalPerson physicalPerson) throws Exception {
        return mapPhysicalPersonToCollaborator(physicalPerson);
    }

    private Collabotor mapPhysicalPersonToCollaborator(PhysicalPerson physicalPerson){
        Collabotor collabotor = new Collabotor();
        collabotor.setAge((int) (new Date().getTime() - physicalPerson.getBirthDate().getTime()));
        collabotor.setBirthDate(physicalPerson.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        collabotor.setClassStatus(physicalPerson.getClassStatus());
        collabotor.setFirstName(physicalPerson.getFirstName());
        collabotor.setLastName(physicalPerson.getLastName());
        collabotor.setIntegrationDate(physicalPerson.getIntegrationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        collabotor.setJob(physicalPerson.getJob());
        collabotor.setStatus(physicalPerson.getStatus());
        collabotor.setCollaboratorId(UUID.randomUUID());
        return collabotor;
    }
}
