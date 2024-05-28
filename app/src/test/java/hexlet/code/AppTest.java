package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AppTest {

    private Javalin app;

    @BeforeEach
    public void setUp() throws Exception {
        app = App.getApp();
    }

    @Test
    void testMainPage() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            String requestBody = "url=http://example.com:8080";
            Response response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("http://example.com:8080");
        });
    }

    @Test
    void testNotCorrectUrl() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            String requestBody = "url=sdfSDFa342dsf";
            Response response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlPage() throws SQLException {
        Url url = new Url("http://example.com:8080");
        UrlsRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            Response response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    void testUrlExistsByName() throws Exception {
        Url url = new Url("http://example.com:8080");
        UrlsRepository.save(url);
        assertThat(UrlsRepository.existsByName("http://example.com:8080")).isTrue();
    }

    @Test
    public void testUrlWithName() {
        String name = "http://example.com";
        Url url = new Url(name);
        assertNull(url.getId());
        assertEquals(name, url.getName());
        assertNull(url.getCreatedAt());
    }

    @Test
    public void testUrlWithNameAndCreatedAt() {
        String name = "http://example.com";
        Timestamp createdAt = Timestamp.from(Instant.now());
        Url url = new Url(name, createdAt);
        assertNull(url.getId());
        assertEquals(name, url.getName());
        assertEquals(createdAt, url.getCreatedAt());
    }

    @Test
    public void testUrlSetters() {
        Long id = 1L;
        String name = "http://example.com";
        Timestamp createdAt = Timestamp.from(Instant.now());
        Url url = new Url(name);

        url.setId(id);
        url.setName(name);
        url.setCreatedAt(createdAt);

        assertEquals(id, url.getId());
        assertEquals(name, url.getName());
        assertEquals(createdAt, url.getCreatedAt());
    }

    @Test
    public void testUrlToString() {
        Long id = 1L;
        String name = "http://example.com";
        Timestamp createdAt = Timestamp.from(Instant.now());
        Url url = new Url(id, name, createdAt);

        String expectedString = "Url(id=" + id + ", name=" + name + ", createdAt=" + createdAt + ")";
        assertEquals(expectedString, url.toString());
    }
}
