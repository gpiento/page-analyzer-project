package hexlet.code;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import java.io.IOException;
import java.sql.SQLException;

public final class App {

    public static Javalin getApp() {
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> ctx.result("Hello, World!"));

        return app;
    }

    public static void main(final String[] args) throws IOException, SQLException {
        Javalin app = getApp();
        app.start(7070);
    }
}
