package BatchProcessing.readers;

import BatchProcessing.models.PhysicalPerson;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class DataSeedReader implements ItemReader<PhysicalPerson> {
//    @Value("${}")
//    private Resource resource;
    @Override
    public PhysicalPerson read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
