package hexlet.code.repository;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class UrlsRepository extends BaseRepository {

    public static void save(final Product product) throws SQLException {
        String sql = "INSERT INTO products (title, price) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getTitle());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static Optional<Product> find(final Long id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                int price = resultSet.getInt("price");
                Product product = new Product(title, price);
                product.setId(id);
                return Optional.of(product);
            }
            return Optional.empty();
        }
    }

    public static <Car> List<Product> getEntities() throws SQLException {
        String sql = "SELECT * FROM products";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            ArrayList<Product> result = new ArrayList<Product>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                int price = resultSet.getInt("price");
                Product product = new Product(title, price);
                product.setId(id);
                result.add(product);
            }
            return result;
        }
    }

}
