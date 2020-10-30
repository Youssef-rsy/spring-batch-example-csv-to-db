package BatchProcessing.springbatchpurge.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BatchStepExecutionContextRowMapper implements RowMapper {


    @Override
    public BatchStepExecutionContext mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
