package hexlet.code.repository;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UrlCheckRepositoryTest {

    MockWebServer mockWebServer;

    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
    }

    @Test
    void save() {
    }

    @Test
    void getEntities() {
    }

    @Test
    void findLastChecks() {
    }
}
