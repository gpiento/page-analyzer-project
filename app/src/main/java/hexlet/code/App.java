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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

@Slf4j
public final class App {

    private static int getPort() {

        String port = System.getenv().getOrDefault("PORT", "7070");

        return Integer.parseInt(port);
    }

    private static String getDbUrl() {

        String envDbUrl = System.getenv("JDBC_DATABASE_URL");

        return envDbUrl != null && !envDbUrl.isEmpty() ? envDbUrl : "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1";
    }

    private static String getDriverClassName(final String dbUrl) {

        log.info("JDBC_DATABASE_DRIVER: {}", dbUrl.contains("postgresql") ? "org.postgresql.Driver" : "org.h2.Driver");

        return dbUrl.contains("postgresql") ? "org.postgresql.Driver" : "org.h2.Driver";
    }

    private static String readResourceFile(final String fileName) throws IOException {

        InputStream inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private static TemplateEngine createTemplateEngine() {

        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver(
                "templates", classLoader);

        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public static Javalin getApp() throws IOException, SQLException {

        String dbUrl = getDbUrl();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(getDriverClassName(dbUrl));
        hikariConfig.setJdbcUrl(dbUrl);
        //log.info("JDBC_DATABASE_URL: {}", dbUrl);

        BaseRepository.dataSource = new HikariDataSource(hikariConfig);
        String sql = readResourceFile("schema.sql");

        log.info("Executing init DB SQL:\n{}", sql);
        try (Connection connection = BaseRepository.dataSource.getConnection();
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
