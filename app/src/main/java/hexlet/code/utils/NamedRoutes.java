package hexlet.code.utils;

public class NamedRoutes {

    public static String rootPath() {
        return "/";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlPath(String id) {
        return urlsPath() + "/" + id;
    }

    public static String urlCheckPath(String id) {
        return urlsPath() + "/" + id + "/checks";
    }
}
