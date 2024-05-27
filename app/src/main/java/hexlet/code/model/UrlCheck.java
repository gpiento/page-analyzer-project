package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class UrlCheck {

    private Long id;
    private int status;
    private String title;
    private String h1;
    private String description;
    private Timestamp createdAt;

    public UrlCheck(int status, String title, String h1, String description) {
        this.status = status;
        this.title = title;
        this.h1 = h1;
        this.description = description;
    }
}
