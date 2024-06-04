package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class UrlsController {

    public static void index(final Context ctx) throws SQLException {

        UrlsPage urlsPage = new UrlsPage(UrlsRepository.getEntities());
        ctx.render("urls/index.jte", Collections.singletonMap("page", urlsPage));
    }

    public static void show(final Context ctx) throws SQLException {

        long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlsRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        List<UrlCheck> urlCheck = UrlCheckRepository.getEntities(id);
        UrlPage page = new UrlPage(url, urlCheck);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }

    public static void create(final Context ctx)
            throws URISyntaxException, SQLException {

        String inputUrl = ctx.formParam("url");
        URL parseUrl;
        try {
            parseUrl = new URI(inputUrl.trim().toLowerCase()).toURL();
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }
        String normalUrl = String.format(
                "%s://%s%s",
                parseUrl.getProtocol() == null ? "http" : parseUrl.getProtocol(),
                parseUrl.getHost(),
                parseUrl.getPort() == -1 ? "" : ":" + parseUrl.getPort()
        );
        if (UrlsRepository.existsByName(normalUrl)) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flash-type", "warning");
        } else {
            Url url = new Url(normalUrl);
            UrlsRepository.save(url);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flash-type", "success");
        }
        ctx.redirect(NamedRoutes.urlsPath());
    }

    public static void check(final Context ctx)
            throws URISyntaxException, SQLException {

        long id = ctx.pathParamAsClass("id", Long.class).get();
        List<UrlCheck> urlChecks = UrlCheckRepository.getEntities(id);
        ctx.sessionAttribute("flash", "Страница успешно проверена");
        ctx.sessionAttribute("flash-type", "success");
        ctx.redirect(NamedRoutes.urlsPath() + "/" + id);
    }
}
