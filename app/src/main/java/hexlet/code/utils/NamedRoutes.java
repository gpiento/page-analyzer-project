package hexlet.code.utils;

public class NamedRoutes {

    public static String rootPath() {
        return "/";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlPath(final String id) {
        return urlsPath() + "/" + id;
    }

    public static String urlPath(final Long id) {
        return urlsPath() + "/" + id;
    }

    public static String urlCheckPath(final String id) {
        return urlsPath() + "/" + id + "/checks";
    }

    public static String urlCheckPath(final Long id) {
        return urlsPath() + "/" + id + "/checks";
    }
}
