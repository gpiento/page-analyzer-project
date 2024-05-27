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

    /**
     * Saves a URL entity to the database.
     *
     * @param url The URL entity to save.
     * @throws SQLException If an error occurs while saving the URL entity.
     */
    public static void save(final Url url) throws SQLException {
        String sql = "INSERT INTO urls (name) VALUES (?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("""
                        DB have not returned an id after saving an entity""");
            }
        }
    }

    /**
     * Finds a URL entity by its ID.
     *
     * @param findId The ID of the URL entity to find.
     * @return An optional containing the URL entity if found, or an empty optional if not found.
     * @throws SQLException If an error occurs while retrieving the URL entity.
     */
    public static Optional<Url> find(final Long findId) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, findId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Timestamp createdAt = resultSet.getTimestamp("create_at");
                Url url = new Url(name);
                url.setId(id);
                url.setCreatedAt(createdAt);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    /**
     * Checks if a URL entity with the given name exists in the database.
     *
     * @param name The name of the URL entity to check.
     * @return True if the URL entity exists, false otherwise.
     * @throws SQLException If an error occurs while checking for the URL entity.
     */
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

    /**
     * This method retrieves a list of entities from the database.
     *
     * @return A list of entities.
     * @throws SQLException If an error occurs while retrieving the entities.
     */
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
