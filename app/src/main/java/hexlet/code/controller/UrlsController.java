package hexlet.code.controller;

import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import io.javalin.http.Context;

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
}
