package hexlet.code.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@ToString
public final class Url {

    private Long id;
    private String name;
    private Timestamp createdAt;

    public Url(final String name) {
        this.name = name;
    }

    public Url(final String name, final Timestamp createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
