package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controller.RootController;
import hexlet.code.controller.UrlsController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

@Slf4j
public final class App {

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    private static boolean isDevelopmentMode() {
        String developmentMode = System.getenv().getOrDefault("DEVELOPMENT_MODE", "false");
        return Boolean.parseBoolean(developmentMode);
    }

    private static String readResourceFile(final String fileName) throws IOException {
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            return "Resource file not found: " + fileName;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return "Error reading resource file: " + fileName;
        }
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public static Javalin getApp() throws IOException, SQLException {

        String dbUrl = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1";
        String envDbUrl = System.getenv("JDBC_DATABASE_URL");
        HikariConfig hikariConfig = new HikariConfig();

        if (envDbUrl == null || envDbUrl.isEmpty()) {
            DriverManager.registerDriver(new org.h2.Driver());
            log.info("JDBC_DATABASE_URL not set, using H2 in-memory database");
        } else {
            DriverManager.registerDriver(new org.postgresql.Driver());
            dbUrl = envDbUrl;
            hikariConfig.setUsername(System.getenv("JDBC_DATABASE_USERNAME"));
            hikariConfig.setPassword(System.getenv("JDBC_DATABASE_PASSWORD"));
        }

        log.info("JDBC_DATABASE_URL: {}", dbUrl);
        hikariConfig.setJdbcUrl(dbUrl);

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        BaseRepository.dataSource = dataSource;
        String sql = readResourceFile("schema.sql");

        log.info("Executing init DB SQL:\n{}", sql);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(NamedRoutes.rootPath(), RootController::index);
        app.get(NamedRoutes.urlsPath(), UrlsController::index);
        app.post(NamedRoutes.urlsPath(), UrlsController::create);
        app.get(NamedRoutes.urlPath("{id}"), UrlsController::show);
        app.post(NamedRoutes.urlCheckPath("{id}"), UrlsController::check);

        return app;
    }

    public static void main(final String[] args)
            throws IOException, SQLException {
        Javalin app = getApp();
        app.start(getPort());
    }
}
