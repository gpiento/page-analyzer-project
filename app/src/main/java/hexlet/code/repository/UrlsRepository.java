package hexlet.code.repository;

import hexlet.code.model.Url;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
public class UrlsRepository extends BaseRepository {

    public static Url saveUrl(final Url url) throws SQLException {

        String sql = "INSERT INTO urls (name) VALUES (?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, url.getName());
//            preparedStatement.setTimestamp(2, url.getCreatedAt());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1));
                return url;
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static Optional<Url> findById(final Long findId) throws SQLException {

        String sql = "SELECT * FROM urls WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, findId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Url url = new Url(id, name, createdAt);
                return Optional.of(url);
            }

            return Optional.empty();
        }
    }

    public static boolean existsByName(final String name) throws SQLException {

        String sql = "SELECT * FROM urls WHERE name = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("name").equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Url> getEntities() throws SQLException {

        String sql = "SELECT * FROM urls";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet resultSet = stmt.executeQuery();
            List<Url> result = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Url url = new Url(name);
                url.setId(id);
                result.add(url);
            }
            return result;
        }
    }
}
