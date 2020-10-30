package BatchProcessing.springbatchpurge;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class PurgeReader implements ItemReader<Object> {


    private static final String QUERY = "Select * from collabotor";
    @Autowired
    private DataSource dataSource;

    public Object read() throws Exception {
        return new JdbcCursorItemReaderBuilder<>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql(QUERY)
                .rowMapper(new BeanPropertyRowMapper(Object.class))
                .build().read();
    }

}
