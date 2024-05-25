package hexlet.code.repository;

import io.javalin.http.Context;

public class UrlsRepository {

    public static void index(Context ctx) {
        ctx.render("urls/index.jte");
    }
}
