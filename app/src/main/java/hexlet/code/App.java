package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

@Slf4j
public final class App {

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    public static Javalin getApp() {

        HikariConfig hikariConfig = new HikariConfig();
        String dbUrl = System.getenv().getOrDefault("JDBC_DATABASE_URL",
                "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1");
        hikariConfig.setJdbcUrl(dbUrl);

        System.out.println("SYSTEM VAR: " + System.getenv("JDBC_DATABASE_URL"));
        System.out.println("USE DB: " + dbUrl);

        log.info("USE DB: {}", dbUrl);

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        InputStream url = App.class.getClassLoader()
                .getResourceAsStream("schema.sql");
        String sql = new BufferedReader(new InputStreamReader(url))
                .lines().collect(Collectors.joining("\n"));

        log.info(sql);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        BaseRepository.dataSource = dataSource;

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> ctx.result("Hello, World!"));

        return app;
    }

    public static void main(final String[] args) {
        Javalin app = getApp();
        app.start(getPort());
    }
}
