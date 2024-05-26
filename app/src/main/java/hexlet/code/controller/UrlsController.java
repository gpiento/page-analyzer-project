package hexlet.code.controller;

import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void index(final Context ctx) {
        List<Url> urlList = new ArrayList<>();
        urlList.add(new Url(1L, "https://hexlet.io"));
        urlList.add(new Url(2L, "https://hexlet.com"));
        UrlsPage page = new UrlsPage(urlList);
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(final Context ctx) {
        Url page = new Url(1L, "https://hexlet.io");
        ctx.render("urls/show.jte", model("page", page));
    }

    public static void create(final Context ctx) throws URISyntaxException {
        try {
            String url = ctx.formParam("url");
            if (url == null) {
                throw new RuntimeException("url is null");
            }
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            String host = uri.getHost();
            int port = uri.getPort();
            String baseUrl = scheme + "://" + host + (port != -1 ? ":" + port : "");
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        ctx.render("urls/index.jte");
    }
}
