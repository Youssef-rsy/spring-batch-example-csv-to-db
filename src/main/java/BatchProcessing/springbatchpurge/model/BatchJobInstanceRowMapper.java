package BatchProcessing.springbatchpurge.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BatchJobInstanceRowMapper implements RowMapper {


    @Override
    public BatchJobInstance mapRow(ResultSet resultSet, int i) throws SQLException {
        return BatchJobInstance.builder().jobInstanceId(resultSet.getLong("job_instance_id")).build();
    }
}
