package BatchProcessing.springbatchpurge.model;

import lombok.Data;

@Data
public class BatchStepExecutionContext {
    private long step_execution_id;
    private String short_context;
    private String serialized_context;

}
