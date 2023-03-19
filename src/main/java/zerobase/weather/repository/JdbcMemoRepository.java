package zerobase.weather.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemoRepository {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    // DataSource는 application.properties의 datasource 정보를 가지고 있음
    public JdbcMemoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // 메모 저장
    public Memo save(Memo memo) {
        String sql = "insert into memo values(?, ?)";
        jdbcTemplate.update(sql, memo.getId(), memo.getText());
        return memo;
    }


    // 메모 조회 (전체)
    public List<Memo> findAll() {
        String sql = "select * from memo";
        return jdbcTemplate.query(sql, memoRowMapper());
    }


    // 메모 조회 (id 기준)
    public Optional<Memo> findById(int id) {
        String sql = "select * from memo where id = ?";
        return jdbcTemplate.query(sql, memoRowMapper(), id).stream().findFirst();
    }


    // RowMapper는 jdbc를 통해 데이터베이스에서 데이터를 가져오면 JSON 형식으로 가져옴
    private RowMapper<Memo> memoRowMapper() {
        return ((rs, rowNum) -> new Memo(
                rs.getInt("id"),
                rs.getString("text")));
    }

}
