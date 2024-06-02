package hexlet.code.controller;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Collections;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void index(final Context ctx) throws SQLException {
        UrlsPage urlsPage = new UrlsPage(UrlsRepository.getEntities());
        ctx.render("urls/index.jte", Collections.singletonMap("page", urlsPage));
    }

    public static void show(final Context ctx) throws SQLException {
        String id = ctx.pathParam("id");
        Url url = UrlsRepository.find(Long.parseLong(id))
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        UrlPage page = new UrlPage(url);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/show.jte", model("page", page));
        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }

    public static void create(final Context ctx)
            throws URISyntaxException, SQLException {
        String inputUrl = ctx.formParam("url");
        if (inputUrl == null || inputUrl.isEmpty()) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }
        URI parseUrl;
        try {
            parseUrl = new URI(inputUrl);
        } catch (URISyntaxException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }
        String normalUrl = String.format(
                "%s://%s%s",
                parseUrl.getScheme() == null ? "http" : parseUrl.getScheme(),
                parseUrl.getHost(),
                parseUrl.getPort() == -1 ? "" : ":" + parseUrl.getPort()
        ).toLowerCase();
        if (UrlsRepository.existsByName(normalUrl)) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flash-type", "warning");
        } else {
            Url urlEntity = new Url(normalUrl);
            UrlsRepository.save(urlEntity);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flash-type", "success");
        }
        ctx.redirect(NamedRoutes.urlsPath());
    }

    public static void check(final Context ctx)
            throws URISyntaxException, SQLException {
        String inputUrl = ctx.formParam("url");
        if (inputUrl == null || inputUrl.isEmpty()) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }
        URI parseUrl;
        try {
            parseUrl = new URI(inputUrl);
        } catch (URISyntaxException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }
        String normalUrl = String.format(
                "%s://%s%s",
                parseUrl.getScheme() == null ? "http" : parseUrl.getScheme(),
                parseUrl.getHost(),
                parseUrl.getPort() == -1 ? "" : ":" + parseUrl.getPort()
        ).toLowerCase();
        if (UrlsRepository.existsByName(normalUrl)) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flash-type", "warning");
        } else {
            Url urlEntity = new Url(normalUrl);
            UrlsRepository.save(urlEntity);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flash-type", "success");
        }
        ctx.redirect(NamedRoutes.urlsPath());
    }
}
