package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import io.javalin.http.Context;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
public class RootController {

    public static void index(final Context ctx) {
        BasePage page = new BasePage();
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        log.info("Flash: {}", page.getFlash());
        log.info("FlashType: {}", page.getFlashType());
        ctx.render("index.jte", Collections.singletonMap("page", page));
    }
}
