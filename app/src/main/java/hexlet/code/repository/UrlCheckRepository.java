package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UrlCheckRepository extends BaseRepository {

    public static UrlCheck saveUrlCheck(final UrlCheck urlCheck) throws SQLException {

        String sql = "INSERT INTO url_checks (url_id, status_code, h1, title, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, urlCheck.getUrlId());
            preparedStatement.setInt(2, urlCheck.getStatusCode());
            preparedStatement.setString(3, urlCheck.getH1());
            preparedStatement.setString(4, urlCheck.getTitle());
            preparedStatement.setString(5, urlCheck.getDescription());
            log.info("Executing save to UrlCheckRepository SQL: {}", sql);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getLong(1));
                return urlCheck;
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<UrlCheck> getEntities(final Long urlId) throws SQLException {

        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY create_at DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, urlId);
            log.info("Executing getEntities SQL: {}", sql);
            ResultSet resultSet = stmt.executeQuery();

            List<UrlCheck> result = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                int statusCode = resultSet.getInt("status_code");
                String h1 = resultSet.getString("h1");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Timestamp createdAt = resultSet.getTimestamp("create_at");
                UrlCheck urlCheck = new UrlCheck(id, urlId, statusCode, h1, title, description, createdAt);
                result.add(urlCheck);
            }
            return result;
        }
    }
}
