package BatchProcessing.springbatchpurge.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BatchJobInstance {

    private long jobInstanceId;

}
