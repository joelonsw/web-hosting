package webhosting.webhosting.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HostingDao {
    private final JdbcTemplate jdbcTemplate;

    public HostingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveFile(String userId, String filePath) {
        String sql = "INSERT INTO USERFILE (user_id, file_path) values (?, ?)";
        jdbcTemplate.update(sql, userId, filePath);
    }
}
